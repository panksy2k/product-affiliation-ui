package com.product.affiliation.services;

import com.product.affiliation.data.Product;
import com.product.affiliation.query.Operator;
import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


public interface MonitorService<T> {

    List<Product> findAllMonitors(int pageNumber, int maxSize, FilterCriteria filters);

    int count();

    Product findById(long productId);

    CompletableFuture<List<String>> getProjectedUniqueItems(String attributeColumnName, ExecutorService filterExecutors);
}
