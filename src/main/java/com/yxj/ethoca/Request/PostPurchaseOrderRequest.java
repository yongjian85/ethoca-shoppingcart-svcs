package com.yxj.ethoca.Request;

import com.yxj.ethoca.dto.PurchaseOrder;

public class PostPurchaseOrderRequest {

    private PurchaseOrder purchaseOrder;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
