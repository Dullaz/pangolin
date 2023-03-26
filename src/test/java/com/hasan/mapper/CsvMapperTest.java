package com.hasan.mapper;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

public class CsvMapperTest {

    @Test
    void testSimpleRecord() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var inputStream = CsvMapperTest.class.getResourceAsStream("/simpleRecord.csv");
        assert inputStream != null;

        var reader = new InputStreamReader(inputStream);
        var records = CsvMapper.parse(SimpleRecord.class, reader);

        var record_1 = records.get(0);

        assert record_1.getStringType().equals("ABC");
    }
}
