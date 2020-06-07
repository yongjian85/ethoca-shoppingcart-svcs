package com.yxj.ethoca.services;

import com.yxj.ethoca.Exceptions.DataQueryException;
import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.Repositories.PurchaseOrderRepository;
import com.yxj.ethoca.dto.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
