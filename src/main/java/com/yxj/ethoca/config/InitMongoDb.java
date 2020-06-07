package com.yxj.ethoca.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class InitMongoDb {

    @Autowired
    private MongoClient mongoClient;

    @Value("${mongo.dbName}")
    private String mongoDbName;

    @Bean
    public com.mongodb.client.MongoDatabase getMongoDatabase () {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));


        com.mongodb.client.MongoDatabase mongoDatabase = mongoClient.getDatabase(mongoDbName).withCodecRegistry(pojoCodecRegistry);

        return mongoDatabase;







    }

}
