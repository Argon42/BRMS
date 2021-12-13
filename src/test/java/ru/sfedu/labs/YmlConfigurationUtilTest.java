package ru.sfedu.labs;

import ru.sfedu.brms.utils.IConfiguration;

public class YmlConfigurationUtilTest extends AbstractConfigurationUtilTest {

    @Override
    protected IConfiguration getConfigurationType() {
        return new YmlConfigurationUtil();
    }
}
