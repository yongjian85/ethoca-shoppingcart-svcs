package com.yxj.ethoca.controllers;

import com.yxj.ethoca.Response.BaseResponse;
import com.yxj.ethoca.Response.GetProductsResponse;

import com.yxj.ethoca.services.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LogManager.getLogger(ProductsController.class);


    /*
    Retrieves the list of current products from MongoDb to be displayed to the front end
    //TODO: set up pagination for a real products backend where there may be many products
     */

    @GetMapping(value="/products", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            //products could be empty
            @ApiResponse(code = 200, message = "list of products to be returned to caller", response = GetProductsResponse.class),
             @ApiResponse(code = 500, message = "Unable to query data to repository", response = BaseResponse.class)
           })
    public ResponseEntity<GetProductsResponse> retrieveProductsInfo() {

        logger.info("Request: GET - products");

        GetProductsResponse getProductsResponse = new GetProductsResponse();


        try {

            getProductsResponse.setProducts(productService.getProducts());
            logger.info ("Response: " + getProductsResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(200).body(getProductsResponse);

        } catch (Exception e) {
           List<String> errors = new ArrayList();
           errors.add("could not get products from mongoDB");
            getProductsResponse.setErrors(errors);

            logger.error("Response: " +getProductsResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(500).body(getProductsResponse);


        }





    }

}
