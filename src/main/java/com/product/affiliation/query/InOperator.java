package com.product.affiliation.query;

import java.util.List;

public class InOperator<V> implements Operator<V> {

    private final String columnName;
    private final List<V> allInclusiveValues;

    public InOperator(String columnName, List<V> allValues) {
        this.columnName = columnName;
        if(allValues == null || allValues.isEmpty()) {
            throw new IllegalArgumentException("InOperator should at least have 1 value for SQL expression");
        }

        this.allInclusiveValues = allValues;
    }

    @Override
    public List<V> values() {
        return allInclusiveValues;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
