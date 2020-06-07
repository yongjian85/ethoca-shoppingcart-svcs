package com.yxj.ethoca.dto;

public class LineItem extends Product {

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


}
