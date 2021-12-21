package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.rules.RuleByCountOfGoods;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public abstract class DataProviderTest {
    private static IDataProvider dataProvider;

    @BeforeEach
    public void Setup() {
        dataProvider = loadDataProvider();
        dataProvider.initDataSource();
    }

    @Test
    public void createRuleByCountOfGoods() {
        var retail = new Retail();
        retail.setName("TestRetail");
        retail.setCountOfStores(4);
        var retailId = dataProvider.saveRetail(retail);
        Assertions.assertTrue(dataProvider.findRetailByID(retailId).isPresent());

        var rule = new RuleByCountOfGoods();
        rule.setName("TestRule");
        rule.setDiscount(10);
        rule.setMinimalCountOfGoods(3);
        rule.setRetailId(retailId);
        var ruleId = dataProvider.saveRule(rule);
        Assertions.assertTrue(dataProvider.findRuleByID(ruleId).isPresent());

        var customer = new Customer(null, "TestCustomer", "88005553535", "qq@qq.qq");
        customer.setRetailId(retailId);
        var customerId = dataProvider.saveCustomer(customer);
        Assertions.assertTrue(dataProvider.findCustomerByID(customerId).isPresent());

        var check = new StoreCheck();
        check.setCost(1000);
        check.setCountOfGoods(3);
        check.setTime(Instant.now());
        check.setCustomerId(customerId);
        var checkId = dataProvider.saveCheck(check);
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

    protected abstract Logger getLog();
}
