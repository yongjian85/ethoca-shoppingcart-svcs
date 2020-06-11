package com.yxj.ethoca.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.yxj.ethoca.Constants.Constants.MAX_PURCHASE_LIMIT;

public class LineItem extends Product {

    @Min(1)
    @Max(MAX_PURCHASE_LIMIT)
    private int quantity;

    private String productDescription;


    @Override
    public String getProductDescription() {
        return null;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.productDescription = null;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "LineItem{" +
                "quantity=" + quantity +
                ", productDescription='" + productDescription + '\'' +
                "} " + super.toString();
    }
}
