package com.yxj.ethoca.validators;

import com.yxj.ethoca.dto.LineItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_IN_PROGRESS;
import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_SUBMITTED;

@Component
public class CommonPurchaseOrderValidator {

    public List<String> validateLineItems (List<LineItem> lineitems, String status) {

        List<String> errorList = new ArrayList<>();

        List<String> duplicateLineItemsProductIds = new ArrayList<>();

        for (LineItem lineItem: lineitems) {

            //Checking to see if the given line item a repeat
            if (duplicateLineItemsProductIds.contains(lineItem.getProductId().trim())) {
                errorList.add(String.format("productId: %s with quantity: %d is a repeat of a previous lineitem", lineItem.getProductId().trim(), lineItem.getQuantity()));
            } else {
                duplicateLineItemsProductIds.add(lineItem.getProductId().trim());
            }
        }

         /*
        To make sure that in a new purchase order creation, that they are creating an 'In Progress' order, or
        creating a finalized order
         */
        if (!status.equals(PURCHASE_ORDER_STATUS_IN_PROGRESS) &&
        !status.equals(PURCHASE_ORDER_STATUS_SUBMITTED)) {

            errorList.add(String.format("Valid statuses include: %s and %s",
                    PURCHASE_ORDER_STATUS_IN_PROGRESS, PURCHASE_ORDER_STATUS_SUBMITTED));



        }

        return errorList;
    }
}
