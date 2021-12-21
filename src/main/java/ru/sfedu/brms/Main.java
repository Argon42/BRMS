package ru.sfedu.brms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.dataProviders.CSVDataProvider;
import ru.sfedu.brms.dataProviders.IDataProvider;
import ru.sfedu.brms.dataProviders.JdbcDataProvider;
import ru.sfedu.brms.dataProviders.XMLDataProvider;
import ru.sfedu.brms.models.enums.CliCommand;
import ru.sfedu.brms.utils.Constants;

import java.util.UUID;

public class Main {
    public static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        IDataProvider dataProvider;
        switch (args[0]) {
            case Constants.MAIN_CSV:
                dataProvider = new CSVDataProvider();
                break;
            case Constants.MAIN_XML:
                dataProvider = new XMLDataProvider();
                break;
            case Constants.MAIN_JDBC:
                dataProvider = new JdbcDataProvider();
                break;
            default:
                System.out.println(Constants.MAIN_INCORRECT_ARGS);
                return;
        }
        dataProvider.dropAll();
        dataProvider.initDataSource();
        new DataSourceGenerator().generate(dataProvider);

        System.out.println(getAnswer(dataProvider, args));
    }

    public static String getAnswer(IDataProvider dataProvider, String[] args) {
        try {
            switch (CliCommand.valueOf(args[1].toUpperCase())) {
                case SEARCH_ALL_RULES:
                    return String.valueOf(dataProvider.searchAllRules());
                case SEARCH_AVAILABLE_RULES_BY_CHECK:
                    return String.valueOf(dataProvider.searchAvailableRules(UUID.fromString(args[2])));
                case SEARCH_AVAILABLE_RULES_BY_CHECK_AND_CUSTOMER:
                    return String.valueOf(dataProvider.searchAvailableRules(UUID.fromString(args[2]), UUID.fromString(args[3])));
                case DISPLAY_STATISTIC:
                    return String.valueOf(dataProvider.displayStatistic(args[2]));
                default:
                    log.error(Constants.MAIN_METHOD_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e);
        }
        return Constants.MAIN_END_MESSAGE;
    }

}
