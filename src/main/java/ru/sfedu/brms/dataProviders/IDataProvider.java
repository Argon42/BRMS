package ru.sfedu.brms.dataProviders;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    void initDataSource();

    UUID createRule(Rule rule);

    void deleteRule(UUID id);

    Rule editRule(Rule rule);

    Rule enableRule(UUID id);

    Rule disableRule(UUID id);

    List<Rule> searchAllRules();

    Optional<Rule> findRuleByID(UUID id);

    Optional<Rule> findRuleByName(String Name);

    UUID saveCheck(Check check);

    void deleteCheck(UUID id);

    Check editCheck(Check check);

    Optional<Check> findCheckByID(UUID id);

    UUID saveCustomer(Customer customer);

    void deleteCustomer(UUID id);

    Customer editCustomer(Customer customer);

    Optional<Customer> findCustomerByID(UUID id);

    List<Rule> searchAvailableRules(Check check);

    List<Rule> searchAvailableRules(float cost, String time, int countOfGoods);

    List<Rule> searchAvailableRules(Check check, Customer customer);

    List<Rule> searchAvailableRules(float cost, String time, int countOfGoods, String userID);

    List<Rule> findRuleForChecks(List<Rule> rulesForSearch);

    List<Rule> findRuleForCustomers(List<Rule> rulesForSearch);

    List<Rule> findRuleForChecksAndCustomers(List<Rule> rulesForSearch);

    List<Rule> findEnabledRules(List<Rule> rulesForSearch);

    String displayStatistic(String searchCriteria);
}