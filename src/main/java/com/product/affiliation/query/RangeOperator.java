package com.product.affiliation.query;

import java.util.Set;

public class RangeOperator<V> implements Operator<V> {

    private final Set<V> rangeValues;

    public RangeOperator(String columnName, Set<V> allValues) {
        if(allValues == null || allValues.size() != 2) {
            throw new IllegalArgumentException("RangeOperator should have 2 values for SQL expression");
        }

        this.rangeValues = allValues;
    }

    @Override
    public Set<V> values() {
        return rangeValues;
    }

}
