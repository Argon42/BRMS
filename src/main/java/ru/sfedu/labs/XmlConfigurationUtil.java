package ru.sfedu.labs;

import ru.sfedu.brms.utils.IConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XmlConfigurationUtil implements IConfiguration {
    private static final String SYSTEM_PROPERTY_NAME = "XmlPropertyPath";
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.xml";
    private static final Properties configuration = new Properties();

    public XmlConfigurationUtil() {
    }

    private static void loadConfiguration() throws IOException {
        String xmlPropertyPath = System.getProperty(SYSTEM_PROPERTY_NAME);
        File nf = new File(xmlPropertyPath != null ? xmlPropertyPath : DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(nf)) {
            configuration.loadFromXML(in);
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
