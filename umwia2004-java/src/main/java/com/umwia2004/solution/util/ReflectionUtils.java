package com.umwia2004.solution.util;

import org.apache.commons.lang3.reflect.FieldUtils;

public class ReflectionUtils {
    public static Object readField(Object object, String fieldName) throws IllegalAccessException {
        return FieldUtils.readField(object, fieldName, true);
    }

    public static String readFieldAsString(Object object, String fieldName) {
        return readFieldAsString(object, fieldName, "N/A");
    }

    public static String readFieldAsString(Object object, String fieldName, String defaultValue) {
        try {
            return String.valueOf(readField(object, fieldName));
        } catch (IllegalAccessException e) {
            return defaultValue;
        }
    }
}
