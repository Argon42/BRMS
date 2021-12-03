package brms;

import brms.Rules.Rule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AbstractDataProvider implements IDataProvider{

    @Override
    public Result CreateRule(Rule rule) {
        return null;
    }

    @Override
    public Result DeleteRule(UUID id) {
        return null;
    }

    @Override
    public Result EditRule(UUID rule) {
        return null;
    }

    @Override
    public Result EnableRule(UUID id) {
        return null;
    }

    @Override
    public Result DisableRule(UUID id) {
        return null;
    }

    @Override
    public Optional<Rule> FindRuleByID(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<Rule> FindRulesByName(String Name) {
        return Optional.empty();
    }

    @Override
    public List<Rule> SearchAvailableRules(Check check) {
        return null;
    }
}
