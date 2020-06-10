package com.yxj.ethoca.validators;

import com.yxj.ethoca.Request.PostPurchaseOrderRequest;
import com.yxj.ethoca.dto.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_IN_PROGRESS;
import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_SUBMITTED;

@Component
public class PostPurchaseOrderValidator {

    @Autowired
    private CommonPurchaseOrderValidator commonPurchaseOrderValidator;

    public List<String> validate (PostPurchaseOrderRequest postPurchaseOrderRequest) {

        //TODO: check the Products cache to see if the products in the lineitems are legit

        List<String> errorList = new ArrayList<>();

        errorList.addAll(commonPurchaseOrderValidator.validateLineItems(postPurchaseOrderRequest.getPurchaseOrder().getLineItems(),
                            postPurchaseOrderRequest.getPurchaseOrder().getStatus()));


        return errorList;
    }
}
