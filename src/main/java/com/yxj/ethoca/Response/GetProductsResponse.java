package com.yxj.ethoca.Response;

import com.yxj.ethoca.dto.Product;

import java.util.List;

public class GetProductsResponse extends BaseResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
