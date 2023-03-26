package com.hasan.mapper;

import com.hasan.annotation.Column;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.reflect.FieldUtils;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/***
 * This class defines functions that allows for mapping a CSV file to a list of java objects
 * This is used in conjunction with the Column annotation class

 * Usage:
 * 1. Define a java object with private fields annotated with Column, defining their column name as
 *      expected in the CSV file
 * 2. Create a Reader from the CSV file and call the "parse" function in this class with the object class
 *      you defined and the reader

 * e.g. CsvMapper.parse(SimpleRecord.class, reader);
 *
 */
public class CsvMapper {

    /***
     * This function takes in a generic class definition and a reader object representing the csv file.
     * It will identify any fields inside the class that are annotated with "Column"
     * It will then read line by line from the reader and extract data for each field by using the columnName
     *      defined inside the "Column" annotation for that specific field.
     * It will then attempt to convert the data extracted into the field data type using the simpleConvert function
     *
     * @param recordClazz the class of the java object where each row will be saved into
     * @param reader reader wrapper around the csv inputstream
     * @param <T> The type of the java object each csv row is saved into
     * @return List of the java objects the csv rows are saved into
     */
    public static <T> List<T> parse(Class<T> recordClazz, Reader reader)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
            IOException {

        List<T> records = new ArrayList<>();

        List<Field> validFields = new ArrayList<>();

        for (Field field : recordClazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                validFields.add(field);
            }
        }

        CSVParser parser = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(reader);

        Constructor<T> constructor = recordClazz.getConstructor();
        for (CSVRecord csvRecord : parser.getRecords()) {
            T record = constructor.newInstance();
            for (Field field : validFields) {
                Column column = field.getAnnotation(Column.class);
                String value = csvRecord.get(!Objects.equals(column.name(), "") ? column.name() : field.getName());
                Object obj = simpleConvert(value, column.mutation(), field.getType());
                FieldUtils.writeDeclaredField(record, field.getName(), obj, true);
            }
            records.add(record);
        }

        return records;
    }

    /***
     * Function to convert a string value into a specific data type specified by the clazz value.
     * The mutation string is to assist in converting complex types.
     * e.g. for datetime objects, this will be the expected format of the datetime
     *      for enum objects, this is an optional name of the method to use other than "valueOf"
     * Naturally this returns an object that can be unsafely cast into the type requested
     * For the purpose of this flow, the object is written directly to a field
     *
     * @param value String data to convert
     * @param mutation Additional parameter to assist in converting complex types, can be null
     * @param clazz The class to convert the string into
     * @return Object
     */
    private static Object simpleConvert(String value, String mutation, Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (value.isEmpty() || value.isBlank()) {
            return null;
        }
        if (String.class == clazz) {
            return value;
        }

        if (clazz.isEnum()) {
            if (mutation.isEmpty()) {
                return clazz.getMethod("valueOf", String.class).invoke(null, value);
            } else {
                return clazz.getMethod(mutation, String.class).invoke(null, value);
            }
        }

        if (LocalDateTime.class == clazz) {
            if (mutation.isEmpty()) {
                return LocalDateTime.parse(value);
            } else {
                return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(mutation));
            }
        }

        if (Long.class == clazz || Long.TYPE == clazz) {
            return Long.parseLong(value);
        }
        if (Double.class == clazz || Double.TYPE == clazz) {
            return Double.parseDouble(value);
        }
        if (Float.class == clazz || Float.TYPE == clazz) {
            return Float.parseFloat(value);
        }
        if (BigDecimal.class == clazz) {
            return new BigDecimal(value);
        }
        if (Boolean.class == clazz || Boolean.TYPE == clazz) {
            return Boolean.parseBoolean(value);
        }
        if (Byte.class == clazz || Byte.TYPE == clazz) {
            return Byte.parseByte(value);
        }
        if (Short.class == clazz || Short.TYPE == clazz) {
            return Short.parseShort(value);
        }
        if (Integer.class == clazz || Integer.TYPE == clazz) {
            return Integer.parseInt(value);
        }
        throw new RuntimeException("Can't convert string to type " + clazz.getName() + " because " +
                "class is unsupported");
    }


}
