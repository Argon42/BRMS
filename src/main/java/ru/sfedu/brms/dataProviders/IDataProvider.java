package ru.sfedu.brms.dataProviders;

import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    void initDataSource();

    Result createRule(Rule rule);

    Result eraseRule(UUID id);

    Result editRule(Rule rule);

    Result enableRule(UUID id);

    Result disableRule(UUID id);

    Optional<Rule> findRuleByID(UUID id);

    Optional<Rule> findRuleByName(String Name);

    List<Rule> searchAvailableRules(Check check);

    List<Rule> loadAllRules();
}