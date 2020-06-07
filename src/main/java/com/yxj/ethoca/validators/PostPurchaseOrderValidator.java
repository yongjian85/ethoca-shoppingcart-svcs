package com.yxj.ethoca.validators;

import com.yxj.ethoca.Request.PostPurchaseOrderRequest;
import com.yxj.ethoca.dto.LineItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostPurchaseOrderValidator {

    public List<String> validate (PostPurchaseOrderRequest postPurchaseOrderRequest) {

        //TODO: check the Products cache to see if the products in the lineitems are legit

        List<String> errorList = new ArrayList<>();

        List<String> duplicateLineItemsProductIds = new ArrayList<>();

        for (LineItem lineItem: postPurchaseOrderRequest.getPurchaseOrder().getLineItems()) {

            //Checking to see if the given line item a repeat
            if (duplicateLineItemsProductIds.contains(lineItem.getProductId().trim())) {
                errorList.add(String.format("productId: %s with quantity: %d is a repeat of a previous lineitem", lineItem.getProductId().trim(), lineItem.getQuantity()));
            } else {
                duplicateLineItemsProductIds.add(lineItem.getProductId().trim());
            }
        }

        return errorList;
    }
}
