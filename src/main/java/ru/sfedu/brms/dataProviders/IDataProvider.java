package ru.sfedu.brms.dataProviders;

import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Data provider.
 */
public interface IDataProvider {
    /**
     * Init data source.
     */
    void initDataSource();

    /**
     * Save rule uuid.
     *
     * @param rule the rule
     * @return the uuid
     */
    UUID saveRule(Rule rule);

    /**
     * Delete rule.
     *
     * @param id the id
     */
    void deleteRule(UUID id);

    /**
     * Edit rule.
     *
     * @param rule the rule
     */
    void editRule(Rule rule);

    /**
     * Enable rule.
     *
     * @param id the id
     */
    void enableRule(UUID id);

    /**
     * Disable rule.
     *
     * @param id the id
     */
    void disableRule(UUID id);

    /**
     * Search all rules list.
     *
     * @return the list
     */
    List<Rule> searchAllRules();

    /**
     * Find rule by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Rule> findRuleByID(UUID id);

    /**
     * Save check uuid.
     *
     * @param check the check
     * @return the uuid
     */
    UUID saveCheck(StoreCheck check);

    /**
     * Delete check.
     *
     * @param id the id
     */
    void deleteCheck(UUID id);

    /**
     * Edit check.
     *
     * @param check the check
     */
    void editCheck(StoreCheck check);

    /**
     * Find check by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<StoreCheck> findCheckByID(UUID id);

    /**
     * Save customer uuid.
     *
     * @param customer the customer
     * @return the uuid
     */
    UUID saveCustomer(Customer customer);

    /**
     * Delete customer.
     *
     * @param id the id
     */
    void deleteCustomer(UUID id);

    /**
     * Edit customer.
     *
     * @param customer the customer
     */
    void editCustomer(Customer customer);

    /**
     * Find customer by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Customer> findCustomerByID(UUID id);

    /**
     * Save retail uuid.
     *
     * @param retail the retail
     * @return the uuid
     */
    UUID saveRetail(Retail retail);

    /**
     * Delete retail.
     *
     * @param id the id
     */
    void deleteRetail(UUID id);

    /**
     * Edit retail.
     *
     * @param retail the retail
     */
    void editRetail(Retail retail);

    /**
     * Find retail by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Retail> findRetailByID(UUID id);

    /**
     * Drop all.
     */
    void dropAll();

    /**
     * Search available rules list.
     *
     * @param checkId the check id
     * @return the list
     */
    List<Rule> searchAvailableRules(UUID checkId);

    /**
     * Search available rules list.
     *
     * @param checkId    the check id
     * @param customerId the customer id
     * @return the list
     */
    List<Rule> searchAvailableRules(UUID checkId, UUID customerId);

    /**
     * Find rule for checks list.
     *
     * @param rulesForSearch the rules for search
     * @return the list
     */
    List<Rule> findRuleForChecks(List<Rule> rulesForSearch);

    /**
     * Find rule for customers list.
     *
     * @param rulesForSearch the rules for search
     * @return the list
     */
    List<Rule> findRuleForCustomers(List<Rule> rulesForSearch);

    /**
     * Find rule for checks and customers list.
     *
     * @param rulesForSearch the rules for search
     * @return the list
     */
    List<Rule> findRuleForChecksAndCustomers(List<Rule> rulesForSearch);

    /**
     * Find enabled rules list.
     *
     * @param rulesForSearch the rules for search
     * @return the list
     */
    List<Rule> findEnabledRules(List<Rule> rulesForSearch);

    /**
     * Display statistic string.
     *
     * @param searchCriteria the search criteria
     * @return the string
     */
    String displayStatistic(String searchCriteria);
}