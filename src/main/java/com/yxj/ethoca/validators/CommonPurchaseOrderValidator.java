package com.yxj.ethoca.validators;

import com.yxj.ethoca.dto.LineItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonPurchaseOrderValidator {

    public List<String> validateLineItems (List<LineItem> lineitems) {

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

        return errorList;
    }
}
