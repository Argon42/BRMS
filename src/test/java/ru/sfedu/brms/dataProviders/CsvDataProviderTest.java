package ru.sfedu.brms.dataProviders;

public class CsvDataProviderTest extends DataProviderTest {
    @Override
    protected IDataProvider loadDataProvider() {
        return new CSVDataProvider();
    }
}

