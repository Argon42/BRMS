package ru.sfedu.labs;

import ru.sfedu.brms.utils.IConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConfigurationUtil implements IConfiguration {
    private static final String SYSTEM_PROPERTY_NAME = "PropertyPath";
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.properties";
    private static final Properties configuration = new Properties();

    public PropertyConfigurationUtil() {
    }

    private static void loadConfiguration() throws IOException {
        String propertyPath = System.getProperty(SYSTEM_PROPERTY_NAME);
        File nf = new File(propertyPath != null ? propertyPath : DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(nf)) {
            configuration.load(in);
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
