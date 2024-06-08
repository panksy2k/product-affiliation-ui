package com.product.affiliation.views.productbuyaffiliation;

import com.product.affiliation.query.Operator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class FilterCriteria  {

    private final Map<String, Operator<?>> filtersCriteria;

    public FilterCriteria() {
        this.filtersCriteria = new ConcurrentHashMap<>();
    }

    public Map<String, Operator<?>> getFiltersCriteria() {
        return this.filtersCriteria;
    }

    public void clear() {
        filtersCriteria.clear();
    }

    public void addDataOperation(String propertyName, BiFunction<String, Operator<?>, Operator<?>> func) {
        filtersCriteria.compute(propertyName, func);
    }
}
