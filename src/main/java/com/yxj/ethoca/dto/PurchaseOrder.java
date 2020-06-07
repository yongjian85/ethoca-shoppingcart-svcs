package com.yxj.ethoca.dto;

import org.bson.types.ObjectId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class PurchaseOrder {

    @Valid
    private List<LineItem> lineItems; //list of items in this purchase order
    private ObjectId id;

    @NotBlank
    private String purchaseOrderOwner; // person who placed this order
    private String status; //status of this order: ie: inProgress / submitted / cancelled

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPurchaseId() {
        return id.toHexString();
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }


    public String getPurchaseOrderOwner() {
        return this.purchaseOrderOwner;
    }

    public void setPurchaseOrderOwner(String purchaseOrderOwner) {
        this.purchaseOrderOwner = purchaseOrderOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}