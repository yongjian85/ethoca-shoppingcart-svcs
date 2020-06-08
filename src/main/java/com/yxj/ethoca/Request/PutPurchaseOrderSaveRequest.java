package com.yxj.ethoca.Request;

import com.yxj.ethoca.dto.LineItem;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.yxj.ethoca.Constants.Constants.*;

public class PutPurchaseOrderSaveRequest {

    @NotBlank
    @Pattern(regexp=PAYLOAD_VALIDATION_ALPHANUMERICS, message= "Purchase ID must only contain alphaNumerics")
    private String purchaseId;

    @NotNull
    @Valid
    @Size(min = 1, message = "Line Items must contain at least 1 item")
    private List<LineItem> lineItems;

    @NotBlank
    @Pattern(regexp= "(In Progress)|(Submitted)", message= "status must be valid")
    private String status;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
