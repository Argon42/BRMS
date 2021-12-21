package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvDataProviderTest extends DataProviderTest {
    private static final Logger log = LogManager.getLogger(CsvDataProviderTest.class);

    @Override
    protected IDataProvider loadDataProvider() {
        return new CSVDataProvider();
    }

    @Override
    protected Logger getLog(){
        return log;
    }
}

