package ru.sfedu.brms.dataProviders;

public class XmlDataProviderTest extends DataProviderTest {
    @Override
    protected IDataProvider loadDataProvider() {
        return new XMLDataProvider();
    }
}
