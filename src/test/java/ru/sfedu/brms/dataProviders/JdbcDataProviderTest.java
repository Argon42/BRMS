package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcDataProviderTest extends DataProviderTest {

    private static final Logger log = LogManager.getLogger(JdbcDataProviderTest.class);

    @Override
    protected IDataProvider loadDataProvider() {
        return new JdbcDataProvider();
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
