package com.product.affiliation.query;

import java.util.Set;

public interface Operator<V> {
    default String name() {
        return getClass().getSimpleName();
    }

    Set<V> values();

    String getColumnName();
}
