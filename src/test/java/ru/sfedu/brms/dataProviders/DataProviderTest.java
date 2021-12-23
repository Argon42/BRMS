package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.enums.DisplayVariants;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.models.rules.RuleByCountOfGoods;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public abstract class DataProviderTest {
    private static IDataProvider dataProvider;

    @BeforeEach
    public void setup() {
        dataProvider = loadDataProvider();
        dataProvider.dropAll();
        dataProvider.initDataSource();
    }

    @Test
    public void searchAllRules() {
        UUID retailId = createRetail();
        UUID ruleId1 = createRuleByCountOfGoods(retailId, 3);
        UUID ruleId2 = createRuleByCountOfGoods(retailId, 3);
        List<Rule> rules = dataProvider.searchAllRules();
        Assertions.assertEquals(2, rules.size());
        Optional<Rule> rule1 = dataProvider.findRuleByID(ruleId1);
        Optional<Rule> rule2 = dataProvider.findRuleByID(ruleId2);
        Assertions.assertTrue(rule1.isPresent() && rule2.isPresent());
        Assertions.assertTrue(rules.contains(rule1.get()));
        Assertions.assertTrue(rules.contains(rule2.get()));
    }

    @Test
    public void searchAllRulesNotNull() {
        UUID retailId = createRetail();
        createRuleByCountOfGoods(retailId, 3);
        createRuleByCountOfGoods(retailId, 3);
        List<Rule> rules = dataProvider.searchAllRules();
        Assertions.assertNotNull(rules);
    }

    @Test
    public void searchAllRulesWhenRulesNotExists() {
        List<Rule> rules = dataProvider.searchAllRules();
        Assertions.assertEquals(0, rules.size());
    }

    @Test
    public void searchAvailableRules() {
        UUID retailId = createRetail();
        UUID ruleId1 = createRuleByCountOfGoods(retailId, 2);
        UUID ruleId2 = createRuleByCountOfGoods(retailId, 3);
        UUID customer = createCustomer(retailId);
        UUID check1 = createStoreCheck(customer, 1000, 1);
        UUID check2 = createStoreCheck(customer, 1000, 2);
        UUID check3 = createStoreCheck(customer, 1000, 4);

        Assertions.assertEquals(0, dataProvider.searchAvailableRules(check1).size());
        Assertions.assertEquals(1, dataProvider.searchAvailableRules(check2).size());
        Assertions.assertEquals(2, dataProvider.searchAvailableRules(check3).size());
    }

    @Test
    public void searchAvailableRulesWithIncorrectCheckId() {
        UUID retailId = createRetail();
        UUID ruleId1 = createRuleByCountOfGoods(retailId, 2);
        UUID ruleId2 = createRuleByCountOfGoods(retailId, 3);
        UUID customer = createCustomer(retailId);
        UUID check1 = createStoreCheck(customer, 1000, 1);

        Assertions.assertThrows(NoSuchElementException.class, () -> dataProvider.searchAvailableRules(UUID.randomUUID()));
    }

    @Test
    public void searchAvailableRulesWithNullCheck() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> dataProvider.searchAvailableRules(null));
    }

    @Test
    public void displayStatisticForEnabledAndCustomer() {
        UUID retailId = createRetail();
        UUID ruleId1 = createRuleByCountOfGoods(retailId, 2);
        UUID ruleId2 = createRuleByCountOfGoods(retailId, 3);
        dataProvider.disableRule(createRuleByCountOfGoods(retailId, 4));

        String result = dataProvider.displayStatistic(String.format("%s_%s", DisplayVariants.ENABLED_RULES, DisplayVariants.RULE_FOR_CUSTOMERS));
        StringBuilder builder = new StringBuilder()
                .append(dataProvider.findRuleByID(ruleId1).get())
                .append('\n')
                .append(dataProvider.findRuleByID(ruleId2).get())
                .append('\n');
        Assertions.assertEquals(builder.toString(), result);
    }

    @Test
    public void displayStatisticWithNullArgument(){
       Assertions.assertThrows(IllegalArgumentException.class, () -> dataProvider.displayStatistic(null));
    }

    @Test
    public void createRuleByCountOfGoods() {
        UUID retailId = createRetail();
        Assertions.assertTrue(dataProvider.findRetailByID(retailId).isPresent());

        UUID ruleId = createRuleByCountOfGoods(retailId, 3);
        Assertions.assertTrue(dataProvider.findRuleByID(ruleId).isPresent());

        UUID customerId = createCustomer(retailId);
        Assertions.assertTrue(dataProvider.findCustomerByID(customerId).isPresent());

        UUID checkId = createStoreCheck(customerId, 1000, 3);
        Assertions.assertTrue(dataProvider.findCheckByID(checkId).isPresent());

        var fullRule = dataProvider.findRuleByID(ruleId);
        Assertions.assertTrue(fullRule.isPresent());
        getLog().info(fullRule);

        dataProvider.deleteCheck(checkId);
        dataProvider.deleteCustomer(customerId);
        dataProvider.deleteRule(ruleId);
        dataProvider.deleteRetail(retailId);
    }

    @Test
    public void workWithCustomer() {
        var customer = new Customer(null, "TestCustomer", "88005553535", "qq@qq.qq");
        UUID id = dataProvider.saveCustomer(customer);
        Assertions.assertNotNull(id);

        Optional<Customer> foundedCustomer = dataProvider.findCustomerByID(id);
        Assertions.assertTrue(foundedCustomer.isPresent());

        foundedCustomer.get().setName("UpdatedTestCustomer");
        dataProvider.editCustomer(foundedCustomer.get());
        Assertions.assertEquals(foundedCustomer.get(), dataProvider.findCustomerByID(foundedCustomer.get().getId()).get());

        dataProvider.deleteCustomer(foundedCustomer.get().getId());
    }

    @Test
    public void createCheckWithCustomerId(){
        UUID retailId = createRetail();

        UUID customer = createCustomer(retailId);
        UUID check1 = createStoreCheck(customer, 1000, 1);
        UUID check2 = createStoreCheck(customer, 1000, 2);
        UUID check3 = createStoreCheck(customer, 1000, 4);

        Assertions.assertEquals(customer, dataProvider.findCheckByID(check1).get().getCustomerId());
        Assertions.assertEquals(customer, dataProvider.findCheckByID(check2).get().getCustomerId());
        Assertions.assertEquals(customer, dataProvider.findCheckByID(check3).get().getCustomerId());
    }

    @Test
    public void workWithCheck() {
        var check = new StoreCheck();
        check.setCost(1000);
        check.setCountOfGoods(3);
        check.setTime(Instant.now());
        UUID id = dataProvider.saveCheck(check);
        Assertions.assertNotNull(id);

        Optional<StoreCheck> foundedCheck = dataProvider.findCheckByID(id);
        Assertions.assertTrue(foundedCheck.isPresent());

        foundedCheck.get().setCost(2000);
        dataProvider.editCheck(foundedCheck.get());
        Assertions.assertEquals(foundedCheck.get(), dataProvider.findCheckByID(foundedCheck.get().getId()).get());

        dataProvider.deleteCheck(foundedCheck.get().getId());
    }

    @Test
    public void workWithRetail() {
        var retail = new Retail();
        retail.setName("UpdatedTestRetail");
        UUID id = dataProvider.saveRetail(retail);
        Assertions.assertNotNull(id);

        Optional<Retail> foundedRetail = dataProvider.findRetailByID(id);
        Assertions.assertTrue(foundedRetail.isPresent());

        foundedRetail.get().setName("UpdatedTestRetail");
        dataProvider.editRetail(foundedRetail.get());
        Assertions.assertEquals(id, foundedRetail.get().getId());
        Retail updated = dataProvider.findRetailByID(foundedRetail.get().getId()).get();
        Assertions.assertEquals(foundedRetail.get(), updated);

        dataProvider.deleteRetail(foundedRetail.get().getId());
    }

    protected abstract IDataProvider loadDataProvider();

    private UUID createStoreCheck(UUID customerId, int cost, int countOfGoods) {
        var check = new StoreCheck();
        check.setCost(cost);
        check.setCountOfGoods(countOfGoods);
        check.setTime(Instant.now());
        check.setCustomerId(customerId);
        return dataProvider.saveCheck(check);
    }

    private UUID createCustomer(UUID retailId) {
        var customer = new Customer(null, "TestCustomer", "88005553535", "qq@qq.qq");
        customer.setRetailId(retailId);
        return dataProvider.saveCustomer(customer);
    }

    private UUID createRuleByCountOfGoods(UUID retailId, int minimalCountOfGoods) {
        var rule = new RuleByCountOfGoods();
        rule.setName("TestRule");
        rule.setDiscount(10);
        rule.setMinimalCountOfGoods(minimalCountOfGoods);
        rule.setRetailId(retailId);
        rule.setEnable(true);
        return dataProvider.saveRule(rule);
    }

    private UUID createRetail() {
        var retail = new Retail();
        retail.setName("TestRetail");
        retail.setCountOfStores(4);
        return dataProvider.saveRetail(retail);
    }

    protected abstract Logger getLog();
}
