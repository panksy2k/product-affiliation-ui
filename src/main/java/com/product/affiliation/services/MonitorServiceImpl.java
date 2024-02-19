package com.product.affiliation.services;

import com.product.affiliation.data.Monitor;
import com.product.affiliation.query.Operator;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class MonitorServiceImpl<T> implements MonitorService<T> {
    @Override
    public List<Monitor> findAllMonitors(int pageNumber, int maxSize, Map<String, Operator<T>> filterCriteria) {
        if(!filterCriteria.isEmpty()) {
            //Get it on the basis of criteria
            return Collections.emptyList();
        }

        Monitor m = new Monitor();
        m.setId(1);
        m.setConnectivityTech(EnumSet.of(Monitor.ConnectivityTech.HDMI));
        m.setBrand("Dell");
        m.setColor("Black");
        m.setDimension("20cm x 23 cm");
        m.setName("Dell 23 Inch Ulta Monitor");
        try {
            m.setAffiliateURL(new URL("https://www.amazon.co.uk"));
        } catch(Exception e) {

        }

        m.setAmazonChoice(true);
        m.setDisplayType(Monitor.DisplayType.LED);
        m.setProductCondition(Monitor.Condition.NEW);
        m.setRefreshRate(Short.valueOf("60"));
        m.setRefreshRateUnit(Monitor.RefreshRateFrequency.Hz);

        m.setWarrantyValue(Short.valueOf("3"));
        m.setUnitOfWarranty(Monitor.Warranty.Years);

        m.setSpecialFeatures(EnumSet.of(Monitor.SpecialFeature.FlickerFree, Monitor.SpecialFeature.CurvedScreen));
        m.setScreenSize(Short.valueOf("42"));
        m.setSizeUnit(Monitor.ScreenSizeUnit.Inches);

        m.setPrice(350D);
        m.setMaxDisplayResolution("2560 x 1940");

        return Arrays.asList(m);
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
    public List<String> getProjectedUniqueItems(String attributeColumnName) {
        switch(attributeColumnName) {
            case "refreshRate": return Arrays.asList("60", "75", "150", "250");
            case "brand": return Arrays.asList("Dell", "Samsung", "HP", "BENQ");
            case "connectivityTech": return Arrays.asList("VGA", "HDMI", "DGVA", "USBC");
            case "productCondition": return Arrays.asList("New", "Used");
            case "color": return Arrays.asList("Black", "White");
            case "screenSize": return Arrays.asList("23", "32", "35", "42");
            case "displayResolution": return Arrays.asList("2950 x 1440");
            case "displayType": return Arrays.asList("LED", "LCD");
            default: return Collections.emptyList();
        }
    }
}
