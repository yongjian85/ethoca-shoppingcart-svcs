package com.yxj.ethoca.config;



import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class InitMongoClient {

    @Value("${mongo.username}")
    private String mongoUserName;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.clusterAddress}")
    private String mongoClusterAddress;

    @Value("${mongo.dbName}")
    private String mongoDbName;


    @Bean
    public MongoClient initializeMongoClient() {



        return  MongoClients.create(
                String.format("mongodb+srv://%s:%s@%s/%s?w=majority&ssl=true",
                        mongoUserName,
                        mongoPassword,
                        mongoClusterAddress,
                        mongoDbName));



    }

}
