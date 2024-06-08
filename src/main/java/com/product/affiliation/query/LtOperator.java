package com.product.affiliation.query;

import java.util.Set;

public class LtOperator<V> implements Operator<V> {
    private final Set<V> minRangeValue;

    public LtOperator(Set<V> atleastValue) {
        if(atleastValue == null || atleastValue.size() != 1) {
            throw new IllegalArgumentException("LtOperator should have 1 value for SQL expression");
        }

        this.minRangeValue = atleastValue;
    }

    @Override
    public String name() {
        return LtOperator.class.getSimpleName();
    }

    @Override
    public Set<V> values() {
        return minRangeValue;
    }

}
