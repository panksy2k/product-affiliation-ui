package com.product.affiliation.events;

import java.util.UUID;

public abstract class ProductPayload {
    private final UUID id;

    public ProductPayload(UUID id) {
      this.id = id;
    }

    public abstract String getType();

    public UUID getId() {
      return this.id;
    }
}
