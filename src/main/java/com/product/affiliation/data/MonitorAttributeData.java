package com.product.affiliation.data;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import kong.unirest.core.json.JSONArray;

public final class MonitorAttributeData implements Function<JSONArray, CompletableFuture<Set<String>>> {
  private List<String> jsonKey;

  public MonitorAttributeData(List<String> jsonNodeAttributes) {
    jsonKey = jsonNodeAttributes;
  }

  @Override
  public CompletableFuture<Set<String>> apply(JSONArray responseArray) {
    if(responseArray == null || responseArray.length() == 0) {
      throw new IllegalArgumentException("Not an array of attribute items!");
    }

    Set<String> parsedValues = new LinkedHashSet<>();

    for(int i = 0; i < responseArray.length(); i++) {
        parsedValues.add(responseArray.getString(i));
    }
    System.out.println(parsedValues);
    return CompletableFuture.completedFuture(parsedValues);
  }
}
