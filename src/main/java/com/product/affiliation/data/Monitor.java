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
    private boolean amazonChoice;
    private EnumSet<ConnectivityTech> connectivityTech;

    public enum Condition {
        NEW,
        USED
    }

    public enum Warranty {
        Months,
        Years,
        Weeks,
        Days
    }

    public enum ScreenSizeUnit {
        Inches,
        Centimetres
    }

    public enum RefreshRateFrequency {
        Hz
    }

    public enum DisplayType {
        LED,
        LCD
    }

    public enum SpecialFeature {
        CurvedScreen,
        TiltAdjustment,
        FlickerFree
    }

    public enum ConnectivityTech {
        USBC("USB Type C"),
        HDMI("HDMI"),
        USBA("USB Type A");

        private final String desc;
        ConnectivityTech(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return this.desc;
        }
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

    public boolean isAmazonChoice() {
        return amazonChoice;
    }

    public void setAmazonChoice(boolean amazonChoice) {
        this.amazonChoice = amazonChoice;
    }

    public EnumSet<ConnectivityTech> getConnectivityTech() {
        return connectivityTech;
    }

    public void setConnectivityTech(EnumSet<ConnectivityTech> connectivityTech) {
        this.connectivityTech = connectivityTech;
    }
}
