package ru.sfedu.brms;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.util.UUID;

public class UUIDConverter extends AbstractBeanField<UUID, String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if(s.isBlank())
            return null;
        return UUID.fromString(s);
    }
}
