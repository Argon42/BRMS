package ru.sfedu.brms;

import ru.sfedu.brms.dataProviders.CSVDataProvider;
import ru.sfedu.brms.dataProviders.IDataProvider;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.models.rules.RuleByCost;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        IDataProvider dataProvider = new CSVDataProvider();
        dataProvider.initDataSource();

        String name="Discount about 1000";
        dataProvider.createRule(new RuleByCost(name, 1000, 100));

        Optional<Rule> rule = dataProvider.findRuleByName(name);
        rule.get().setDescription("Is description for test editing");

        dataProvider.editRule(rule.get());

        dataProvider.eraseRule(rule.get().getId());
    }
}
