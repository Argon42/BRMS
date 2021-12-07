package ru.sfedu.brms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import ru.sfedu.brms.models.HistoryContent;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;

public class HistoryUtil {
    private static final Logger log = LogManager.getLogger(HistoryUtil.class);

    public static void saveToLog(HistoryContent content) {
        MongoClient mongoDB;
        try {
            String url = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_URL);
            String database = ConfigurationUtil.getConfigurationEntry(Constants.HISTORY_DATABASE);

            mongoDB = MongoClients.create(url);
            mongoDB.getDatabase(database)
                    .getCollection(database)
                    .insertOne(Document.parse(objectToString(content)));
            mongoDB.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    private static String objectToString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
