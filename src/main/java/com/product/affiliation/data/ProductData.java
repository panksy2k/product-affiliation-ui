package com.product.affiliation.data;

import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import kong.unirest.core.JsonNode;

public interface ProductData {
    CompletableFuture<Set<String>> findProductAttributeByName(String productType, List<String> attributeNames) throws CompletionException;
    CompletableFuture<List<Product>> findAllProductsByType(String productType, FilterCriteria criteria, Function<JsonNode, List<Product>> responseMapper) throws CompletionException;
}
