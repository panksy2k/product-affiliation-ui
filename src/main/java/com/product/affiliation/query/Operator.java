package com.product.affiliation.query;

import java.util.List;

public interface Operator<V> {
    default String name() {
        return getClass().getSimpleName();
    }

    List<V> values();

    String getColumnName();
}
