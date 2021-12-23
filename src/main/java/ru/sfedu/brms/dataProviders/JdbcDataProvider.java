package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.models.rules.RuleByCountOfGoods;
import ru.sfedu.brms.models.rules.RuleByPurchaseCount;
import ru.sfedu.brms.models.rules.RuleByTime;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;
import ru.sfedu.brms.utils.SqlUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * The type Jdbc data provider.
 */
public class JdbcDataProvider extends DataProvider {
    private static final Logger log = LogManager.getLogger(JdbcDataProvider.class);

    private String url;
    private String username;
    private String password;

    public void initDataSource() {
        try {
            url = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_URL);
            username = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_USERNAME);
            password = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_PASSWORD);
            createTables();
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void dropAll() {
        try {
            url = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_URL);
            username = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_USERNAME);
            password = ConfigurationUtil.getConfigurationEntry(Constants.JDBC_PASSWORD);
        } catch (Exception e) {
            log.error(e);
        }
        execute(SqlUtil.dropTables(new String[]{
                Retail.class.getSimpleName(),
                StoreCheck.class.getSimpleName(),
                Customer.class.getSimpleName(),
                RuleByTime.class.getSimpleName(),
                RuleByPurchaseCount.class.getSimpleName(),
                RuleByCountOfGoods.class.getSimpleName()
        }));
    }

    @Override
    public List<Rule> searchAllRules() {
        List<Rule> ruleList = new ArrayList<>();
        executeQuery(SqlUtil.selectRulesByTime(), set ->
        {
            try {
                while (set != null && set.next()) {
                    ruleList.add(SqlUtil.readRuleByTime(set));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });

        executeQuery(SqlUtil.selectRulesByPurchaseCount(), set -> {
            try {
                while (set != null && set.next()) {
                    ruleList.add(SqlUtil.readRuleByPurchase(set));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });

        executeQuery(SqlUtil.selectRulesByCountOfGoods(), set -> {
            try {
                while (set != null && set.next()) {
                    ruleList.add(SqlUtil.readRuleByCountOfGoods(set));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });

        return ruleList.stream()
                .peek(rule -> findRetailByID(rule.getRetailId())
                        .ifPresent(rule::setRetail))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rule> findRuleByID(UUID id) {
        return searchAllRules()
                .stream()
                .filter(rule -> rule.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<StoreCheck> findCheckByID(UUID id) {
        AtomicReference<Optional<StoreCheck>> result = new AtomicReference<>(Optional.empty());
        executeQuery(SqlUtil.selectCheckWithId(id), set -> {
            try {
                while (set != null && set.next()) {
                    result.set(Optional.of(SqlUtil.readCheck(set)));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });
        return result.get();
    }

    @Override
    public Optional<Customer> findCustomerByID(UUID id) {
        AtomicReference<Optional<Customer>> customer = new AtomicReference<>(Optional.empty());
        executeQuery(SqlUtil.selectCustomerWithId(id), set -> {
            try {
                while (set != null && set.next()) {
                    customer.set(Optional.of(SqlUtil.readCustomer(set)));
                    customer.get().get().setChecks(findAllChecksByCustomer(customer.get().get().getId()));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });
        return customer.get();
    }

    @Override
    public Optional<Retail> findRetailByID(UUID id) {
        AtomicReference<Optional<Retail>> retailReference = new AtomicReference<>(Optional.empty());
        executeQuery(SqlUtil.selectRetailWithId(id), set -> {
            try {
                while (set != null && set.next()) {
                    var retail = SqlUtil.readRetail(set);
                    retail.setCustomers(findAllCustomersByRetail(retail.getId()));
                    retail.setChecks(retail.getCustomers().stream()
                            .flatMap(customer -> findAllChecksByCustomer(customer.getId()).stream())
                            .collect(Collectors.toList())
                    );
                    retailReference.set(Optional.of(retail));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });
        return retailReference.get();
    }

    @Override
    protected Retail save(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        retail.setId(UUID.randomUUID());
        execute(SqlUtil.create(retail));
        return retail;
    }

    @Override
    protected void delete(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.delete(retail));
    }

    @Override
    protected Retail update(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.update(retail));
        return retail;
    }

    @Override
    protected List<StoreCheck> findAllChecksByCustomer(UUID id) {
        List<StoreCheck> checks = new ArrayList<>();
        executeQuery(SqlUtil.selectChecksWithCustomerId(id), set -> {
            try {
                while (set != null && set.next()) {
                    checks.add(SqlUtil.readCheck(set));
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });
        return checks;
    }

    @Override
    protected Customer update(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.update(customer));
        return customer;
    }

    @Override
    protected void delete(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.delete(customer));
    }

    @Override
    protected void delete(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.delete(check));
    }

    @Override
    protected List<Customer> findAllCustomersByRetail(UUID id) {
        List<Customer> customers = new ArrayList<>();
        executeQuery(SqlUtil.selectCustomersWithRetailId(id), set -> {
            try {
                while (set != null && set.next()) {
                    Customer customer = SqlUtil.readCustomer(set);
                    customer.setChecks(findAllChecksByCustomer(customer.getId()));
                    customers.add(customer);
                }
            } catch (SQLException e) {
                log.error(e);
            }
        });
        return customers;
    }

    @Override
    protected Customer save(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        customer.setId(UUID.randomUUID());
        execute(SqlUtil.create(customer));
        return customer;
    }

    @Override
    protected StoreCheck save(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        check.setId(UUID.randomUUID());
        execute(SqlUtil.create(check));
        return check;
    }

    @Override
    protected StoreCheck update(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        execute(SqlUtil.update(check));
        return check;
    }

    @Override
    protected Rule update(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        switch (rule.getRuleType()) {
            case RULE_BY_PURCHASE_COUNT:
                execute(SqlUtil.update((RuleByPurchaseCount) rule));
                break;
            case RULE_BY_COUNT_OF_GOODS:
                execute(SqlUtil.update((RuleByCountOfGoods) rule));
                break;
            case RULE_BY_TIME:
                execute(SqlUtil.update((RuleByTime) rule));
                break;
        }
        return rule;
    }

    @Override
    protected void delete(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        switch (rule.getRuleType()) {
            case RULE_BY_PURCHASE_COUNT:
                execute(SqlUtil.delete((RuleByPurchaseCount) rule));
                break;
            case RULE_BY_COUNT_OF_GOODS:
                execute(SqlUtil.delete((RuleByCountOfGoods) rule));
                break;
            case RULE_BY_TIME:
                execute(SqlUtil.delete((RuleByTime) rule));
                break;
        }
    }

    @Override
    protected Rule save(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        rule.setId(UUID.randomUUID());
        switch (rule.getRuleType()) {
            case RULE_BY_PURCHASE_COUNT:
                execute(SqlUtil.create((RuleByPurchaseCount) rule));
                break;
            case RULE_BY_COUNT_OF_GOODS:
                execute(SqlUtil.create((RuleByCountOfGoods) rule));
                break;
            case RULE_BY_TIME:
                execute(SqlUtil.create((RuleByTime) rule));
                break;
        }
        return rule;
    }

    private void createTables() {
        execute(SqlUtil.createTableRetail());
        execute(SqlUtil.createTableCheck());
        execute(SqlUtil.createTableCustomer());
        execute(SqlUtil.createTableRuleByTime());
        execute(SqlUtil.createTableRuleByPurchaseCount());
        execute(SqlUtil.createTableRuleByCountOfGoods());
    }

    private Result execute(String sql) {
        try (Connection connection = getNewConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate(sql);
            return Result.SUCCESS;
        } catch (Exception e) {
            log.error(e);
            return Result.ERROR;
        }
    }

    private Result executeQuery(String sql, ResultSetHandler resultSetHandler) {
        try (Connection connection = getNewConnection()) {
            var statement = connection.createStatement();
            var result = statement.executeQuery(sql);
            resultSetHandler.invoke(result);
            return Result.SUCCESS;
        } catch (Exception e) {
            log.error(e);
            return Result.ERROR;
        }
    }

    private Connection getNewConnection() throws SQLException, ClassNotFoundException, IOException {
        Class.forName(ConfigurationUtil.getConfigurationEntry(Constants.DB_DRIVER));
        return DriverManager.getConnection(url, username, password);
    }

    private interface ResultSetHandler {
        /**
         * Invoke.
         *
         * @param resultSet the result set
         */
        void invoke(ResultSet resultSet);
    }
}
