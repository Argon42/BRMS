package ru.sfedu.brms.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.rules.RuleByCountOfGoods;
import ru.sfedu.brms.models.rules.RuleByPurchaseCount;
import ru.sfedu.brms.models.rules.RuleByTime;

import java.sql.ResultSet;
import java.time.Instant;
import java.util.UUID;

public class SqlUtil {
    private static final Logger log = LogManager.getLogger(SqlUtil.class);

    public static String createTableCheck() {
        return String.format(Constants.SQL_CREATE_TABLE_CHECK,
                StoreCheck.class.getSimpleName(),
                Constants.FIELD_STORE_CHECK_ID,
                Constants.FIELD_STORE_CHECK_TIME,
                Constants.FIELD_STORE_CHECK_COST,
                Constants.FIELD_STORE_CHECK_COUNT_OF_GOODS,
                Constants.FIELD_STORE_CHECK_CUSTOMER_ID
        );
    }

    public static String createTableCustomer() {
        return String.format(Constants.SQL_CREATE_TABLE_CUSTOMER,
                Customer.class.getSimpleName(),
                Constants.FIELD_CUSTOMER_ID,
                Constants.FIELD_CUSTOMER_NAME,
                Constants.FIELD_CUSTOMER_PHONE_NUMBER,
                Constants.FIELD_CUSTOMER_EMAIL,
                Constants.FIELD_CUSTOMER_RETAIL_ID
        );
    }

    public static String createTableRetail() {
        return String.format(Constants.SQL_CREATE_TABLE_RETAIL,
                Retail.class.getSimpleName(),
                Constants.FIELD_RETAIL_ID,
                Constants.FIELD_RETAIL_NAME,
                Constants.FIELD_RETAIL_COUNT_OF_STORES
        );
    }

    public static String createTableRuleByPurchaseCount() {
        return String.format(Constants.SQL_CREATE_TABLE_RULE_BY_PURCHASE_COUNT,
                RuleByPurchaseCount.class.getSimpleName(),
                Constants.FIELD_RULE_ID,
                Constants.FIELD_RULE_NAME,
                Constants.FIELD_RULE_DESCRIPTION,
                Constants.FIELD_RULE_RETAIL_ID,
                Constants.FIELD_RULE_BY_PURCHASE_COUNT_MINIMAL_COST,
                Constants.FIELD_RULE_BY_PURCHASE_COUNT_DISCOUNT_PERCENT
        );
    }

    public static String createTableRuleByCountOfGoods() {
        return String.format(Constants.SQL_CREATE_TABLE_RULE_BY_COUNT_OF_GOODS,
                RuleByCountOfGoods.class.getSimpleName(),
                Constants.FIELD_RULE_ID,
                Constants.FIELD_RULE_NAME,
                Constants.FIELD_RULE_DESCRIPTION,
                Constants.FIELD_RULE_RETAIL_ID,
                Constants.FIELD_RULE_BY_COUNT_OF_GOODS_MINIMAL_COUNT_OF_GOODS,
                Constants.FIELD_RULE_BY_COUNT_OF_GOODS_DISCOUNT
        );
    }

    public static String createTableRuleByTime() {
        return String.format(Constants.SQL_CREATE_TABLE_RULE_BY_TIME,
                RuleByTime.class.getSimpleName(),
                Constants.FIELD_RULE_ID,
                Constants.FIELD_RULE_NAME,
                Constants.FIELD_RULE_DESCRIPTION,
                Constants.FIELD_RULE_RETAIL_ID,
                Constants.FIELD_RULE_BY_TIME_START,
                Constants.FIELD_RULE_BY_TIME_END,
                Constants.FIELD_RULE_BY_TIME_DISCOUNT
        );
    }

    public static String create(RuleByPurchaseCount rule) {
        return String.format(Constants.SQL_INSERT_RULE_BY_PURCHASE_COUNT,
                rule.getRuleType().getRuleClass().getSimpleName(),
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getRetailId(),
                rule.getMinimalCost(),
                rule.getDiscountPercent()
        );
    }

