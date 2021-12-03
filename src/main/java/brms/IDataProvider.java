package brms;

import brms.Rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDataProvider {
    Result CreateRule(Rule rule);
    Result DeleteRule(UUID id);
    Result EditRule(UUID rule);
    Result EnableRule(UUID id);
    Result DisableRule(UUID id);
    Optional<Rule> FindRuleByID(UUID id);
    Optional<Rule> FindRulesByName(String Name);
    List<Rule> SearchAvailableRules(Check check);
}