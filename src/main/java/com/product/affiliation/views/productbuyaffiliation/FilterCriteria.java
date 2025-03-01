package com.product.affiliation.views.productbuyaffiliation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilterCriteria  {
    private final List<ProductQuery> filtersCriteria;

    public FilterCriteria() {
        this.filtersCriteria = new ArrayList<>();
    }

    public List<ProductQuery> getFiltersCriteria() {
        return this.filtersCriteria;
    }

    public void clear() {
        filtersCriteria.clear();
    }

    public void addDataOperation(ProductQuery productQuery) {
        int idx = findQueryParam(ProductQuery.of(null, productQuery.getKey(), null), ProductQuery.byKey);
        if(idx >= 0) {
          filtersCriteria.set(idx, productQuery);
        } else {
          filtersCriteria.add(productQuery);
        }
    }

    public void removeDataOperation(ProductQuery productQuery) {
        int idx = findQueryParam(ProductQuery.of(null, productQuery.getKey(), null), ProductQuery.byKey);
        if(idx >= 0) {
          filtersCriteria.remove(idx);
        }
    }

    private int findQueryParam(ProductQuery productQueryFindKey, Comparator<ProductQuery> byKey) {
      for(int i = 0; i < filtersCriteria.size(); i++) {
        if(byKey.compare(filtersCriteria.get(i), productQueryFindKey) == 0) {
          return i;
        }
      }

      return -1;
    }
}
