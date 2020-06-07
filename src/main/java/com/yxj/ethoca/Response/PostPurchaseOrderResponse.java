package com.yxj.ethoca.Response;

public class PostPurchaseOrderResponse extends BaseResponse {

    private String purchaseOrderId;

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}
