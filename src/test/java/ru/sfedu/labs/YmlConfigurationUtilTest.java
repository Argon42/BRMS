package ru.sfedu.labs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class YmlConfigurationUtilTest {
    private static final String FIELD_NAME = "testField%s";
    private static final String VALUE_NAME = "testValue%s";
    private static final int COUNT_OF_TEST_FIELDS = 3;
    private static final int NOT_EXIST_NUM_OF_FIELD = -1;

    private static final String LIST_NAME = "testList";
    private static final String LIST_ELEMENT_NAME = "TestListElement%s";
    private static final int LIST_LENGTH = 3;

    @Test
    public void getProperty() throws IOException {
        for (int i = 0; i < COUNT_OF_TEST_FIELDS; i++) {
            String expectedValue = String.format(VALUE_NAME, i);
            String actualValue = YmlConfigurationUtil.getConfigurationEntry(String.format(FIELD_NAME, i));
            Assertions.assertEquals(expectedValue, actualValue);
        }
    }

    @Test
    public void getNotExistProperty() throws IOException {
        Assertions.assertNull(YmlConfigurationUtil.getConfigurationEntry(String.format(FIELD_NAME, NOT_EXIST_NUM_OF_FIELD)));
    }

    @Test
    public void getPropertyMap() throws IOException {
        Map<Integer, String> map = YmlConfigurationUtil.getConfigurationEntriesMap(LIST_NAME);
        Assertions.assertEquals(LIST_LENGTH, map.size());
        for (int i = 0; i < LIST_LENGTH; i++) {
            Assertions.assertEquals(map.get(i), String.format(LIST_ELEMENT_NAME, i));
        }
    }

    @Test
    public void getNotExistPropertyMap() throws IOException {
        Map<Integer, String> map = YmlConfigurationUtil.getConfigurationEntriesMap(LIST_NAME + NOT_EXIST_NUM_OF_FIELD);
        Assertions.assertEquals(0, map.size());
    }

    @Test
    public void getPropertyList() throws IOException {
        List<String> map = YmlConfigurationUtil.getConfigurationEntriesList(LIST_NAME);
        Assertions.assertEquals(LIST_LENGTH, map.size());
        for (int i = 0; i < LIST_LENGTH; i++) {
            Assertions.assertTrue(map.contains(String.format(LIST_ELEMENT_NAME, i)));
        }
    }

    @Test
    public void getNotExistPropertyList() throws IOException {
        List<String> map = YmlConfigurationUtil.getConfigurationEntriesList(LIST_NAME + NOT_EXIST_NUM_OF_FIELD);
        Assertions.assertEquals(0, map.size());
    }
}
