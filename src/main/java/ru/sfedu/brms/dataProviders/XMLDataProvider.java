package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.InstantPersistenceDelegate;
import ru.sfedu.brms.UUIDPersistenceDelegate;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Xml data provider.
 */
public class XMLDataProvider extends FileDataProvider {
    private static final Logger log = LogManager.getLogger(XMLDataProvider.class);

    @Override
    protected void afterCreateFolder(Class<?> ruleClass, String fullPath) {
        beanToXml(new ArrayList<>(), ruleClass);
    }

    @Override
    protected <T> List<T> fileToBean(Class<T> beanType) {
        return xmlToBean(beanType);
    }

    @Override
    protected <T> Result beanToFile(List<?> beans, Class<?> beanClass) {
        return beanToXml(beans, beanClass);
    }

    @Override
    protected <T> String createPath(Class<T> object) throws IOException {
        if (object == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        String rootPath = loadRootPath();
        String csvExtension = ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_XML);

        return rootPath + object.getSimpleName() + csvExtension;
    }

    @Override
    protected String loadRootPath() throws IOException {
        return ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH);
    }

    private <T> List<T> xmlToBean(Class<T> beanType) {
        try (InputStream fileStream = new FileInputStream(createPath(beanType))) {
            try (XMLDecoder decoder = new XMLDecoder(fileStream)) {
                List<T> beans = (List<T>) decoder.readObject();

                decoder.close();
                fileStream.close();

                return beans;
            }
        } catch (Exception e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    private Result beanToXml(List<?> beans, Class<?> beanClass) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(createPath(beanClass))) {
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
                try (XMLEncoder encoder = new XMLEncoder(bufferedOutputStream)) {
                    encoder.setPersistenceDelegate(UUID.class, new UUIDPersistenceDelegate());
                    encoder.setPersistenceDelegate(Instant.class, new InstantPersistenceDelegate());

                    encoder.writeObject(beans);

                    return Result.SUCCESS;
                }
            }
        } catch (Exception e) {
            log.error(e);
            return Result.ERROR;
        }
    }
}
