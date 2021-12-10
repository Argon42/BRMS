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
import ru.sfedu.brms.models.Customer;
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
        RuleTypes.loadAllClassesRules().forEach(this::createFolder);
        createFolder(Customer.class);
        createFolder(Check.class);
    }

    @Override
    protected List<Check> findAllChecksByCustomer(UUID id) {
        return csvToBean(Check.class)
                .stream()
                .filter(check -> check.getCustomerId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    protected Customer update(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) csvToBean(customer.getClass());
        Optional<Customer> foundedCustomer = newCollection.stream()
                .filter(customer1 -> Objects.equals(customer1.getId(), customer.getId())).findFirst();
        if (foundedCustomer.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, customer.getId()));

        int index = newCollection.indexOf(foundedCustomer.get());
        newCollection.set(index, customer);
        beanToCsv(newCollection, customer.getClass());
        customer.setChecks(findAllChecksByCustomer(customer.getId()));
        return customer;
    }

    @Override
    protected void delete(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) csvToBean(Customer.class);

        if (!newCollection.remove(customer))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, customer.getId()));

        beanToCsv(newCollection, customer.getClass());
    }

    @Override
    protected void delete(Check check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Check> newCollection = (List<Check>) csvToBean(Check.class);

        if (!newCollection.remove(check))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, check.getId()));

        beanToCsv(newCollection, check.getClass());
    }

    @Override
    protected Customer save(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) csvToBean(Customer.class);
        customer.setId(UUID.randomUUID());
        newCollection.add(customer);
        beanToCsv(newCollection, customer.getClass());
        customer.setChecks(findAllChecksByCustomer(customer.getId()));
        return customer;
    }

    @Override
    protected Check save(Check check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Check> newCollection = (List<Check>) csvToBean(Check.class);
        check.setId(UUID.randomUUID());
        newCollection.add(check);
        beanToCsv(newCollection, check.getClass());
        return check;
    }

    @Override
    protected Check update(Check check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Check> newCollection = (List<Check>) csvToBean(Check.class);
        Optional<Check> foundRule = newCollection.stream()
                .filter(check1 -> Objects.equals(check1.getId(), check.getId())).findFirst();
        if (foundRule.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, check.getId()));

        int index = newCollection.indexOf(foundRule.get());
        newCollection.set(index, check);
        beanToCsv(newCollection, check.getClass());
        return check;
    }

    @Override
    protected Rule update(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());
        Optional<Rule> foundRule = newCollection.stream().filter(rule1 -> Objects.equals(rule1.getId(), rule.getId())).findFirst();
        if (foundRule.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, rule.getId()));

        int index = newCollection.indexOf(foundRule.get());
        newCollection.set(index, rule);
        beanToCsv(newCollection, rule.getClass());
        return rule;
    }

    @Override
    protected void delete(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());

        if (!newCollection.remove(rule))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, rule.getId()));

        beanToCsv(newCollection, rule.getClass());
    }

    @Override
    protected Rule save(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) csvToBean(rule.getRuleType().getRuleClass());
        rule.setId(UUID.randomUUID());
        newCollection.add(rule);
        beanToCsv(newCollection, rule.getClass());
        return rule;
    }

    @Override
    public List<Rule> searchAllRules() {
        return RuleTypes.loadAllClassesRules()
                .stream()
                .map(this::csvToBean)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rule> findRuleByID(UUID id) {
        if (id == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);
        return this.searchAllRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<Rule> findRuleByName(String name) {
        if (name == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);
        return this.searchAllRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getName(), name))
                .findFirst();
    }

    @Override
    public Optional<Check> findCheckByID(UUID id) {
        if (id == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);
        return csvToBean(Check.class)
                .stream()
                .filter(check -> Objects.equals(check.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByID(UUID id) {
        if (id == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);
        return csvToBean(Customer.class)
                .stream()
                .filter(customer -> Objects.equals(customer.getId(), id))
                .peek(customer -> customer.setChecks(findAllChecksByCustomer(customer.getId())))
                .findFirst();
    }

    private void createFolder(Class<?> ruleClass) {
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
            result = Result.ERROR;
        }
        return result;
    }

    private <T> String createPath(Class<T> object) throws IOException {
        if (object == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        String rootPath = loadRootPath();
        String csvExtension = ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV);

        return rootPath + object.getSimpleName() + csvExtension;
    }

    private String loadRootPath() throws IOException {
        return ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    }
}
