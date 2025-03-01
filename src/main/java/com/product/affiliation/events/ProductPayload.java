package com.product.affiliation.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(name = FindMonitorPayload.TYPE, value = FindMonitorPayload.class)
})
public abstract class ProductPayload {
  @JsonProperty
  private final String id;

  public ProductPayload(String id) {
    this.id = id;
  }

  public abstract String getType();

  public String getId() {
    return this.id;
  }
}
