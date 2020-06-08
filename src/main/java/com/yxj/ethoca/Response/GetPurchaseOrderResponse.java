package com.yxj.ethoca.Response;

import com.yxj.ethoca.dto.PurchaseOrder;

public class GetPurchaseOrderResponse extends BaseResponse {

    private PurchaseOrder purchaseOrder;

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
