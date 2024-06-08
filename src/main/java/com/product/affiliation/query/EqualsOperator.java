package com.product.affiliation.query;

import java.util.Set;

public class EqualsOperator<V> implements Operator<V> {
    private final Set<V> value;

    public EqualsOperator(Set<V> value) {
        if(value == null) {
            throw new IllegalArgumentException("EqualsOperator should not be null");
        }

        this.value = value;
    }

    @Override
    public Set<V> values() {
        return value;
    }
}
