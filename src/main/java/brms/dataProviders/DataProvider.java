package brms.dataProviders;

import brms.Result;
import brms.models.rules.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

public abstract class DataProvider implements IDataProvider {

    private static final Logger log = LogManager.getLogger(DataProvider.class);

    @Override
    public Result CreateRule(Rule rule) {
        log.info("Create new rule");

        Optional<Result> ruleError = validateRule(rule);
        if(ruleError.isPresent()){
            log.error("Rule create {}", ruleError.get());
            return ruleError.get();
        }

        log.info("Save new rule");
        //TODO вызов метода сохранения в бд
        //TODO сохранение истории изменения

        log.info("Rule saved");
        return Result.SUCCESS;
    }

    @Override
    public Result DeleteRule(UUID id) {
        log.info("Delete rule");

        Optional<Rule> rule = FindRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not find");
            return Result.SUCCESS;
        }

        log.info("Delete find rule");
        //TODO вызов метода удаления в бд
        //TODO сохранение истории изменения

        log.info("Rule deleted");
        return Result.SUCCESS;
    }

    @Override
    public Result EditRule(Rule rule) {
        log.info("Edit rule");

        Optional<Result> errorRule = validateRule(rule.getId());
        if (errorRule.isPresent())
        {
            log.error("Rule edit {}", errorRule.get());
            return errorRule.get();
        }

        log.info("Edit validated rule");
        //TODO вызов метода изменения в бд
        //TODO сохранение истории изменения

        log.info("Rule edited");
        return Result.SUCCESS;
    }

    @Override
    public Result EnableRule(UUID id) {
        log.info("Enable rule");

        Optional<Rule> rule = FindRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not found");
            return Result.ERROR_NOT_FOUND;
        }
        //TODO изменение состояния правила

        log.info("Enable find rule");
        //TODO вызов метода сохранения в бд
        //TODO сохранение истории изменения

        log.info("Rule Enabled");
        return Result.SUCCESS;
    }

    @Override
    public Result DisableRule(UUID id) {
        log.info("Disable rule");

        Optional<Rule> rule = FindRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not found");
            return Result.ERROR_NOT_FOUND;
        }
        //TODO изменение состояния правила

        log.info("Disable find rule");
        //TODO вызов метода сохранения в бд
        //TODO сохранение истории изменения

        log.info("Rule Disabled");
        return Result.SUCCESS;
    }

    private Optional<Result> validateRule(Rule rule) {
        if(rule == null)
            return Optional.of(Result.ERROR);

        if(rule.getName() == null)
            return Optional.of(Result.ERROR);

        Optional<Rule> findRule = FindRuleByName(rule.getName());
        return findRule.isEmpty() ? Optional.empty() : Optional.of(Result.ERROR_ALREADY_EXIST);
    }

    private Optional<Result> validateRule(UUID id)
    {
        //TODO проверка существующего правила
        //TODO переименовать методы в нормальные названия
        return Optional.empty();
    }
}
