package com.product.affiliation.query;

import java.util.Set;

public class InOperator<V> implements Operator<V> {

    private final Set<V> allInclusiveValues;

    public InOperator(Set<V> allValues) {
        if(allValues == null) {
            throw new IllegalArgumentException("InOperator should at least have 1 value for SQL expression");
        }

        this.allInclusiveValues = allValues;
    }

    @Override
    public Set<V> values() {
        return allInclusiveValues;
    }


}
