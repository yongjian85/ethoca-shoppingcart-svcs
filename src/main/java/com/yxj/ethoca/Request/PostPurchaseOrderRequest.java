package com.yxj.ethoca.Request;

import com.sun.istack.internal.NotNull;
import com.yxj.ethoca.dto.PurchaseOrder;

import javax.validation.Valid;

public class PostPurchaseOrderRequest {

    @NotNull
    @Valid
    private PurchaseOrder purchaseOrder;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
