package com.os.inventory.entity;

public class SalesProduct {
    private String id;
    private String productCode;
    private String productName;
    private String brand;
    private String sellingUnit;
    private String sellingUnitPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellingUnit() {
        return sellingUnit;
    }

    public void setSellingUnit(String sellingUnit) {
        this.sellingUnit = sellingUnit;
    }

    public String getSellingUnitPrice() {
        return sellingUnitPrice;
    }

    public void setSellingUnitPrice(String sellingUnitPrice) {
        this.sellingUnitPrice = sellingUnitPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
