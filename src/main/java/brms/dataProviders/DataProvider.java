package brms.dataProviders;

import brms.Result;
import brms.models.HistoryContent;
import brms.models.rules.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.UUID;

public abstract class DataProvider implements IDataProvider {

    private static final Logger log = LogManager.getLogger(DataProvider.class);

    @Override
    public Result createRule(Rule rule) {
        log.info("Create new rule");

        Optional<Result> ruleError = validateRule(rule);
        if(ruleError.isPresent()){
            log.error("Rule create {}", ruleError.get());
            return ruleError.get();
        }

        log.info("Save new rule");

        //TODO обработчик ошибок
        Rule createdRule = saveRule(rule);
        saveHistory(new HistoryContent(UUID.randomUUID(),
                        DataProvider.class.getSimpleName(),
                        System.currentTimeMillis(),
                        createdRule,
                        Thread.currentThread().getStackTrace()[1].getMethodName(),
                        Result.SUCCESS)
        );

        log.info("Rule saved");
        return Result.SUCCESS;
    }

    protected void saveHistory(HistoryContent historyContent){
        //TODO сделать сохранение в монгу
    }

    protected abstract Rule update(Rule rule);

    protected abstract void delete(Rule rule);

    protected abstract Rule saveRule(Rule rule);

    @Override
    public Result eraseRule(UUID id) {
        log.info("Delete rule");

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not find");
            return Result.SUCCESS;
        }

        log.info("Delete find rule");

        //TODO обработка ошибок
        delete(rule.get());
        saveHistory(new HistoryContent(UUID.randomUUID(),
                DataProvider.class.getSimpleName(),
                System.currentTimeMillis(),
                rule,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                Result.SUCCESS)
        );

        log.info("Rule deleted");
        return Result.SUCCESS;
    }

    @Override
    public Result editRule(Rule rule) {
        log.info("Edit rule");

        Optional<Result> errorRule = validateRule(rule.getId());
        if (errorRule.isPresent())
        {
            log.error("Rule edit {}", errorRule.get());
            return errorRule.get();
        }

        log.info("Edit validated rule");

        //TODO обработчик ошибок
        Rule editedRule = update(rule);
        saveHistory(new HistoryContent(UUID.randomUUID(),
                DataProvider.class.getSimpleName(),
                System.currentTimeMillis(),
                editedRule,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                Result.SUCCESS)
        );

        log.info("Rule edited");
        return Result.SUCCESS;
    }

    @Override
    public Result enableRule(UUID id) {
        log.info("Enable rule");

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not found");
            return Result.ERROR_NOT_FOUND;
        }
        //TODO изменение состояния правила

        log.info("Enable find rule");

        //TODO обработчик ошибок
        Rule editedRule = update(rule.get());
        saveHistory(new HistoryContent(UUID.randomUUID(),
                DataProvider.class.getSimpleName(),
                System.currentTimeMillis(),
                editedRule,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                Result.SUCCESS)
        );

        log.info("Rule Enabled");
        return Result.SUCCESS;
    }

    @Override
    public Result disableRule(UUID id) {
        log.info("Disable rule");

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty())
        {
            log.warn("Rule not found");
            return Result.ERROR_NOT_FOUND;
        }
        //TODO изменение состояния правила

        log.info("Disable find rule");

        //TODO обработчик ошибок
        Rule editedRule = update(rule.get());
        saveHistory(new HistoryContent(UUID.randomUUID(),
                DataProvider.class.getSimpleName(),
                System.currentTimeMillis(),
                editedRule,
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                Result.SUCCESS)
        );

        log.info("Rule Disabled");
        return Result.SUCCESS;
    }

    private Optional<Result> validateRule(Rule rule) {
        if(rule == null)
            return Optional.of(Result.ERROR);

        if(rule.getName() == null)
            return Optional.of(Result.ERROR);

        Optional<Rule> findRule = findRuleByName(rule.getName());
        return findRule.isEmpty() ? Optional.empty() : Optional.of(Result.ERROR_ALREADY_EXIST);
    }

    private Optional<Result> validateRule(UUID id)
    {
        //TODO проверка существующего правила
        //TODO переименовать методы в нормальные названия
        return Optional.empty();
    }
}
