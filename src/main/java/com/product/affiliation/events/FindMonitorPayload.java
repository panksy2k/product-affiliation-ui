package com.product.affiliation.events;

import java.util.UUID;

public class FindMonitorPayload extends ProductPayload {
  private static final String TYPE = "FIND_MONITOR";

  private final String queryPayload;

  public FindMonitorPayload(UUID id, String queryPayload) {
    super(id);
    this.queryPayload = queryPayload;
  }

  @Override
  public String getType() {
    return TYPE;
  }

  public String getQueryPayload() {
    return queryPayload;
  }
}
