package com.product.affiliation.services;

import com.product.affiliation.data.MonitorDataMapper;
import com.product.affiliation.data.Product;
import com.product.affiliation.data.ProductDataClient;
import com.product.affiliation.data.ProductTypeEnum;
import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl<T> implements MonitorService<T> {
    private static final Logger LOG = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Autowired
    private ProductDataClient productDataClient;

    @Override
    public CompletableFuture<List<Product>> findAllMonitors(int pageNumber, int maxSize, FilterCriteria filterCriteria) {
        System.out.println(filterCriteria.getFiltersCriteria().toString());
        return productDataClient.findAllProductsByType(ProductTypeEnum.MONITOR.name(), filterCriteria, new MonitorDataMapper());
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public Product findById(long productId) {
        return null;
    }

    @Override
    public CompletableFuture<Set<String>> getProjectedUniqueItems(String attributeColumnName,
                                                                  ExecutorService filterExecutors) {
        switch (attributeColumnName) {
            case "refreshRate", "screenSize", "productCondition":
                return productDataClient.findProductAttributeByName(ProductTypeEnum.MONITOR.name(), Arrays.asList(attributeColumnName));
          case "brand", "connectivityTech", "color", "displayResolution", "displayType":
              return CompletableFuture.completedFuture(Collections.emptySet());
         default:
                return CompletableFuture.supplyAsync(() -> Collections.emptySet());
        }
    }
}
