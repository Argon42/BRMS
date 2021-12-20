package ru.sfedu.brms.utils;

public class Constants {
    public static final String DEFAULT_AUTHOR = "system";
    public static final String CSV_PATH = "csv.path";
    public static final String FILE_EXTENSION_CSV = "csv.fileExtension";

    public static final String XML_PATH = "xml.path";
    public static final String FILE_EXTENSION_XML = "xml.fileExtension";

    public static final String MONGO_URL = "historyUtil.url";
    public static final String HISTORY_DATABASE = "historyUtil.mongoDatabase";

    public static final String JDBC_URL = "jdbc.url";
    public static final String JDBC_USERNAME = "jdbc.username";
    public static final String JDBC_PASSWORD = "jdbc.password";
    public static final String DB_DRIVER = "jdbc.driver";


    public static final String ARGUMENT_IS_NULL = "Argument is Null";
    public static final String OBJECT_WITH_ID_NOT_FOUND_EXCEPTION = "Object with id: \"%s\" not founded";
    public static final String ARGUMENT_WITH_INCORRECT_FIELD = "Argument contains incorrect field.\n%s:%s";
    public static final String OBJECT_NAME_IS_NULL = "The object Name is Null";


    public static final String FIELD_RULE_ID = "id";
    public static final String FIELD_RULE_NAME = "name";
    public static final String FIELD_RULE_DESCRIPTION = "description";

    public static final String FIELD_RULE_BY_TIME_START = "startTime";
    public static final String FIELD_RULE_BY_TIME_END = "endTime";
    public static final String FIELD_RULE_BY_TIME_DISCOUNT = "discount";

    public static final String FIELD_RULE_BY_PURCHASE_COUNT_MINIMAL_COST = "minimalCost";
    public static final String FIELD_RULE_BY_PURCHASE_COUNT_DISCOUNT_PERCENT = "discountPercent";

    public static final String FIELD_RULE_BY_COUNT_OF_GOODS_MINIMAL_COUNT_OF_GOODS = "minimalCountOfGoods";
    public static final String FIELD_RULE_BY_COUNT_OF_GOODS_DISCOUNT = "discount";

    public static final String FIELD_CUSTOMER_ID = "id";
    public static final String FIELD_CUSTOMER_NAME = "name";
    public static final String FIELD_CUSTOMER_PHONE_NUMBER = "phoneNumber";
    public static final String FIELD_CUSTOMER_EMAIL = "email";
    public static final String FIELD_CUSTOMER_RETAIL_ID = "retailId";

    public static final String FIELD_STORE_CHECK_ID = "id";
    public static final String FIELD_STORE_CHECK_TIME = "time";
    public static final String FIELD_STORE_CHECK_COST = "cost";
    public static final String FIELD_STORE_CHECK_COUNT_OF_GOODS = "countOfGoods";
    public static final String FIELD_STORE_CHECK_CUSTOMER_ID = "customerId";

    public static final String FIELD_RETAIL_ID = "id";
    public static final String FIELD_RETAIL_NAME = "name";
    public static final String FIELD_RETAIL_COUNT_OF_STORES = "countOfStores";


    public static final String SQL_INSERT_RULE_BY_PURCHASE_COUNT = "INSERT INTO %s VALUES('%s', '%s', '%s', %.00f, %.00f);";
    public static final String SQL_INSERT_RULE_BY_COUNT_OF_GOODS = "INSERT INTO %s VALUES('%s', '%s', '%s', %d, %d);";
    public static final String SQL_INSERT_RULE_BY_TIME = "INSERT INTO %s VALUES('%s', '%s', '%s', '%s', '%s', %.00f);";
    public static final String SQL_INSERT_STORE_CHECK = "INSERT INTO %s VALUES('%s', '%s', %.00f, %d, '%s');";
    public static final String SQL_INSERT_CUSTOMER = "INSERT INTO %s VALUES('%s', '%s', '%s', '%s', '%s');";
    public static final String SQL_INSERT_RETAIL = "INSERT INTO %s VALUES('%s', '%s', %d);";

    public static final String SQL_SELECT_ALL_FROM = "SELECT * FROM %s;";
    public static final String SQL_SELECT_ALL_FROM_WHERE_ID = "SELECT * FROM %s WHERE id='%s';";
    public static final String SQL_SELECT_CHECKS_WITH_CUSTOMER_ID = "SELECT * FROM %s WHERE customerId='%s';";

    public static final String SQL_DELETE_WHERE_ID = "DELETE FROM %s WHERE id = '%s';";

    public static final String SQL_UPDATE_RULE_BY_PURCHASE_COUNT = "UPDATE %s SET %s='%s', %s='%s', %s='%s', %s=%.00f, %s=%.000f;";
    public static final String SQL_UPDATE_RULE_BY_COUNT_OF_GOODS = "UPDATE %s SET %s='%s', %s='%s', %s='%s', %s=%d, %s=%d;";
    public static final String SQL_UPDATE_RULE_BY_TIME = "UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s='%s', %s=%.000f;";
    public static final String SQL_UPDATE_CHECK = "UPDATE %s SET %s='%s', %s='%s', %s=%.00f, %s=%d, %s='%s';";
    public static final String SQL_UPDATE_CUSTOMER = "UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s', %s='%s';";
    public static final String SQL_UPDATE_RETAIL = "UPDATE %s SET %s='%s', %s='%s', %s=%d;";

    public static final String SQL_CREATE_TABLE_CHECK =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s DOUBLE PRECISION, " +
                    "%s INT, " +
                    "%s VARCHAR(36)" +
                    ");";
    public static final String SQL_CREATE_TABLE_CUSTOMER =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255)" +
                    ");";
    public static final String SQL_CREATE_TABLE_RETAIL =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s INT" +
                    ");";
    public static final String SQL_CREATE_TABLE_RULE_BY_TIME =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s INT" +
                    ");";
    public static final String SQL_CREATE_TABLE_RULE_BY_PURCHASE_COUNT =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s DOUBLE PRECISION, " +
                    "%s DOUBLE PRECISION" +
                    ");";
    public static final String SQL_CREATE_TABLE_RULE_BY_COUNT_OF_GOODS =
            "CREATE TABLE IF NOT EXISTS %s(" +
                    "%s VARCHAR(36) PRIMARY KEY, " +
                    "%s VARCHAR(255), " +
                    "%s VARCHAR(255), " +
                    "%s INT, " +
                    "%s INT" +
                    ");";
}
