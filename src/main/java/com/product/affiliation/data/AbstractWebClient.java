package com.product.affiliation.data;

import kong.unirest.core.Config;
import kong.unirest.core.Unirest;

public abstract class AbstractWebClient {
    private final String PRODUCT_SERVICE_BASE_URL;

    public AbstractWebClient(String baseURL) {
        PRODUCT_SERVICE_BASE_URL = baseURL;
    }

    protected String getAllProductsURL() {
        return String.format("%s/%s", PRODUCT_SERVICE_BASE_URL, "/get/all");
    }

    protected String getAttributeValueByNameURL() {
        return String.format("%s/%s/{%s}", PRODUCT_SERVICE_BASE_URL, "attr", "name");
    }

    protected void unirestConfig() {
      Unirest
        .config()
        .connectTimeout(3000)
        .setDefaultHeader("Accept", "application/json")
        .interceptor(new ProductRESTInterceptor());
    }
}
