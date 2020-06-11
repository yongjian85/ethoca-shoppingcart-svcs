package com.yxj.ethoca.Repositories;

import com.mongodb.MongoCommandException;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.yxj.ethoca.Exceptions.DataQueryException;
import com.yxj.ethoca.Exceptions.DataSaveException;
import com.yxj.ethoca.controllers.PurchaseOrderController;
import com.yxj.ethoca.dto.LineItem;
import com.yxj.ethoca.dto.PurchaseOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;
import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_CANCELLED;
import static com.yxj.ethoca.Constants.Constants.PURCHASE_ORDER_STATUS_IN_PROGRESS;

@Repository
public class PurchaseOrderRepository {

    @Autowired
    MongoDatabase mongoDatabase;

    private static final Logger logger = LogManager.getLogger(PurchaseOrderRepository.class);


    public String createPurchaseOrder (PurchaseOrder purchaseOrder) throws DataSaveException {

        try {
            MongoCollection<PurchaseOrder> collection = mongoDatabase.getCollection("PurchaseOrders", PurchaseOrder.class);

            ObjectId id = new ObjectId();

            purchaseOrder.setId(id);
            //Could be a save or a direct purchase
            purchaseOrder.setStatus(purchaseOrder.getStatus());

            collection.insertOne(purchaseOrder);

            return id.toHexString();
        } catch (MongoWriteException e) {
            logger.error(e.getMessage());
            throw new DataSaveException();

        } catch (MongoWriteConcernException mongoWriteConcernException) {
            logger.error(mongoWriteConcernException.getMessage());
            throw new DataSaveException();

        } catch (MongoCommandException mongoCommandException) {
           logger.error(mongoCommandException.getErrorMessage());
            throw new DataSaveException();
        } catch (MongoException mongoException) {
            logger.error(mongoException.getMessage());
            throw new DataSaveException();
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new DataSaveException();
        }

    }

    public PurchaseOrder retrieveMostRecentPurchaseOrder (String username) throws DataQueryException {

        try {
            MongoCollection<PurchaseOrder> collection = mongoDatabase.getCollection("PurchaseOrders", PurchaseOrder.class);


            // we are making an assumption here that each user may only have a single 'In Progress' purchase order
            return collection.find(and(eq("purchaseOrderOwner", username), eq("status", PURCHASE_ORDER_STATUS_IN_PROGRESS))).first();

        } catch (MongoException mongoException) {

        logger.error(mongoException.getMessage());
            throw new DataQueryException();
        } catch (Exception e) {
           logger.error(e.getMessage());
            throw new DataQueryException();
        }

    }

    public boolean updatePurchaseOrder (String purchaseId, List<LineItem> lineItems, String status) throws DataSaveException {

        try {
            MongoCollection<PurchaseOrder> collection = mongoDatabase.getCollection("PurchaseOrders", PurchaseOrder.class);

            //we want to make sure that this particular document is still "In Progress" when we are doing the update
            //or else this record was already processed so we shouldnt update the lineitems
            UpdateResult updateResult = collection.updateOne (
                    and (eq("purchaseId", purchaseId), eq("status", PURCHASE_ORDER_STATUS_IN_PROGRESS)),
                    Updates.combine(Updates.set("lineItems", lineItems),
                            Updates.set("status", status)));

            if (updateResult.getMatchedCount() > 0) { // found the matching record
                return true;
            } else {
                return false;
            }

        } catch (MongoException mongoException) {
           logger.error(mongoException.getMessage());
            throw new DataSaveException();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSaveException();
        }

    }

    public boolean cancelPurchaseOrder (String purchaseId) throws DataSaveException {

        try {
            MongoCollection<PurchaseOrder> collection = mongoDatabase.getCollection("PurchaseOrders", PurchaseOrder.class);

            //we want to make sure that this particular document is still "In Progress" when we are doing the update
            //or else this record was already processed so we shouldnt update the lineitems
            UpdateResult updateResult = collection.updateOne (
                    and (eq("purchaseId", purchaseId), eq("status", PURCHASE_ORDER_STATUS_IN_PROGRESS)),
                            set("status", PURCHASE_ORDER_STATUS_CANCELLED));

            if (updateResult.getMatchedCount() > 0) { // found the matching record
                return true;
            } else {
                return false;
            }

        } catch (MongoException mongoException) {
           logger.error(mongoException.getMessage());
            throw new DataSaveException();
        } catch (Exception e) {
           logger.error(e.getMessage());
            throw new DataSaveException();
        }

    }
}
