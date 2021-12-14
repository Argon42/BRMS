package ru.sfedu.brms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.HistoryContent;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;
import ru.sfedu.labs.MongoDB;

public class HistoryUtil {
    private static final Logger log = LogManager.getLogger(HistoryUtil.class);

    public static void saveToLog(HistoryContent content) {
        try {
            String url = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_URL);
            String database = ConfigurationUtil.getConfigurationEntry(Constants.HISTORY_DATABASE);
            new MongoDB(url, database).saveObject(content);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
