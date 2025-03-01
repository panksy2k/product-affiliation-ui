package com.product.affiliation.data;

import kong.unirest.core.Config;
import kong.unirest.core.HttpRequest;
import kong.unirest.core.HttpRequestSummary;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Interceptor;

public class ProductRESTInterceptor implements Interceptor {
    @Override
    public void onRequest(HttpRequest<?> request, Config config) {
        //System.out.println("Request Payload: " + request.asJson().getBody().toPrettyString());
    }

    @Override
    public void onResponse(HttpResponse<?> response, HttpRequestSummary request, Config config) {
        //System.out.println("Response Payload: " + response.getBody().toString());
    }
}
