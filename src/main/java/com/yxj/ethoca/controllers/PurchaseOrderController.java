package com.yxj.ethoca.controllers;

import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.Request.PostPurchaseOrderRequest;
import com.yxj.ethoca.Response.PostPurchaseOrderResponse;
import com.yxj.ethoca.services.PurchaseOrderService;
import com.yxj.ethoca.validators.PostPurchaseOrderValidator;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PostPurchaseOrderValidator postPurchaseOrderValidator;


    @PostMapping(value = "/purchaseOrder")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "confirmation that this order was successfully created", response = PostPurchaseOrderResponse.class),
    @ApiResponse(code = 400, message = "Payload body contained invalid data", response = MethodArgumentNotValidException.class),
    @ApiResponse(code = 500, message = "Unable to save data to respository", response = PostPurchaseOrderResponse.class),
    @ApiResponse(code = 501, message = "Unknown Exception, investigation needed", response = PostPurchaseOrderResponse.class)})
    public ResponseEntity<PostPurchaseOrderResponse> createPurchaseOrder(@RequestBody @Valid PostPurchaseOrderRequest postPurchaseOrderRequest) {

        PostPurchaseOrderResponse postPurchaseOrderResponse = new PostPurchaseOrderResponse();

        List<String> errors = postPurchaseOrderValidator.validate(postPurchaseOrderRequest);

        if (errors.size() > 0) {
            postPurchaseOrderResponse.setErrors(errors);
            return ResponseEntity.status(400).body(postPurchaseOrderResponse);
        }



        try {
            String purchaseOrderId = purchaseOrderService.createPurchaseOrder(postPurchaseOrderRequest.getPurchaseOrder());

            postPurchaseOrderResponse.setPurchaseOrderId(purchaseOrderId);

            return ResponseEntity.status(200).body(postPurchaseOrderResponse);

        } catch (DataSaveException dataSaveException) {

            errors.add(dataSaveException.getMessage());
            postPurchaseOrderResponse.setErrors(errors);

            return ResponseEntity.status(500).body(postPurchaseOrderResponse);
        } catch (Exception e) {
            errors.add(e.getMessage());
            postPurchaseOrderResponse.setErrors(errors);

            return ResponseEntity.status(501).body(postPurchaseOrderResponse);
        }

    }
}
