package com.product.affiliation.data;

import java.net.URL;
import java.util.EnumSet;

public class Monitor {
    private long id;
    private String name;
    private URL affiliateURL;
    private Condition productCondition;
    private Double price;
    private Short warrantyValue;
    private Warranty unitOfWarranty;
    private Short screenSize;
    private ScreenSizeUnit sizeUnit;
    private Short refreshRate;
    private RefreshRateFrequency refreshRateUnit;
    private String maxDisplayResolution;
    private DisplayType displayType;
    private String dimension;
    private EnumSet<SpecialFeature> specialFeatures;
    private String brand;
    private String color;

    enum Condition {
        NEW,
        USED
    }

    enum Warranty {
        MONTHS,
        YEARS,
        WEEKS,
        DAYS
    }

    enum ScreenSizeUnit {
        INCHES,
        CM
    }

    enum RefreshRateFrequency {
        Hz
    }

    enum DisplayType {
        LED,
        LCD
    }

    enum SpecialFeature {
        CurvedScreen,
        TiltAdjustment,
        FlickerFree
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getAffiliateURL() {
        return affiliateURL;
    }

    public void setAffiliateURL(URL affiliateURL) {
        this.affiliateURL = affiliateURL;
    }

    public Condition getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(Condition productCondition) {
        this.productCondition = productCondition;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Short getWarrantyValue() {
        return warrantyValue;
    }

    public void setWarrantyValue(Short warrantyValue) {
        this.warrantyValue = warrantyValue;
    }

    public Warranty getUnitOfWarranty() {
        return unitOfWarranty;
    }

    public void setUnitOfWarranty(Warranty unitOfWarranty) {
        this.unitOfWarranty = unitOfWarranty;
    }

    public Short getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Short screenSize) {
        this.screenSize = screenSize;
    }

    public ScreenSizeUnit getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(ScreenSizeUnit sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public Short getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(Short refreshRate) {
        this.refreshRate = refreshRate;
    }

    public RefreshRateFrequency getRefreshRateUnit() {
        return refreshRateUnit;
    }

    public void setRefreshRateUnit(RefreshRateFrequency refreshRateUnit) {
        this.refreshRateUnit = refreshRateUnit;
    }

    public String getMaxDisplayResolution() {
        return maxDisplayResolution;
    }

    public void setMaxDisplayResolution(String maxDisplayResolution) {
        this.maxDisplayResolution = maxDisplayResolution;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(DisplayType displayType) {
        this.displayType = displayType;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public EnumSet<SpecialFeature> getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(EnumSet<SpecialFeature> specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
