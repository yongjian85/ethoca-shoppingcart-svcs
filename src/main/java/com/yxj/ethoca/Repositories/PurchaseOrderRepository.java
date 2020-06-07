package com.yxj.ethoca.Repositories;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.dto.PurchaseOrder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_IN_PROGRESS;

@Repository
public class PurchaseOrderRepository {

    @Autowired
    MongoDatabase mongoDatabase;

    public String createPurchaseOrder (PurchaseOrder purchaseOrder) throws DataSaveException {

        try {
            MongoCollection<PurchaseOrder> collection = mongoDatabase.getCollection("PurchaseOrders", PurchaseOrder.class);

            ObjectId id = new ObjectId();

            purchaseOrder.setId(id);
            purchaseOrder.setStatus(PURCHASE_ORDER_STATUS_IN_PROGRESS);

            collection.insertOne(purchaseOrder);

            return id.toHexString();
        } catch (MongoWriteException e) {
            //todo: add logging
            System.out.println(e.getMessage());
            throw new DataSaveException();

        } catch (MongoWriteConcernException mongoWriteConcernException) {
            System.out.println(mongoWriteConcernException.getMessage());
            throw new DataSaveException();
            //todo: add logging
        } catch (MongoCommandException mongoCommandException) {
            //todo: add logging
            System.out.println(mongoCommandException.getMessage());
            throw new DataSaveException();
        } catch (MongoException mongoException) {
            //todo: add logging
            System.out.println(mongoException.getMessage());
            throw new DataSaveException();
        } catch (Exception e) {
            //todo: add logging
            System.out.println(e.getMessage());
            throw new DataSaveException();
        }

    }
}
