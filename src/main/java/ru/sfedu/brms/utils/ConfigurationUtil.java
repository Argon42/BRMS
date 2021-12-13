package ru.sfedu.brms.utils;

import ru.sfedu.labs.PropertyConfigurationUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigurationUtil {
    private static final IConfiguration configuration = new PropertyConfigurationUtil();

    public ConfigurationUtil() {
    }

    public static Map<Integer, String> getConfigurationEntriesMap(IConfiguration configuration, String mapKey) throws IOException {
        Properties properties = configuration.getProperties();
        return properties
                .keySet()
                .stream()
                .map(key -> (String) key)
                .filter(key -> key.matches(mapKey + "\\[.*]"))
                .collect(Collectors.toMap(
                        s -> Integer.parseInt(s.substring(mapKey.length() + 1, s.length() - 1)),
                        properties::getProperty)
                );
    }

    public static Map<Integer, String> getConfigurationEntriesMap(String mapKey) throws IOException {
        return getConfigurationEntriesMap(configuration, mapKey);
    }

    public static List<String> getConfigurationEntriesList(IConfiguration configuration, String listKey) throws IOException {
        Properties properties = configuration.getProperties();
        return properties
                .keySet()
                .stream()
                .map(key -> (String) key)
                .filter(key -> key.matches(listKey + "\\[.*]"))
                .sorted()
                .map(properties::getProperty)
                .collect(Collectors.toList());
    }

    public static List<String> getConfigurationEntriesList(String listKey) throws IOException {
        return getConfigurationEntriesList(configuration, listKey);
    }

    public static String getConfigurationEntry(IConfiguration configuration, String key) throws IOException {
        return configuration.getProperties().getProperty(key);
    }

    public static String getConfigurationEntry(String key) throws IOException {
        return getConfigurationEntry(configuration, key);
    }

}
