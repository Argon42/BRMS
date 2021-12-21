package ru.sfedu.brms.dataProviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XmlDataProviderTest extends DataProviderTest {
    private static final Logger log = LogManager.getLogger(XmlDataProviderTest.class);

    @Override
    protected IDataProvider loadDataProvider() {
        return new XMLDataProvider();
    }

    @Override
    protected Logger getLog() {
        return log;
    }
}
