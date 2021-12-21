package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.HistoryUtil;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.HistoryContent;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.utils.Constants;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class DataProvider implements IDataProvider {

    private static final Logger log = LogManager.getLogger(DataProvider.class);

    public abstract void initDataSource();

    @Override
    public UUID saveRetail(Retail retail) {
        log.info("Create new retail: {}", retail);

        if (isIncorrectNewRetail(retail)) {
            log.error("Create retail error");
            saveHistory(createHistoryContent(retail, Result.ERROR));
            throw new IllegalArgumentException();
        }

        try {
            Retail createdRetail = save(retail);
            log.info("Retail created: {}", retail);
            saveHistory(createHistoryContent(createdRetail, Result.SUCCESS));
            return createdRetail.getId();
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(retail, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void deleteRetail(UUID id) {
        log.info("Delete retail: {}", id);

        Optional<Retail> retail = findRetailByID(id);
        if (retail.isEmpty()) {
            log.error("Retail not find: {}", id);
            saveHistory(createHistoryContent(retail, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }

        try {
            delete(retail.get());
            log.info("Retail deleted: {}", retail);
            saveHistory(createHistoryContent(retail, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(retail, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void editRetail(Retail retail) {
        log.info("Edit retail: {}", retail);

        try {
            Retail editedRetail = update(retail);
            log.info("Retail edited: {}", retail);
            saveHistory(createHistoryContent(editedRetail, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(retail, Result.ERROR));
            throw e;
        }
    }

    @Override
    public UUID saveRule(Rule rule) {
        log.info("Create new rule: {}", rule);

        if (isIncorrectNewRule(rule)) {
            log.error("Create rule error");
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw new IllegalArgumentException();
        }

        try {
            Rule createdRule = save(rule);
            log.info("Rule created: {}", rule);
            saveHistory(createHistoryContent(createdRule, Result.SUCCESS));
            return createdRule.getId();
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void deleteRule(UUID id) {
        log.info("Delete rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not find: {}", id);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }

        try {
            delete(rule.get());
            log.info("Rule deleted: {}", rule);
            saveHistory(createHistoryContent(rule, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void editRule(Rule rule) {
        log.info("Edit rule: {}", rule);

        try {
            Rule editedRule = update(rule);
            log.info("Rule edited: {}", rule);
            saveHistory(createHistoryContent(editedRule, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void enableRule(UUID id) {
        log.info("Enable rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not found: {}", id);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }
        rule.get().setEnable(true);
        log.info("Enable find rule: {}", rule);

        try {
            Rule editedRule = update(rule.get());
            log.info("Rule Enabled: {}", rule);
            saveHistory(createHistoryContent(editedRule, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void disableRule(UUID id) {
        log.info("Disable rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not found: {}", id);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }
        rule.get().setEnable(false);
        log.info("Disable find rule: {}", rule);

        try {
            Rule editedRule = update(rule.get());
            log.info("Rule Disabled: {}", editedRule);
            saveHistory(createHistoryContent(editedRule, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(rule, Result.ERROR));
            throw e;
        }
    }

    @Override
    public UUID saveCheck(StoreCheck check) {
        log.info("Create new check: {}", check);

        if (isIncorrectNewCheck(check)) {
            log.error("Create check error");
            saveHistory(createHistoryContent(check, Result.ERROR));
            throw new IllegalArgumentException();
        }

        try {
            StoreCheck createdCheck = save(check);
            log.info("Check created: {}", check);
            saveHistory(createHistoryContent(createdCheck, Result.SUCCESS));
            return createdCheck.getId();
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(check, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void deleteCheck(UUID id) {
        log.info("Delete check: {}", id);

        Optional<StoreCheck> check = findCheckByID(id);
        if (check.isEmpty()) {
            log.error("Check not find: {}", id);
            saveHistory(createHistoryContent(check, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }

        try {
            delete(check.get());
            log.info("Check deleted: {}", check);
            saveHistory(createHistoryContent(check, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(check, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void editCheck(StoreCheck check) {
        log.info("Edit check: {}", check);

        try {
            StoreCheck editedCheck = update(check);
            log.info("Check edited: {}", check);
            saveHistory(createHistoryContent(editedCheck, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(check, Result.ERROR));
            throw e;
        }
    }

    @Override
    public UUID saveCustomer(Customer customer) {
        log.info("Create new customer: {}", customer);

        if (isIncorrectNewCustomer(customer)) {
            log.error("Create customer error");
            saveHistory(createHistoryContent(customer, Result.ERROR));
            throw new IllegalArgumentException();
        }

        try {
            Customer createdCustomer = save(customer);
            log.info("Customer created: {}", customer);
            saveHistory(createHistoryContent(createdCustomer, Result.SUCCESS));
            return createdCustomer.getId();
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(customer, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void deleteCustomer(UUID id) {
        log.info("Delete customer: {}", id);

        Optional<Customer> customer = findCustomerByID(id);
        if (customer.isEmpty()) {
            log.error("Customer not find: {}", id);
            saveHistory(createHistoryContent(customer, Result.ERROR));
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, id));
        }

        try {
            delete(customer.get());
            log.info("Customer deleted: {}", customer);
            saveHistory(createHistoryContent(customer, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(customer, Result.ERROR));
            throw e;
        }
    }

    @Override
    public void editCustomer(Customer customer) {
        log.info("Edit rule: {}", customer);

        try {
            Customer editedCustomer = update(customer);
            log.info("Customer edited: {}", customer);
            saveHistory(createHistoryContent(editedCustomer, Result.SUCCESS));
        } catch (Exception e) {
            log.error(e);
            saveHistory(createHistoryContent(customer, Result.ERROR));
            throw e;
        }
    }

    protected abstract Retail save(Retail retail);

    protected abstract void delete(Retail retail);

    protected abstract Retail update(Retail retail);

    protected abstract List<StoreCheck> findAllChecksByCustomer(UUID id);

    protected abstract Customer update(Customer customer);

    protected abstract void delete(Customer customer);

    protected abstract void delete(StoreCheck check);

    protected abstract List<Customer> findAllCustomersByRetail(UUID id);

    protected abstract Customer save(Customer customer);

    protected abstract StoreCheck save(StoreCheck check);

    protected abstract StoreCheck update(StoreCheck check);

    protected abstract Rule update(Rule rule);

    protected abstract void delete(Rule rule);

    protected void saveHistory(HistoryContent historyContent) {
        HistoryUtil.saveToLog(historyContent);
    }

    protected abstract Rule save(Rule rule);

    protected HistoryContent createHistoryContent(Object object, Result result) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
        return new HistoryContent(UUID.randomUUID(),
                stackTrace.getClassName(),
                System.currentTimeMillis(),
                object,
                stackTrace.getMethodName(),
                Constants.DEFAULT_AUTHOR,
                result);
    }

    private boolean isIncorrectNewRetail(Retail retail) {
        if (retail == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        if (retail.getName() == null)
            throw new IllegalArgumentException(Constants.OBJECT_NAME_IS_NULL);

        if(retail.getId() == null)
            return false;

        return findRetailByID(retail.getId()).isPresent();
    }

    private boolean isIncorrectNewCustomer(Customer customer) {
        if (customer == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        if (customer.getName() == null)
            throw new IllegalArgumentException(Constants.OBJECT_NAME_IS_NULL);

        if(customer.getId() == null)
            return false;

        return findCustomerByID(customer.getId()).isPresent();
    }

    private boolean isIncorrectNewCheck(StoreCheck check) {
        if (check == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        if (check.getCost() < 0)
            throw new IllegalArgumentException(String.format(Constants.ARGUMENT_WITH_INCORRECT_FIELD, Constants.FIELD_STORE_CHECK_COST, check.getCost()));

        if (check.getCountOfGoods() <= 0)
            throw new IllegalArgumentException(String.format(Constants.ARGUMENT_WITH_INCORRECT_FIELD, Constants.FIELD_STORE_CHECK_COUNT_OF_GOODS, check.getCost()));

       if(check.getId() == null)
           return false;

        return findCheckByID(check.getId()).isPresent();
    }

    private boolean isIncorrectNewRule(Rule rule) {
        if (rule == null)
            throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        if (rule.getName() == null)
            throw new IllegalArgumentException(Constants.OBJECT_NAME_IS_NULL);

        if(rule.getId() == null)
            return false;

        return findRuleByID(rule.getId()).isPresent();
    }

}
