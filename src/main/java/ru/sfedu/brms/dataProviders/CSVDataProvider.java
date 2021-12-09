package ru.sfedu.brms.dataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.Check;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVDataProvider extends DataProvider {

    private static final Logger log = LogManager.getLogger(CSVDataProvider.class);

    @Override
    public void initDataSource() {
        RuleTypes.loadAllClassesRules().forEach(ruleClass -> {
            try {
                String path = createPath(ruleClass);
                Path fullPath = Paths.get(loadRootPath());
                if (!Files.exists(fullPath)) {
                    try {
                        Files.createDirectories(fullPath);
                        log.info("Path {} created", fullPath);
                    } catch (IOException e) {
                        log.error(e);
                    }
                }

                if (new File(path).createNewFile())
                    log.info("File {} created", path);
            } catch (IOException e) {
                log.error(e);
            }
        });
    }

    @Override
    protected Rule update(Rule rule) {
        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());
        Rule foundRule = newCollection.stream().filter( rule1 -> Objects.equals(rule1.getId(), rule.getId())).findFirst().get();
        int index = newCollection.indexOf(foundRule);
        newCollection.set(index, rule);
        beanToCsv(newCollection, rule.getClass());
        return rule;
    }

    @Override
    protected void delete(Rule rule) {
        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());
        newCollection.remove(rule);
        beanToCsv(newCollection, rule.getClass());
    }

    @Override
    protected Rule save(Rule rule) {
        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());
        rule.setId(UUID.randomUUID());
        newCollection.add(rule);
        beanToCsv(newCollection, rule.getClass());
        return rule;
    }

    @Override
    public Optional<Rule> findRuleByID(UUID id) {
        return loadAllRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<Rule> findRuleByName(String name) {
        return loadAllRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getName(), name))
                .findFirst();
    }

    @Override
    public List<Rule> searchAvailableRules(Check check) {
        return loadAllRules()
                .stream()
                .filter(Rule::isEnable)
                .filter(rule -> rule.checkRule(check))
                .collect(Collectors.toList());
    }

    @Override
    public List<Rule> loadAllRules() {
        return RuleTypes.loadAllClassesRules()
                .stream()
                .map(this::csvToBean)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private <T> List<T> csvToBean(Class<T> beanType) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(createPath(beanType)));
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(beanType).build();
            List<T> beans = csvToBean.parse();
            csvReader.close();
            return beans;
        } catch (Exception exception) {
            log.error(exception);
        }
        return new ArrayList<>();
    }

    private <T> Result beanToCsv(List<?> beans, Class<?> beanClass) {
        Result result;
        try {
            FileWriter fileWriter = new FileWriter(createPath(beanClass), false);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();

            beanToCsv.write(((List<T>) beans));
            csvWriter.close();
            fileWriter.close();

            result = Result.SUCCESS;
        } catch (Exception exception) {
            log.error(exception);
            result = Result.SUCCESS;
        }
        return result;
    }

    private <T> String createPath(Class<T> object) throws IOException {
        if (object == null) throw new IllegalArgumentException("Argument is null");

        String rootPath = loadRootPath();
        String csvExtension = ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV);

        return rootPath + object.getSimpleName() + csvExtension;
    }

    private String loadRootPath() throws IOException {
        return ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    }
}
