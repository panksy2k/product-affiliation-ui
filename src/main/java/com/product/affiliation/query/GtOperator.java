package com.product.affiliation.query;

import java.util.Set;

public class GtOperator<V> implements Operator<V> {
    private final Set<V> maxRangeValue;

    public GtOperator(Set<V> allValues) {
        if(allValues == null || allValues.size() != 1) {
            throw new IllegalArgumentException("GtOperator should have 1 value for SQL expression");
        }

        this.maxRangeValue =  allValues;
    }
    @Override
    public String name() {
        return GtOperator.class.getSimpleName();
    }

    @Override
    public Set<V> values() {
        return maxRangeValue;
    }
}
