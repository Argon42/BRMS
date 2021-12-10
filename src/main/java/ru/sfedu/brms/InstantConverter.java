package ru.sfedu.brms;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.time.Instant;

public class InstantConverter extends AbstractBeanField<Instant, String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return Instant.parse(s);
    }
}
