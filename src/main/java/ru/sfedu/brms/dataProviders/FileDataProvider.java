package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.Customer;
import ru.sfedu.brms.models.Retail;
import ru.sfedu.brms.models.StoreCheck;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.models.enums.RuleTypes;
import ru.sfedu.brms.models.rules.Rule;
import ru.sfedu.brms.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type File data provider.
 */
public abstract class FileDataProvider extends DataProvider {

    private static final Logger log = LogManager.getLogger(FileDataProvider.class);

    @Override
    public void initDataSource() {
        RuleTypes.loadAllClassesRules().forEach(this::createFolder);
        createFolder(Customer.class);
        createFolder(StoreCheck.class);
        createFolder(Retail.class);
    }

    @Override
    public void dropAll() {
        try {
            String path = loadRootPath();
            Path fullPath = Paths.get(loadRootPath());
            if (deleteRecursive(new File(path))) {
                log.info("Path {} deleted", fullPath);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private boolean deleteRecursive(File path) throws FileNotFoundException {
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()) {
            for (File f : Objects.requireNonNull(path.listFiles())) {
                ret = ret && deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }

    @Override
    public Optional<Retail> findRetailByID(UUID id) {
        return fileToBean(Retail.class)
                .stream()
                .filter(retail -> Objects.equals(retail.getId(), id))
                .peek(retail -> retail.setCustomers(findAllCustomersByRetail(retail.getId())))
                .peek(retail -> retail.setChecks(
                        retail.getCustomers().stream()
                                .flatMap(customer -> findAllChecksByCustomer(customer.getId()).stream())
                                .collect(Collectors.toList())))
                .findFirst();
    }

    @Override
    public List<Rule> searchAllRules() {
        return RuleTypes.loadAllClassesRules()
                .stream()
                .map(this::fileToBean)
                .flatMap(Collection::stream)
                .peek(rule -> findRetailByID(rule.getRetailId()).ifPresent(rule::setRetail))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Rule> findRuleByID(UUID id) {
        return this.searchAllRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getId(), id))
                .peek(rule -> findRetailByID(rule.getRetailId())
                        .ifPresent(rule::setRetail))
                .findFirst();
    }

    @Override
    public Optional<StoreCheck> findCheckByID(UUID id) {
        return fileToBean(StoreCheck.class)
                .stream()
                .filter(check -> Objects.equals(check.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<Customer> findCustomerByID(UUID id) {
        return fileToBean(Customer.class)
                .stream()
                .filter(customer -> Objects.equals(customer.getId(), id))
                .peek(customer -> customer.setChecks(findAllChecksByCustomer(customer.getId())))
                .findFirst();
    }

    @Override
    protected List<StoreCheck> findAllChecksByCustomer(UUID id) {
        return fileToBean(StoreCheck.class)
                .stream()
                .filter(check -> check.getCustomerId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    protected List<Customer> findAllCustomersByRetail(UUID id) {
        return fileToBean(Customer.class)
                .stream()
                .filter(customer -> customer.getRetailId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    protected Retail save(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Retail> newCollection = (List<Retail>) fileToBean(Retail.class);
        retail.setId(UUID.randomUUID());
        newCollection.add(retail);
        beanToFile(newCollection, retail.getClass());
        return retail;
    }

    @Override
    protected void delete(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Retail> newCollection = (List<Retail>) fileToBean(Retail.class);

        if (!newCollection.remove(retail))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, retail.getId()));

        beanToFile(newCollection, retail.getClass());
    }

    @Override
    protected Retail update(Retail retail) {
        if (retail == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Retail> newCollection = (List<Retail>) fileToBean(retail.getClass());
        Optional<Retail> foundedRetail = newCollection
                .stream()
                .filter(retail1 -> Objects.equals(retail1.getId(), retail.getId()))
                .findFirst();
        if (foundedRetail.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, retail.getId()));

        int index = newCollection.indexOf(foundedRetail.get());
        newCollection.set(index, retail);
        beanToFile(newCollection, retail.getClass());
        return retail;
    }

    @Override
    protected Customer save(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) fileToBean(Customer.class);
        customer.setId(UUID.randomUUID());
        newCollection.add(customer);
        beanToFile(newCollection, customer.getClass());
        return customer;
    }

    @Override
    protected Customer update(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) fileToBean(customer.getClass());
        Optional<Customer> foundedCustomer = newCollection
                .stream()
                .filter(customer1 -> Objects.equals(customer1.getId(), customer.getId()))
                .findFirst();
        if (foundedCustomer.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, customer.getId()));

        int index = newCollection.indexOf(foundedCustomer.get());
        newCollection.set(index, customer);
        beanToFile(newCollection, customer.getClass());
        return customer;
    }

    @Override
    protected void delete(Customer customer) {
        if (customer == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Customer> newCollection = (List<Customer>) fileToBean(Customer.class);

        if (!newCollection.remove(customer))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, customer.getId()));

        beanToFile(newCollection, customer.getClass());
    }

    @Override
    protected StoreCheck save(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<StoreCheck> newCollection = (List<StoreCheck>) fileToBean(StoreCheck.class);
        check.setId(UUID.randomUUID());
        newCollection.add(check);
        beanToFile(newCollection, check.getClass());
        return check;
    }

    @Override
    protected StoreCheck update(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<StoreCheck> newCollection = (List<StoreCheck>) fileToBean(StoreCheck.class);
        Optional<StoreCheck> foundedCheck = newCollection
                .stream()
                .filter(check1 -> Objects.equals(check1.getId(), check.getId()))
                .findFirst();
        if (foundedCheck.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, check.getId()));

        int index = newCollection.indexOf(foundedCheck.get());
        newCollection.set(index, check);
        beanToFile(newCollection, check.getClass());
        return check;
    }

    @Override
    protected void delete(StoreCheck check) {
        if (check == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<StoreCheck> newCollection = (List<StoreCheck>) fileToBean(StoreCheck.class);

        if (!newCollection.remove(check))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, check.getId()));

        beanToFile(newCollection, check.getClass());
    }

    @Override
    protected Rule save(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) fileToBean(rule.getRuleType().getRuleClass());
        rule.setId(UUID.randomUUID());
        newCollection.add(rule);
        beanToFile(newCollection, rule.getClass());
        return rule;
    }

    @Override
    protected Rule update(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) fileToBean(rule.getRuleType().getRuleClass());
        Optional<Rule> foundRule = newCollection.stream().filter(rule1 -> Objects.equals(rule1.getId(), rule.getId())).findFirst();
        if (foundRule.isEmpty())
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, rule.getId()));

        int index = newCollection.indexOf(foundRule.get());
        newCollection.set(index, rule);
        beanToFile(newCollection, rule.getClass());
        return rule;
    }

    @Override
    protected void delete(Rule rule) {
        if (rule == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        List<Rule> newCollection = (List<Rule>) fileToBean(rule.getRuleType().getRuleClass());

        if (!newCollection.remove(rule))
            throw new IllegalArgumentException(String.format(Constants.OBJECT_WITH_ID_NOT_FOUND_EXCEPTION, rule.getId()));

        beanToFile(newCollection, rule.getClass());
    }

    /**
     * After create folder.
     *
     * @param ruleClass the rule class
     * @param fullPath  the full path
     */
    protected void afterCreateFolder(Class<?> ruleClass, String fullPath) {
    }

    /**
     * File to bean list.
     *
     * @param <T>      the type parameter
     * @param beanType the bean type
     * @return the list
     */
    protected abstract <T> List<T> fileToBean(Class<T> beanType);

    /**
     * Bean to file result.
     *
     * @param <T>       the type parameter
     * @param beans     the beans
     * @param beanClass the bean class
     * @return the result
     */
    protected abstract <T> Result beanToFile(List<?> beans, Class<?> beanClass);

    /**
     * Create path string.
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return the string
     * @throws IOException the io exception
     */
    protected abstract <T> String createPath(Class<T> object) throws IOException;

    /**
     * Load root path string.
     *
     * @return the string
     * @throws IOException the io exception
     */
    protected abstract String loadRootPath() throws IOException;

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

            if (new File(path).createNewFile()) {
                log.info("File {} created", path);
                afterCreateFolder(ruleClass, path);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
