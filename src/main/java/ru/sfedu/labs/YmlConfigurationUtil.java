package ru.sfedu.labs;

import org.yaml.snakeyaml.Yaml;
import ru.sfedu.brms.utils.IConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class YmlConfigurationUtil implements IConfiguration {
    private static final String SYSTEM_PROPERTY_NAME = "YmlPropertyPath";
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.yml";
    private static final Properties configuration = new Properties();

    public YmlConfigurationUtil() {
    }

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

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    @Override
    public Properties getProperties() throws IOException {
        return getConfiguration();
    }

}
