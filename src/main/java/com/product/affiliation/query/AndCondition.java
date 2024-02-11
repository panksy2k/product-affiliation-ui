package com.product.affiliation.query;

public interface AndCondition<T extends Operator<T>, V extends Operator<V>> extends Condition<T, V> {
    String and(Operator<T> op1, Operator<V> op2);
}
