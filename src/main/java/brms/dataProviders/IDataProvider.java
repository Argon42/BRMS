package brms.dataProviders;

import brms.Result;
import brms.models.Check;
import brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    Result createRule(Rule rule);

    Result eraseRule(UUID id);

    Result editRule(Rule rule);

    Result enableRule(UUID id);

    Result disableRule(UUID id);

    Optional<Rule> findRuleByID(UUID id);

    Optional<Rule> findRuleByName(String Name);

    List<Rule> searchAvailableRules(Check check);
}