package com.product.affiliation.data;

import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.function.Supplier;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductDataClient extends AbstractWebClient implements ProductData {
    private static final Logger LOG = LoggerFactory.getLogger(ProductDataClient.class);

    public ProductDataClient(String productHttpServiceURL) {
        super(productHttpServiceURL);
    }

    @Override
    public CompletableFuture<Set<String>> findProductAttributeByName(String productType,
                                                                     List<String> attributeNames)
            throws CompletionException {
      if(attributeNames == null || attributeNames.isEmpty()) {
        throw new IllegalArgumentException("No attribute found -- for fetch!");
      }

      Supplier<HttpResponse<JsonNode>> requestGet = () -> {
            //unirestConfig();
            return Unirest.get(getAttributeValueByNameURL())
                    .header("Accept", "application/json")
                    .routeParam("name", attributeNames.get(0))
                    .queryString("productType", productType)
                    .asJson();
        };

        return CompletableFuture.supplyAsync(requestGet)
                .thenCompose(response -> {
                    if (response.getStatus() == 404) {
                        return CompletableFuture.failedFuture(new RuntimeException(""));
                    }

                    JsonNode responseWithAttributeValue = response.getBody();
                    if (responseWithAttributeValue == null || !responseWithAttributeValue.isArray()) {
                        return CompletableFuture.failedFuture(new RuntimeException(
                                "Invalid response from HTTP service - findProductByAttributeName"));
                    }

                    return CompletableFuture.completedFuture(responseWithAttributeValue.getArray())
                      .thenCompose(new MonitorAttributeData(attributeNames));
                })
                .exceptionally(t -> {
                    LOG.error("Error whilst calling a route on a REST service (findProductAttributeByName)", t);
                    return null;
                });
    }

    @Override
    public CompletableFuture<List<Product>> findAllProductsByType(String productType,
                                                                  FilterCriteria criteria,
                                                                  Function<JsonNode, List<Product>> responseMapper)
            throws CompletionException {

        JSONObject productTypeAttributes = new JSONObject();
        productTypeAttributes.put("productType", productType);
        productTypeAttributes.put("attr", criteria.getFiltersCriteria());

        Supplier<HttpResponse<JsonNode>> answerPayloadSupplier = () -> Unirest.post(getAllProductsURL())
                .header("Accept", "application/json")
                .body(productTypeAttributes)
                .asJson();

        return CompletableFuture.supplyAsync(answerPayloadSupplier)
                .thenCompose(res -> {
                    if (res.getStatus() != 200) {
                        return CompletableFuture.failedFuture(new RuntimeException(res.getStatusText()));
                    }

                    JsonNode responseWithAttributeValue = res.getBody();
                    if (responseWithAttributeValue == null || !responseWithAttributeValue.isArray()) {
                        return CompletableFuture.failedFuture(
                                new RuntimeException("Invalid response from HTTP service " + getAllProductsURL()));
                    }

                    return CompletableFuture.completedFuture(responseMapper.apply(responseWithAttributeValue));
                })
                .exceptionally(e -> {
                    LOG.error("Error whilst calling/parsing a route on a REST service (findAllProductsByType)", e);
                    return null;
                });
    }
}
