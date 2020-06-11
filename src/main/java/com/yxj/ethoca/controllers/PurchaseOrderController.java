package com.yxj.ethoca.controllers;

import com.yxj.ethoca.Exceptions.DataQueryException;
import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.Request.PostPurchaseOrderRequest;
import com.yxj.ethoca.Request.PutPurchaseOrderSaveRequest;
import com.yxj.ethoca.Response.BaseResponse;
import com.yxj.ethoca.Response.GetPurchaseOrderResponse;
import com.yxj.ethoca.Response.PostPurchaseOrderResponse;
import com.yxj.ethoca.dto.PurchaseOrder;
import com.yxj.ethoca.services.PurchaseOrderService;
import com.yxj.ethoca.validators.PostPurchaseOrderValidator;
import com.yxj.ethoca.validators.PutPurchaseOrderSaveValidator;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PostPurchaseOrderValidator postPurchaseOrderValidator;

    @Autowired
    private PutPurchaseOrderSaveValidator putPurchaseOrderSaveValidator;

    private static final Logger logger = LogManager.getLogger(PurchaseOrderController.class);



    /*
    Creates either an 'In Progress' purchaseOrder to be retrieved later (save function)
    or directly creates a 'Submitted' order, implying that the customer has directly purchased the item without an intermittent save
     */
    @PostMapping(value = "/purchaseOrder")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "confirmation that this order was successfully created", response = PostPurchaseOrderResponse.class),
    @ApiResponse(code = 400, message = "Payload body contained invalid data", response = MethodArgumentNotValidException.class),
    @ApiResponse(code = 500, message = "Unable to save data to respository", response = PostPurchaseOrderResponse.class),
    @ApiResponse(code = 501, message = "Unknown Exception, investigation needed", response = PostPurchaseOrderResponse.class)})
    public ResponseEntity<PostPurchaseOrderResponse> createPurchaseOrder(@RequestBody @Valid PostPurchaseOrderRequest postPurchaseOrderRequest) {

        logger.info(postPurchaseOrderRequest.toString());
        PostPurchaseOrderResponse postPurchaseOrderResponse = new PostPurchaseOrderResponse();

        List<String> errors = postPurchaseOrderValidator.validate(postPurchaseOrderRequest);

        if (errors.size() > 0) {
            postPurchaseOrderResponse.setErrors(errors);
            logger.error(postPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(400).body(postPurchaseOrderResponse);
        }



        try {
            String purchaseOrderId = purchaseOrderService.createPurchaseOrder(postPurchaseOrderRequest.getPurchaseOrder());

            postPurchaseOrderResponse.setPurchaseOrderId(purchaseOrderId);

            logger.info(postPurchaseOrderResponse);
            ThreadContext.clearMap();
            return ResponseEntity.status(200).body(postPurchaseOrderResponse);

        } catch (DataSaveException dataSaveException) {

            errors.add(dataSaveException.getMessage());
            postPurchaseOrderResponse.setErrors(errors);
            logger.error(postPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(500).body(postPurchaseOrderResponse);
        } catch (Exception e) {
            errors.add(e.getMessage());
            postPurchaseOrderResponse.setErrors(errors);
            logger.error(postPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(501).body(postPurchaseOrderResponse);
        }

    }

    /*
    Gets the most recent purchase order from the user that is in "In Progress" state
    If Authentication is completed, then we do not need to get the path variable from the Get Request
     */
    @GetMapping(value = "/purchaseOrder/{username}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "returns the latest 'In Progress' purchase order from the user", response = GetPurchaseOrderResponse.class),
            @ApiResponse(code = 400, message = "User name was not supplied in the request path", response = GetPurchaseOrderResponse.class),
            @ApiResponse(code = 404, message = "User does not have any 'In Progress' purchase orders", response = GetPurchaseOrderResponse.class),
            @ApiResponse(code = 500, message = "Unable to query data from respository", response = GetPurchaseOrderResponse.class),
            @ApiResponse(code = 501, message = "Unknown Exception, investigation needed", response = GetPurchaseOrderResponse.class)})

    public ResponseEntity<GetPurchaseOrderResponse> getMostRecentPurchaseOrder(@PathVariable String username) {
        GetPurchaseOrderResponse getPurchaseOrderResponse = new GetPurchaseOrderResponse();

        logger.info(String.format("/purchaseOrder/%s", username));

        List<String> errors = new ArrayList<>();

        if (null == username || username.isEmpty()) {
            errors.add("Username is required");
            getPurchaseOrderResponse.setErrors(errors);
            logger.error(getPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(400).body(getPurchaseOrderResponse);
        } else if (username.contains("<") || username.contains(">")) { //stopping any sort of scripting tag here
            errors.add("Username contains illegal characters");
            getPurchaseOrderResponse.setErrors(errors);
            logger.error(getPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(400).body(getPurchaseOrderResponse);
        }

        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.retrieveMostRecentPurchaseOrder(username);

            if (null == purchaseOrder) {
                errors.add("User does not have any 'In Progress' purchase orders");
                getPurchaseOrderResponse.setErrors(errors);
                logger.error(getPurchaseOrderResponse.toString());
                ThreadContext.clearMap();
                return ResponseEntity.status(404).body(getPurchaseOrderResponse);
            }

            getPurchaseOrderResponse.setPurchaseOrder(purchaseOrder);
            logger.info(getPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(200).body(getPurchaseOrderResponse);

        } catch (DataQueryException dataQueryException) {
            errors.add(dataQueryException.getMessage());
            getPurchaseOrderResponse.setErrors(errors);
            logger.error(getPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(500).body(getPurchaseOrderResponse);
        } catch (Exception e) {
            errors.add(e.getMessage());
            getPurchaseOrderResponse.setErrors(errors);
            logger.error(getPurchaseOrderResponse.toString());
            ThreadContext.clearMap();
            return ResponseEntity.status(501).body(getPurchaseOrderResponse);
        }


    }

    /*
    This endpoint serves two purposes:
    1: update the line items and allow the user to continue editing
    2: update the line items and submit the request as final
     */
    @PutMapping (value = "/purchaseOrder")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Purchase Order was updated successfully", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Payload body contained invalid data", response = MethodArgumentNotValidException.class),
            @ApiResponse(code = 404, message = "Purchase Order cannot be updated", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Unable to save data to repository", response = BaseResponse.class),
            @ApiResponse(code = 501, message = "Unknown Exception, investigation needed", response = BaseResponse.class)})

    public ResponseEntity<BaseResponse> savePurchaseOrder(@RequestBody @Valid PutPurchaseOrderSaveRequest putPurchaseOrderSaveRequest) {
        BaseResponse baseResponse = new BaseResponse();

        logger.info(putPurchaseOrderSaveRequest.toString());

        List<String> errors = new ArrayList<>();
        try {
            putPurchaseOrderSaveValidator.validate(putPurchaseOrderSaveRequest);
        } catch (Exception e) {
            errors.add(e.getMessage());
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(500).body(baseResponse);
        }

        if (errors.size() > 0) {
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(400).body(baseResponse);
        }


        try {

            boolean isDocumentUpdated = purchaseOrderService.updatePurchaseOrder(putPurchaseOrderSaveRequest.getPurchaseId(),
                                                        putPurchaseOrderSaveRequest.getLineItems(),
                                                        putPurchaseOrderSaveRequest.getStatus());
            if (isDocumentUpdated) {
                logger.info("completed");
                return ResponseEntity.status(200).body(baseResponse);
            } else {
                errors.add(String.format("Purchase Id: %s was not in 'In Progress' status", putPurchaseOrderSaveRequest.getPurchaseId()));
                baseResponse.setErrors(errors);
                logger.error(baseResponse.toString());
                return ResponseEntity.status(404).body(baseResponse);
            }

        } catch (DataSaveException dataQueryException) {
            errors.add(dataQueryException.getMessage());
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(500).body(baseResponse);
        } catch (Exception e) {
            errors.add(e.getMessage());
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(501).body(baseResponse);
        }


    }

    /*
    This endpoint cancels the purchase order by Id
    */
    @DeleteMapping (value = "/purchaseOrder/{purchaseId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Purchase Order was cancelled successfully", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "Payload body contained invalid data", response = MethodArgumentNotValidException.class),
            @ApiResponse(code = 404, message = "Purchase Order cannot be cancelled", response = BaseResponse.class),
            @ApiResponse(code = 500, message = "Unable to save data to repository", response = BaseResponse.class),
            @ApiResponse(code = 501, message = "Unknown Exception, investigation needed", response = BaseResponse.class)})

    public ResponseEntity<BaseResponse> cancelPurchaseOrder(@PathVariable String purchaseId) {
        BaseResponse baseResponse = new BaseResponse();

        logger.info(String.format("/purchaseOrder/%s", purchaseId));
        List<String> errors = new ArrayList<>();

        if (null == purchaseId || purchaseId.isEmpty()) {
            errors.add("purchaseId is required");
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(400).body(baseResponse);
        } else if (purchaseId.contains("<") || purchaseId.contains(">")) { //stopping any sort of scripting tag here
            errors.add("purchaseId contains illegal characters");
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(400).body(baseResponse);
        }


        try {

            boolean isDocumentUpdated = purchaseOrderService.cancelPurchaseOrder(purchaseId);
            if (isDocumentUpdated) {
                logger.info (String.format("Cancel completed for %s", purchaseId));
                return ResponseEntity.status(200).body(baseResponse);
            } else {
                errors.add(String.format("Purchase Id: %s was not in 'In Progress' status", purchaseId));
                baseResponse.setErrors(errors);
                logger.error (String.format("Cancel error for %s", purchaseId));
                logger.error(baseResponse.toString());
                return ResponseEntity.status(404).body(baseResponse);
            }

        } catch (DataSaveException dataQueryException) {
            errors.add(dataQueryException.getMessage());
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(500).body(baseResponse);
        } catch (Exception e) {
            errors.add(e.getMessage());
            baseResponse.setErrors(errors);
            logger.error(baseResponse.toString());
            return ResponseEntity.status(501).body(baseResponse);
        }


    }

}
