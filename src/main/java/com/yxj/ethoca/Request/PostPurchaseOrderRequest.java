package com.yxj.ethoca.Request;

import com.yxj.ethoca.dto.PurchaseOrder;

import javax.validation.Valid;

public class PostPurchaseOrderRequest {

    @Valid
    private PurchaseOrder purchaseOrder;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
