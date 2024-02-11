package com.product.affiliation.query;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EqualsOperator<V> implements Operator<V> {

    private final String columnName;
    private final V value;

    public EqualsOperator(String columnName, V value) {
        this.columnName = columnName;
        if(value == null) {
            throw new IllegalArgumentException("EqualsOperator should not be null");
        }

        this.value = value;
    }

    @Override
    public List<V> values() {
        return Stream.of(value).collect(Collectors.toList());
    }

    @Override
    public String getColumnName() {
        return columnName;
    }
}
