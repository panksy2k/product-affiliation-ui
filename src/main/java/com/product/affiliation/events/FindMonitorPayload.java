package com.product.affiliation.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Map;

public class FindMonitorPayload extends ProductPayload {
  static final String TYPE = "FIND_MONITOR";

  @JsonProperty
  private Map<String, Collection<?>> queryAttributes;

  public FindMonitorPayload(@JsonProperty("id") String id) {
    super(id);
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public Map<String, Collection<?>> getQueryAttributes() {
    return queryAttributes;
  }

  public void setQueryAttributes(Map<String, Collection<?>> queryAttributes) {
    this.queryAttributes = queryAttributes;
  }
}
