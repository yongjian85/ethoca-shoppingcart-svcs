package com.yxj.ethoca.validators;

import com.yxj.ethoca.Request.PostPurchaseOrderRequest;
import com.yxj.ethoca.dto.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostPurchaseOrderValidator {

    @Autowired
    private CommonPurchaseOrderValidator commonPurchaseOrderValidator;

    public List<String> validate (PostPurchaseOrderRequest postPurchaseOrderRequest) {

        //TODO: check the Products cache to see if the products in the lineitems are legit

        List<String> errorList = new ArrayList<>();

        errorList.addAll(commonPurchaseOrderValidator.validateLineItems(postPurchaseOrderRequest.getPurchaseOrder().getLineItems()));

        return errorList;
    }
}
