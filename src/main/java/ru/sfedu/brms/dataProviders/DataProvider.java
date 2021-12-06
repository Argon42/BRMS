package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.HistoryContent;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.utils.Constants;

import java.util.Optional;
import java.util.UUID;

public abstract class DataProvider implements IDataProvider {

    private static final Logger log = LogManager.getLogger(DataProvider.class);

    @Override
    public Result createRule(Rule rule) {
        log.info("Create new rule: {}", rule);

        Optional<Result> ruleError = validateRule(rule);
        if (ruleError.isPresent()) {
            log.error("Rule create {}", ruleError.get());
            throw new IllegalArgumentException(ruleError.get().toString());
        }

        log.info("Save new rule: {}", rule);

        //TODO обработчик ошибок
        Rule createdRule = save(rule);
        saveHistory(createHistoryContent(createdRule, Result.SUCCESS));

        log.info("Rule saved: {}", rule);
        return Result.SUCCESS;
    }

    @Override
    public Result eraseRule(UUID id) {
        log.info("Delete rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not find: {}", id);
            throw new IllegalArgumentException(String.format("Record with id:\"%s\" not found", id));
        }

        log.info("Delete find: {}", rule);

        //TODO обработка ошибок
        delete(rule.get());
        saveHistory(createHistoryContent(rule, Result.SUCCESS));

        log.info("Rule deleted: {}", rule);
        return Result.SUCCESS;
    }

    @Override
    public Result editRule(Rule rule) {
        log.info("Edit rule: {}", rule);

        Optional<Result> errorRule = validateRule(rule.getId());
        if (errorRule.isPresent())
        {
            log.error("Rule edit {}", errorRule.get());
            throw new IllegalArgumentException(errorRule.get().toString());
        }

        log.info("Edit validated rule: {}", rule);

        //TODO обработчик ошибок
        Rule editedRule = update(rule);
        saveHistory(createHistoryContent(editedRule, Result.SUCCESS));

        log.info("Rule edited: {}", rule);
        return Result.SUCCESS;
    }

    @Override
    public Result enableRule(UUID id) {
        log.info("Enable rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not found: {}", id);
            throw new IllegalArgumentException(String.format("Record with id:\"%s\" not found", id));
        }
        //TODO изменение состояния правила

        log.info("Enable find rule: {}", rule);

        //TODO обработчик ошибок
        Rule editedRule = update(rule.get());
        saveHistory(createHistoryContent(editedRule, Result.SUCCESS));

        log.info("Rule Enabled: {}", rule);
        return Result.SUCCESS;
    }

    @Override
    public Result disableRule(UUID id) {
        log.info("Disable rule: {}", id);

        Optional<Rule> rule = findRuleByID(id);
        if (rule.isEmpty()) {
            log.error("Rule not found: {}", id);
            throw new IllegalArgumentException(String.format("Record with id:\"%s\" not found", id));
        }
        //TODO изменение состояния правила

        log.info("Disable find rule: {}", rule);

        //TODO обработчик ошибок
        Rule editedRule = update(rule.get());
        saveHistory(createHistoryContent(editedRule, Result.SUCCESS));

        log.info("Rule Disabled: {}", rule);
        return Result.SUCCESS;
    }

    public abstract void initDataSource();

    protected abstract Rule update(Rule rule);

    protected abstract void delete(Rule rule);

    protected void saveHistory(HistoryContent historyContent) {
        //TODO сделать сохранение в монгу
    }

    protected abstract Rule save(Rule rule);

    protected HistoryContent createHistoryContent(Object object, Result result) {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[2];
        return new HistoryContent(UUID.randomUUID(),
                stackTrace.getClassName(),
                System.currentTimeMillis(),
                object,
                stackTrace.getMethodName(),
                Constants.DEFAULT_AUTHOR,
                result);
    }

    private Optional<Result> validateRule(UUID id) {
        //TODO проверка существующего правила
        //TODO переименовать методы в нормальные названия
        return Optional.empty();
    }

    private Optional<Result> validateRule(Rule rule) {
        if (rule == null)
            return Optional.of(Result.ERROR);

        if (rule.getName() == null)
            return Optional.of(Result.ERROR);

        Optional<Rule> findRule = findRuleByName(rule.getName());
        return findRule.isEmpty() ? Optional.empty() : Optional.of(Result.ERROR_ALREADY_EXIST);
    }

}
