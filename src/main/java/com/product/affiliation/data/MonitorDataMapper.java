package com.product.affiliation.data;

import static java.util.Objects.requireNonNullElseGet;
import com.product.affiliation.exception.ParsingException;
import com.product.affiliation.util.ObjectUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kong.unirest.core.JsonNode;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;

public class MonitorDataMapper implements Function<JsonNode, List<Product>> {

  @Override
  public List<Product> apply(JsonNode productJSON) {
    System.out.println(productJSON);
    if(productJSON.isArray()) {
      JSONArray responseArray = productJSON.getArray();
      List<Product> retrivedProductAttribute = new ArrayList<>(responseArray.length());

      for(int i = 0 ; i < responseArray.length(); i++) {
        JSONObject jsonObject = responseArray.getJSONObject(i);

        final Product prd = new Product();

        requireNonNullElseGet(jsonObject.getString("id"), () -> new IllegalArgumentException("ID not found for the product whilst parsing!"));
        requireNonNullElseGet(jsonObject.getString("affiliateLink"), () -> new IllegalArgumentException("affiliateLink not found for the product whilst parsing!"));

        if(jsonObject.has("price")) {
          JSONObject priceJSON = jsonObject.getJSONObject("price");
          Double productPrice = (Double) ObjectUtil.parseValue(priceJSON, "cost", Double.class);
          String productPriceCurrency = (String) ObjectUtil.parseValue(priceJSON, "productCurrency", String.class);

          prd.setPrice(String.format("%s %.3f", productPriceCurrency, productPrice));
        }

        if(jsonObject.has("refreshRate")) {
          JSONObject refreshRateJSON = jsonObject.getJSONObject("refreshRate");
          Integer productRefreshRateVal = (Integer) ObjectUtil.parseValue(refreshRateJSON, "value", Integer.class);
          String productRefreshRateUnit = (String) ObjectUtil.parseValue(refreshRateJSON, "measure", String.class);

          prd.setRefreshRate(String.format("%d %s", productRefreshRateVal, productRefreshRateUnit));
        }

        if(jsonObject.has("responseTime")) {
          JSONObject responseTimeJSON = jsonObject.getJSONObject("responseTime");
          Float productResponseTimeVal = (Float) ObjectUtil.parseValue(responseTimeJSON, "value", Float.class);
          String productResponseTimeUnit = (String) ObjectUtil.parseValue(responseTimeJSON, "measurement", String.class);

          prd.setResponseTime(String.format("%.1f %s", productResponseTimeVal, productResponseTimeUnit));
        }

        if(jsonObject.has("screenSize")) {
          JSONObject screenSizeJSON = jsonObject.getJSONObject("screenSize");
          Float productScreenSizeVal = (Float) ObjectUtil.parseValue(screenSizeJSON, "size", Float.class);
          String productResponseTimeUnit = (String) ObjectUtil.parseValue(screenSizeJSON, "unit", String.class);

          prd.setScreenSize(String.format("%.1f %s", productScreenSizeVal, productResponseTimeUnit));
        }

        if(jsonObject.has("warranty")) {
          JSONObject warranty = jsonObject.getJSONObject("warranty");
          Integer warrantyVal = (Integer) ObjectUtil.parseValue(warranty, "warrantyValue", Integer.class);
          String warrantyUnit = (String) ObjectUtil.parseValue(warranty, "warrantyUnit", String.class);

          prd.setWarrantyValue(String.format("%d %s", warrantyVal, warrantyUnit));
        }

        prd.setProductCondition(Product.Condition.valueOf(jsonObject.getString("productCondition")));
        prd.setName(jsonObject.getString("description"));

        try {
          URL affiliateLink = new URL(jsonObject.getString("affiliateLink"));
          prd.setAffiliateURL(affiliateLink);
        } catch(MalformedURLException e) {
          throw new ParsingException(e.getMessage());
        }

        retrivedProductAttribute.add(prd);
      }

      return retrivedProductAttribute;
    }

    return Collections.emptyList();
  }
}
