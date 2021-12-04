package brms.dataProviders;

import brms.models.Check;
import brms.Result;
import brms.models.rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    Result CreateRule(Rule rule);

    Result DeleteRule(UUID id);

    Result EditRule(Rule rule);

    Result EnableRule(UUID id);

    Result DisableRule(UUID id);

    Optional<Rule> FindRuleByID(UUID id);

    Optional<Rule> FindRuleByName(String Name);

    List<Rule> SearchAvailableRules(Check check);
}