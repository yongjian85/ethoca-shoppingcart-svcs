package com.yxj.ethoca.services;

import com.yxj.ethoca.Exceptions.DataQueryException;
import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.Repositories.PurchaseOrderRepository;
import com.yxj.ethoca.dto.LineItem;
import com.yxj.ethoca.dto.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public String createPurchaseOrder (PurchaseOrder purchaseOrder) throws DataSaveException {
        return purchaseOrderRepository.createPurchaseOrder(purchaseOrder);
    }

    public PurchaseOrder retrieveMostRecentPurchaseOrder (String user) throws DataQueryException {
        return purchaseOrderRepository.retrieveMostRecentPurchaseOrder(user);
    }

    public boolean updatePurchaseOrder (String purchaseId, List<LineItem> lineItems) throws DataSaveException {
         return purchaseOrderRepository.updatePurchaseOrder(purchaseId, lineItems);
    }

}
