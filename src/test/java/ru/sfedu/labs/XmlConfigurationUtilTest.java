package ru.sfedu.labs;

import ru.sfedu.brms.utils.IConfiguration;

public class XmlConfigurationUtilTest extends AbstractConfigurationUtilTest {

    @Override
    protected IConfiguration getConfigurationType() {
        return new XmlConfigurationUtil();
    }
}
