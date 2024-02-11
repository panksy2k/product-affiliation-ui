package com.product.affiliation.query;

import java.util.List;

public class RangeOperator<V> implements Operator<V> {

    private final String columnName;
    private final List<V> rangeValues;

    public RangeOperator(String columnName, List<V> allValues) {
        this.columnName = columnName;

        if(allValues == null || allValues.size() != 2) {
            throw new IllegalArgumentException("RangeOperator should have 2 values for SQL expression");
        }

        this.rangeValues = allValues;
    }

    @Override
    public List<V> values() {
        return rangeValues;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
