package ru.sfedu.brms.dataProviders;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.brms.models.enums.Result;
import ru.sfedu.brms.utils.ConfigurationUtil;
import ru.sfedu.brms.utils.Constants;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Csv data provider.
 */
public class CSVDataProvider extends FileDataProvider {
    private static final Logger log = LogManager.getLogger(CSVDataProvider.class);

    @Override
    protected <T> List<T> fileToBean(Class<T> beanType) {
        return csvToBean(beanType);
    }

    @Override
    protected <T> Result beanToFile(List<?> beans, Class<?> beanClass) {
        return beanToCsv(beans, beanClass);
    }

    @Override
    protected <T> String createPath(Class<T> object) throws IOException {
        if (object == null) throw new IllegalArgumentException(Constants.ARGUMENT_IS_NULL);

        String rootPath = loadRootPath();
        String csvExtension = ConfigurationUtil.getConfigurationEntry(Constants.FILE_EXTENSION_CSV);

        return rootPath + object.getSimpleName() + csvExtension;
    }

    @Override
    protected String loadRootPath() throws IOException {
        return ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
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
}
