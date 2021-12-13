package ru.sfedu.labs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.IConfiguration;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractConfigurationUtilTest {
    private static final String FIELD_NAME = "testField%s";
    private static final String VALUE_NAME = "testValue%s";
    private static final int COUNT_OF_TEST_FIELDS = 3;
    private static final int NOT_EXIST_NUM_OF_FIELD = -1;

    private static final String LIST_NAME = "testList";
    private static final String LIST_ELEMENT_NAME = "TestListElement%s";
    private static final int LIST_LENGTH = 3;

    private static IConfiguration configuration;

    @BeforeEach
    public void Setup() {
        configuration = getConfigurationType();
    }

    @Test
    public void getProperty() throws IOException {
        for (int i = 0; i < COUNT_OF_TEST_FIELDS; i++) {
            String expectedValue = String.format(VALUE_NAME, i);
            String actualValue = ConfigurationUtil.getConfigurationEntry(configuration, String.format(FIELD_NAME, i));
            Assertions.assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    public void getNotExistProperty() throws IOException {
        Assertions.assertNull(ConfigurationUtil.getConfigurationEntry(configuration, String.format(FIELD_NAME, NOT_EXIST_NUM_OF_FIELD)));
    }

    @Test
    public void getPropertyMap() throws IOException {
        Map<Integer, String> map = ConfigurationUtil.getConfigurationEntriesMap(configuration, LIST_NAME);
        Assertions.assertEquals(LIST_LENGTH, map.size());
        for (int i = 0; i < LIST_LENGTH; i++) {
            Assertions.assertEquals(map.get(i), String.format(LIST_ELEMENT_NAME, i));
        }
    }

    @Test
    public void getNotExistPropertyMap() throws IOException {
        Map<Integer, String> map = ConfigurationUtil.getConfigurationEntriesMap(configuration, LIST_NAME + NOT_EXIST_NUM_OF_FIELD);
        Assertions.assertEquals(0, map.size());
    }

    @Test
    public void getPropertyList() throws IOException {
        List<String> map = ConfigurationUtil.getConfigurationEntriesList(configuration, LIST_NAME);
        Assertions.assertEquals(LIST_LENGTH, map.size());
        for (int i = 0; i < LIST_LENGTH; i++) {
            Assertions.assertTrue(map.contains(String.format(LIST_ELEMENT_NAME, i)));
        }
    }

    @Test
    public void getNotExistPropertyList() throws IOException {
        List<String> map = ConfigurationUtil.getConfigurationEntriesList(configuration, LIST_NAME + NOT_EXIST_NUM_OF_FIELD);
        Assertions.assertEquals(0, map.size());
    }

    protected abstract IConfiguration getConfigurationType();
}
