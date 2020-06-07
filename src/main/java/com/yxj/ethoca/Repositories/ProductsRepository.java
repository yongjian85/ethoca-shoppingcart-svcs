package com.yxj.ethoca.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.yxj.ethoca.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductsRepository {

    @Autowired
    MongoDatabase mongoDatabase;

    public List<Product> getProducts() {

        MongoCollection<Product> productsCollection = mongoDatabase.getCollection("products", Product.class);

        return productsCollection.find().into(new ArrayList<Product>());



    }
}
