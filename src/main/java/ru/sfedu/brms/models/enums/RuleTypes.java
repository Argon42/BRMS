package ru.sfedu.brms.models.enums;

import ru.sfedu.brms.models.rules.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum RuleTypes {
    RULE_BY_COST(RuleByCost.class),
    RULE_BY_COUNT_OF_GOODS(RuleByCountOfGoods.class),
    RULE_BY_COUNT_OF_PURCHASES(RuleByCountOfPurchases.class),
    RULE_BY_TIME(RuleByTime.class);

    private final Class<? extends Rule> ruleClass;

    RuleTypes(Class<? extends Rule> ruleClass) {
        this.ruleClass = ruleClass;
    }

    public static List<Class<? extends Rule>> loadAllClassesRules() {
        return Arrays.stream(RuleTypes.values())
                .map(RuleTypes::getRuleClass)
                .collect(Collectors.toList());
    }

    public Class<? extends Rule> getRuleClass() {
        return ruleClass;
    }
}
