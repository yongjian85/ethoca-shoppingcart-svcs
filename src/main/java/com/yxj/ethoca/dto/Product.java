package com.yxj.ethoca.dto;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.yxj.ethoca.Constants.Constants.PAYLOAD_VALIDATION_ALPHANUMERICS;
import static com.yxj.ethoca.Constants.Constants.PAYLOAD_VALIDATION_ALPHANUMERICS_WITH_SPACE;

public class Product {

    private ObjectId id;

    @NotBlank
    @Pattern(regexp=PAYLOAD_VALIDATION_ALPHANUMERICS, message= "Product Id must only contain alphaNumerics")
    private String productId;

    @NotBlank
    @Pattern(regexp=PAYLOAD_VALIDATION_ALPHANUMERICS_WITH_SPACE, message= "Product Name must only contain alphaNumerics and spaces")
    private String productName;

    //Not needed as part of request payloads
    private String productDescription;

    private String productImg;

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productImg='" + productImg + '\'' +
                '}';
    }
}
