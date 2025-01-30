package com.umwia2004.solution.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;

public class Asserts {
    public static void sameDimension(int[][] matrix1, int[][] matrix2, Class<? extends Exception> clazz, String message) {
        equals(matrix1.length, matrix2.length, clazz, message);
        for (int i = 0; i < matrix1.length; i++) {
            equals(matrix1[i].length, matrix2[i].length, clazz, message);
        }
    }

    public static void sameDimension(int[] array, int[][] matrix, Class<? extends Exception> clazz, String message) {
        for (int[] ints : matrix) {
            equals(array.length, ints.length, clazz, message);
        }
    }

    public static void equals(int value, int target, Class<? extends Exception> clazz, String message) {
        isTrue(value == target, clazz, message);
    }

    public static <T extends Comparable<T>> void smallerThan(T value, T target, Class<? extends Exception> clazz, String message) {
        isTrue(value.compareTo(target) < 0, clazz, message);
    }

    public static <T extends Comparable<T>> void greaterThan(T value, T target, Class<? extends Exception> clazz, String message) {
        isTrue(value.compareTo(target) > 0, clazz, message);
    }

    public static void between(int value, int startInclusive, int endInclusive, Class<? extends Exception> clazz, String message) {
        isTrue(value >= startInclusive && value <= endInclusive, clazz, message);
    }

    public static void isNotEmpty(Collection<?> collection, Class<? extends Exception> clazz, String message) {
        isTrue(CollectionUtils.isNotEmpty(collection), clazz, message);
    }

    public static void nonNull(Object object, Class<? extends Exception> clazz, String message) {
        isTrue(null != object, clazz, message);
    }

    public static void isFalse(boolean condition, Class<? extends Exception> clazz, String message) {
        isTrue(!condition, clazz, message);
    }

    public static void isTrue(boolean condition, Class<? extends Exception> clazz, String message) {
        if (!condition) {
            try {
                throw clazz.getDeclaredConstructor(String.class).newInstance(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
