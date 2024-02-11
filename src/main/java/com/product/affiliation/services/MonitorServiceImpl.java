package com.product.affiliation.services;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.query.Operator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl implements MonitorService {
    @Override
    public List<Monitor> findAllMonitors(int pageNumber, int maxSize) {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Monitor findById(long productId) {
        return null;
    }

    @Override
    public <T> List<T> getProjectedUniqueItems(String attributeColumnName) {
        return null;
    }

    @Override
    public <T> List<Monitor> fetchAllMonitorsByFilters(List<Operator<T>> filters) {
        return null;
    }
}