    public static String create(RuleByCountOfGoods rule) {
        return String.format(Constants.SQL_INSERT_RULE_BY_COUNT_OF_GOODS,
                rule.getRuleType().getRuleClass().getSimpleName(),
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getRetailId(),
                rule.getMinimalCountOfGoods(),
                rule.getDiscount()
        );
    }

    public static String create(RuleByTime rule) {
        return String.format(Constants.SQL_INSERT_RULE_BY_TIME,
                rule.getRuleType().getRuleClass().getSimpleName(),
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getRetailId(),
                rule.getStartTime(),
                rule.getEndTime(),
                rule.getDiscount()
        );
    }

    public static String create(StoreCheck check) {
        return String.format(Constants.SQL_INSERT_STORE_CHECK,
                StoreCheck.class.getSimpleName(),
                check.getId(),
                check.getTime(),
                check.getCost(),
                check.getCountOfGoods(),
                check.getCustomerId()
        );
    }

    public static String create(Customer customer) {
        return String.format(Constants.SQL_INSERT_CUSTOMER,
                Customer.class.getSimpleName(),
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getEmail(),
                customer.getRetailId()
        );
    }

    public static String create(Retail retail) {
        return String.format(Constants.SQL_INSERT_RETAIL,
                Retail.class.getSimpleName(),
                retail.getId(),
                retail.getName(),
                retail.getCountOfStores()
        );
    }

