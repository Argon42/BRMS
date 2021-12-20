package ru.sfedu.brms.dataProviders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;

import java.time.Instant;

public abstract class DataProviderTest {
    private static IDataProvider dataProvider;

    @BeforeEach
    public void Setup() {
        dataProvider = loadDataProvider();
        dataProvider.initDataSource();
    }

    @Test
    public void workWithCustomer() {
        var customer = new Customer(null, "TestCustomer", "88005553535", "qq@qq.qq");
        var id = dataProvider.saveCustomer(customer);
        Assertions.assertNotNull(id);

        var foundedCustomer = dataProvider.findCustomerByID(id);
        Assertions.assertTrue(foundedCustomer.isPresent());

        foundedCustomer.get().setName("UpdatedTestCustomer");
        var editedCustomer = dataProvider.editCustomer(foundedCustomer.get());
        Assertions.assertNotNull(editedCustomer);

        dataProvider.deleteCustomer(editedCustomer.getId());
    }

    @Test
    public void workWithCheck() {
        var check = new StoreCheck();
        check.setCost(1000);
        check.setCountOfGoods(3);
        check.setTime(Instant.now());
        var id = dataProvider.saveCheck(check);
        Assertions.assertNotNull(id);

        var foundedCheck = dataProvider.findCheckByID(id);
        Assertions.assertTrue(foundedCheck.isPresent());

        foundedCheck.get().setCost(2000);
        var editedCheck = dataProvider.editCheck(foundedCheck.get());
        Assertions.assertNotNull(editedCheck);

        dataProvider.deleteCheck(editedCheck.getId());
    }

    @Test
    public void workWithRetail() {
        var retail = new Retail();
        retail.setName("UpdatedTestRetail");
        var id = dataProvider.saveRetail(retail);
        Assertions.assertNotNull(id);

        var foundedRetail = dataProvider.findRetailByID(id);
        Assertions.assertTrue(foundedRetail.isPresent());

        foundedRetail.get().setName("UpdatedTestRetail");
        var editedRetail = dataProvider.editRetail(foundedRetail.get());
        Assertions.assertNotNull(editedRetail);

        dataProvider.deleteRetail(editedRetail.getId());
    }

    protected abstract IDataProvider loadDataProvider();
}
