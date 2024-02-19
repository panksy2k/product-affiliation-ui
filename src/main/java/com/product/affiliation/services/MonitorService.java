package com.product.affiliation.services;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.query.Operator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


public interface MonitorService<T> {

    List<Monitor> findAllMonitors(int pageNumber, int maxSize, Map<String, Operator<T>> filters);

    int count();

    Monitor findById(long productId);

    List<String> getProjectedUniqueItems(String attributeColumnName);
}