    public static String delete(RuleByPurchaseCount rule) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                rule.getRuleType().getRuleClass().getSimpleName(), rule.getId()
        );
    }

    public static String delete(RuleByCountOfGoods rule) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                rule.getRuleType().getRuleClass().getSimpleName(), rule.getId()
        );
    }

    public static String delete(RuleByTime rule) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                rule.getRuleType().getRuleClass().getSimpleName(), rule.getId()
        );
    }

    public static String delete(StoreCheck check) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                StoreCheck.class.getSimpleName(), check.getId()
        );
    }

    public static String delete(Customer customer) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                Customer.class.getSimpleName(), customer.getId()
        );
    }

    public static String delete(Retail retail) {
        return String.format(Constants.SQL_DELETE_WHERE_ID,
                Retail.class.getSimpleName(), retail.getId()
        );
    }

    public static String update(RuleByPurchaseCount rule) {
        return String.format(Constants.SQL_UPDATE_RULE_BY_PURCHASE_COUNT,
                rule.getRuleType().getRuleClass().getSimpleName(),
                Constants.FIELD_RULE_NAME, rule.getName(),
                Constants.FIELD_RULE_DESCRIPTION, rule.getDescription(),
                Constants.FIELD_RULE_RETAIL_ID, rule.getRetailId(),
                Constants.FIELD_RULE_BY_PURCHASE_COUNT_MINIMAL_COST, rule.getMinimalCost(),
                Constants.FIELD_RULE_BY_PURCHASE_COUNT_DISCOUNT_PERCENT, rule.getDiscountPercent(),
                rule.getId()
        );
    }

    public static String update(RuleByCountOfGoods rule) {
        return String.format(Constants.SQL_UPDATE_RULE_BY_COUNT_OF_GOODS,
                rule.getRuleType().getRuleClass().getSimpleName(),
                Constants.FIELD_RULE_NAME, rule.getName(),
                Constants.FIELD_RULE_DESCRIPTION, rule.getDescription(),
                Constants.FIELD_RULE_RETAIL_ID, rule.getRetailId(),
                Constants.FIELD_RULE_BY_COUNT_OF_GOODS_MINIMAL_COUNT_OF_GOODS, rule.getMinimalCountOfGoods(),
                Constants.FIELD_RULE_BY_COUNT_OF_GOODS_DISCOUNT, rule.getDiscount(),
                rule.getId()
        );
    }

    public static String update(RuleByTime rule) {
        return String.format(Constants.SQL_UPDATE_RULE_BY_TIME,
                rule.getRuleType().getRuleClass().getSimpleName(),
                Constants.FIELD_RULE_NAME, rule.getName(),
                Constants.FIELD_RULE_DESCRIPTION, rule.getDescription(),
                Constants.FIELD_RULE_RETAIL_ID, rule.getRetailId(),
                Constants.FIELD_RULE_BY_TIME_START, rule.getStartTime(),
                Constants.FIELD_RULE_BY_TIME_END, rule.getEndTime(),
                Constants.FIELD_RULE_BY_TIME_DISCOUNT, rule.getDiscount(),
                rule.getId()
        );
    }

    public static String update(StoreCheck check) {
        return String.format(Constants.SQL_UPDATE_CHECK,
                StoreCheck.class.getSimpleName(),
                Constants.FIELD_STORE_CHECK_TIME, check.getTime(),
                Constants.FIELD_STORE_CHECK_COST, check.getCost(),
                Constants.FIELD_STORE_CHECK_COUNT_OF_GOODS, check.getCountOfGoods(),
                Constants.FIELD_STORE_CHECK_CUSTOMER_ID, check.getCustomerId(),
                check.getId()
        );
    }

    public static String update(Customer customer) {
        return String.format(Constants.SQL_UPDATE_CUSTOMER,
                Customer.class.getSimpleName(),
                Constants.FIELD_CUSTOMER_NAME, customer.getName(),
                Constants.FIELD_CUSTOMER_PHONE_NUMBER, customer.getPhoneNumber(),
                Constants.FIELD_CUSTOMER_EMAIL, customer.getEmail(),
                Constants.FIELD_CUSTOMER_RETAIL_ID, customer.getRetailId(),
                customer.getId()
        );
    }

    public static String update(Retail retail) {
        return String.format(Constants.SQL_UPDATE_RETAIL,
                Retail.class.getSimpleName(),
                Constants.FIELD_RETAIL_NAME, retail.getName(),
                Constants.FIELD_RETAIL_COUNT_OF_STORES, retail.getCountOfStores(),
                retail.getId()
        );
    }

    public static String selectRetailWithId(UUID id) {
        return String.format(Constants.SQL_SELECT_ALL_FROM_WHERE_ID,
                Retail.class.getSimpleName(), id
        );
    }

    public static String selectCustomerWithId(UUID id) {
        return String.format(Constants.SQL_SELECT_ALL_FROM_WHERE_ID,
                Customer.class.getSimpleName(), id
        );
    }

    public static String selectCheckWithId(UUID id) {
        return String.format(Constants.SQL_SELECT_ALL_FROM_WHERE_ID,
                StoreCheck.class.getSimpleName(), id
        );
    }

    public static String selectRulesByTime() {
        return String.format(Constants.SQL_SELECT_ALL_FROM, RuleByTime.class.getSimpleName());
    }

    public static String selectRulesByPurchaseCount() {
        return String.format(Constants.SQL_SELECT_ALL_FROM, RuleByPurchaseCount.class.getSimpleName());
    }

    public static String selectRulesByCountOfGoods() {
        return String.format(Constants.SQL_SELECT_ALL_FROM, RuleByCountOfGoods.class.getSimpleName());
    }

    public static String selectChecksWithCustomerId(UUID id) {
        return String.format(Constants.SQL_SELECT_CHECKS_WITH_CUSTOMER_ID,
                StoreCheck.class.getSimpleName(), id
        );
    }

    public static RuleByTime readRuleByTime(ResultSet set) {
        var rule = new RuleByTime();
        try {
            rule.setId(UUID.fromString(set.getString(Constants.FIELD_RULE_ID)));
            rule.setName(set.getString(Constants.FIELD_RULE_NAME));
            rule.setDescription(set.getString(Constants.FIELD_RULE_DESCRIPTION));
            rule.setRetailId(UUID.fromString(set.getString(Constants.FIELD_RULE_RETAIL_ID)));
            rule.setStartTime(Instant.parse(set.getString(Constants.FIELD_RULE_BY_TIME_START)));
            rule.setEndTime(Instant.parse(set.getString(Constants.FIELD_RULE_BY_TIME_END)));
            rule.setDiscount(set.getFloat(Constants.FIELD_RULE_BY_TIME_DISCOUNT));
        } catch (Exception e) {
            log.error(e);
        }
        return rule;
    }

    public static RuleByPurchaseCount readRuleByPurchase(ResultSet set) {
        var rule = new RuleByPurchaseCount();
        try {
            rule.setId(UUID.fromString(set.getString(Constants.FIELD_RULE_ID)));
            rule.setName(set.getString(Constants.FIELD_RULE_NAME));
            rule.setDescription(set.getString(Constants.FIELD_RULE_DESCRIPTION));
            rule.setRetailId(UUID.fromString(set.getString(Constants.FIELD_RULE_RETAIL_ID)));
            rule.setMinimalCost(set.getFloat(Constants.FIELD_RULE_BY_PURCHASE_COUNT_MINIMAL_COST));
            rule.setDiscountPercent(set.getFloat(Constants.FIELD_RULE_BY_PURCHASE_COUNT_DISCOUNT_PERCENT));
        } catch (Exception e) {
            log.error(e);
        }
        return rule;
    }

    public static RuleByCountOfGoods readRuleByCountOfGoods(ResultSet set) {
        var rule = new RuleByCountOfGoods();
        try {
            rule.setId(UUID.fromString(set.getString(Constants.FIELD_RULE_ID)));
            rule.setName(set.getString(Constants.FIELD_RULE_NAME));
            rule.setDescription(set.getString(Constants.FIELD_RULE_DESCRIPTION));
            rule.setRetailId(UUID.fromString(set.getString(Constants.FIELD_RULE_RETAIL_ID)));
            rule.setMinimalCountOfGoods(set.getInt(Constants.FIELD_RULE_BY_COUNT_OF_GOODS_MINIMAL_COUNT_OF_GOODS));
            rule.setDiscount(set.getInt(Constants.FIELD_RULE_BY_COUNT_OF_GOODS_DISCOUNT));
        } catch (Exception e) {
            log.error(e);
        }
        return rule;
    }

    public static StoreCheck readCheck(ResultSet set) {
        StoreCheck check = new StoreCheck();
        try {
            check.setId(UUID.fromString(set.getString(Constants.FIELD_STORE_CHECK_ID)));
            check.setTime(Instant.parse(set.getString(Constants.FIELD_STORE_CHECK_TIME)));
            check.setCost(set.getFloat(Constants.FIELD_STORE_CHECK_COST));
            check.setCountOfGoods(set.getInt(Constants.FIELD_STORE_CHECK_COUNT_OF_GOODS));
            check.setCustomerId(UUID.fromString(set.getString(Constants.FIELD_STORE_CHECK_CUSTOMER_ID)));
        } catch (Exception e) {
            log.error(e);
        }
        return check;
    }

    public static Customer readCustomer(ResultSet set) {
        Customer customer = new Customer();
        try {
            customer.setId(UUID.fromString(set.getString(Constants.FIELD_CUSTOMER_ID)));
            customer.setName(set.getString(Constants.FIELD_CUSTOMER_NAME));
            customer.setPhoneNumber(set.getString(Constants.FIELD_CUSTOMER_PHONE_NUMBER));
            customer.setEmail(set.getString(Constants.FIELD_CUSTOMER_EMAIL));
            customer.setRetailId(UUID.fromString(set.getString(Constants.FIELD_CUSTOMER_RETAIL_ID)));
        } catch (Exception e) {
            log.error(e);
        }
        return customer;
    }

    public static Retail readRetail(ResultSet set) {
        Retail retail = new Retail();
        try {
            retail.setId(UUID.fromString(set.getString(Constants.FIELD_RETAIL_ID)));
            retail.setName(set.getString(Constants.FIELD_RETAIL_NAME));
            retail.setCountOfStores(set.getInt(Constants.FIELD_RETAIL_COUNT_OF_STORES));
        } catch (Exception e) {
            log.error(e);
        }
        return retail;
    }

}
