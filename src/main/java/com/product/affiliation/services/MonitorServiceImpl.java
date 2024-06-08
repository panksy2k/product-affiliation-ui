package com.product.affiliation.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import com.product.affiliation.data.Product;
import com.product.affiliation.data.ProductFilterSerializer;
import com.product.affiliation.events.FindMonitorPayload;
import com.product.affiliation.views.productbuyaffiliation.FilterCriteria;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl<T> implements MonitorService<T> {
  @Override
  public List<Product> findAllMonitors(int pageNumber, int maxSize, FilterCriteria filterCriteria) {
    Gson gson =
      new GsonBuilder().setPrettyPrinting().registerTypeAdapter(FilterCriteria.class, new ProductFilterSerializer())
        .create();
    String jsonStr = gson.toJson(filterCriteria);

    System.out.printf("%s\n", jsonStr);


    if (!filterCriteria.getFiltersCriteria().isEmpty()) {
      //Get it on the basis of criteria
      return Collections.emptyList();
    }

    //Now serialize the complete payload with UUID, type, JSON content into byte[]
    StringWriter sw = new StringWriter();
    FindMonitorPayload payload = new FindMonitorPayload(UUID.randomUUID(), jsonStr);
    Gson findMonitorGson = new Gson();
    findMonitorGson.toJson(payload, sw);
    sw.toString().getBytes(Charset.forName("UTF-8"));



    return Collections.emptyList();
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
  public CompletableFuture<List<String>> getProjectedUniqueItems(String attributeColumnName,
                                                                 ExecutorService filterExecutors) {
    switch (attributeColumnName) {
      case "refreshRate":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("60", "75", "150", "250"), filterExecutors);
      case "brand":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("Dell", "Samsung", "HP", "BENQ"), filterExecutors);
      case "connectivityTech":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("VGA", "HDMI", "DGVA", "USBC"), filterExecutors);
      case "productCondition":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("New", "Used"), filterExecutors);
      case "color":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("Black", "White"), filterExecutors);
      case "screenSize":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("23", "32", "35", "42"), filterExecutors);
      case "displayResolution":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("2950 x 1440"), filterExecutors);
      case "displayType":
        return CompletableFuture.supplyAsync(() -> Arrays.asList("LED", "LCD"), filterExecutors);
      default:
        return CompletableFuture.supplyAsync(() -> Collections.emptyList());
    }
  }
}
