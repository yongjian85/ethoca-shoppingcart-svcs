package com.yxj.ethoca.controllers;

import com.yxj.ethoca.Response.GetProductsResponse;
import com.yxj.ethoca.dto.Product;
import com.yxj.ethoca.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping(value="/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetProductsResponse> retrieveKeystoreCreationInfo() {

        GetProductsResponse getProductsResponse = new GetProductsResponse();

        try {

            getProductsResponse.setProducts(productService.getProducts());
        } catch (Exception e) {
           List<String> errors = new ArrayList();
           errors.add("could not get products from mongoDB");
            getProductsResponse.setErrors(errors);

            return ResponseEntity.status(400).body(getProductsResponse);


        }


        return ResponseEntity.status(200).body(getProductsResponse);


    }

}
