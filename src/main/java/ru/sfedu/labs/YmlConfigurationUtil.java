package ru.sfedu.labs;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Configuration utility. Allows to get configuration properties from the
 * xml configuration file
 *
 * @author Roman Sidelnikov
 */
public class YmlConfigurationUtil {
    private static final String SYSTEM_PROPERTY_NAME = "YmlPropertyPath";
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.yml";
    private static final Properties configuration = new Properties();

    public YmlConfigurationUtil() {
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * or from SystemPropertyPath "<code>SYSTEM_PROPERTY_NAME</code>"
     *
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException {
        String ymlPropertyPath = System.getProperty(SYSTEM_PROPERTY_NAME);
        File nf = new File(ymlPropertyPath != null ? ymlPropertyPath : DEFAULT_CONFIG_PATH);

        try (InputStream in = new FileInputStream(nf)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(in);
            configuration.putAll(yamlData);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Gets configuration entry value
     *
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException {
        return getConfiguration().getProperty(key);
    }

    public static Map<Integer, String> getConfigurationEntriesMap(String mapKey) throws IOException {
        Properties properties = getConfiguration();
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

    public static List<String> getConfigurationEntriesList(String listKey) throws IOException {
        return getConfiguration()
                .keySet()
                .stream()
                .map(key -> (String) key)
                .filter(key -> key.matches(listKey + "\\[.*]"))
                .sorted()
                .map(configuration::getProperty)
                .collect(Collectors.toList());
    }

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

}
