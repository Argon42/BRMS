package ru.sfedu.brms.dataProviders;

import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    void initDataSource();

    UUID saveRule(Rule rule);

    void deleteRule(UUID id);

    void editRule(Rule rule);

    void enableRule(UUID id);

    void disableRule(UUID id);

    List<Rule> searchAllRules();

    Optional<Rule> findRuleByID(UUID id);

    UUID saveCheck(StoreCheck check);

    void deleteCheck(UUID id);

    void editCheck(StoreCheck check);

    Optional<StoreCheck> findCheckByID(UUID id);

    UUID saveCustomer(Customer customer);

    void deleteCustomer(UUID id);

    void editCustomer(Customer customer);

    Optional<Customer> findCustomerByID(UUID id);

    UUID saveRetail(Retail retail);

    void deleteRetail(UUID id);

    void editRetail(Retail retail);

    Optional<Retail> findRetailByID(UUID id);
}