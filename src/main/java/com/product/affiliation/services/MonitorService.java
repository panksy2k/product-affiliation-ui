package com.product.affiliation.services;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.query.Operator;
import java.util.List;

public interface MonitorService {

    List<Monitor> findAllMonitors(int pageNumber, int maxSize);

    int count();

    Monitor findById(long productId);

    <T> List<T> getProjectedUniqueItems(String attributeColumnName);

    <T> List<Monitor> fetchAllMonitorsByFilters(List<Operator<T>> filters);
}
