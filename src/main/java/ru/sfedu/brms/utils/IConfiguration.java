package ru.sfedu.brms.utils;

import java.io.IOException;
import java.util.Properties;

public interface IConfiguration {
    Properties getProperties() throws IOException;
}
