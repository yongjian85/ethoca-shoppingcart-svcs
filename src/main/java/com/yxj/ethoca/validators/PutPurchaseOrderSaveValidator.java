package com.yxj.ethoca.validators;

import com.yxj.ethoca.Request.PutPurchaseOrderSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PutPurchaseOrderSaveValidator {

    @Autowired
    private CommonPurchaseOrderValidator commonPurchaseOrderValidator;

    public List<String> validate (PutPurchaseOrderSaveRequest putPurchaseOrderSaveRequest) {

        //TODO: check the Products cache to see if the products in the lineitems are legit

        List<String> errorList = new ArrayList<>();

        errorList.addAll(commonPurchaseOrderValidator.validateLineItems(putPurchaseOrderSaveRequest.getLineItems(),
                            putPurchaseOrderSaveRequest.getStatus()));

        return errorList;
    }

}
