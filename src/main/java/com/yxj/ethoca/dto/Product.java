package com.yxj.ethoca.dto;

import org.bson.types.ObjectId;

public class Product {

    private ObjectId id;
    private String productName;
    private String productDescription;



    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getProductId() {
        return id.toHexString();
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
