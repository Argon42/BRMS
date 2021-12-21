package ru.sfedu.labs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.result.InsertOneResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import ru.sfedu.brms.utils.Constants;

public class MongoDB {
    private final Logger log = LogManager.getLogger(MongoDB.class);
    private String url;
    private String database;

    public MongoDB() {
    }

    public MongoDB(String url, String database) {
        this.url = url;
        this.database = database;
    }

    public static String objectToString(Object object) throws JsonProcessingException {
        return objectMapper().writeValueAsString(object);
    }

    public static <T> T stringToObject(String json, Class<T> objectType) throws JsonProcessingException {
        return objectMapper().readValue(json, objectType);
    }

    private static ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public <T> void deleteObject(T object) throws JsonProcessingException {
        if (object == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        try {
            MongoClient mongoDB = MongoClients.create(url);
            Document document = mongoDB.getDatabase(database)
                    .getCollection(database)
                    .findOneAndDelete(Document.parse(objectToString(object)));
            mongoDB.close();
            if (document == null)
                throw new IllegalArgumentException("Object not found in db");
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    public void drop() {
        MongoClient mongoDB;
        try {
            mongoDB = MongoClients.create(url);
            mongoDB.getDatabase(database)
                    .drop();
            mongoDB.close();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    public <T> void saveObject(T object) throws JsonProcessingException {
        if (object == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        try {
            MongoClient mongoDB = MongoClients.create(url);
            InsertOneResult insertOneResult = mongoDB.getDatabase(database)
                    .getCollection(database)
                    .insertOne(Document.parse(objectToString(object)));
            mongoDB.close();
            if (!insertOneResult.wasAcknowledged())
                throw new IllegalArgumentException();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
