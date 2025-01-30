package com.umwia2004.solution.lab7.util;

import com.umwia2004.solution.util.Asserts;

import java.util.Arrays;

public class IntArray {
    public final int length;
    private final int[] array;

    public IntArray(int[] array) {
        this.array = array;
        this.length = array.length;
    }

    public IntArray add(IntArray other) {
        Asserts.equals(array.length, other.array.length, IllegalArgumentException.class, "Arrays must have the same length");
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] + other.array[i];
        }
        return new IntArray(result);
    }

    public boolean allGreaterThan(IntArray other) {
        Asserts.equals(array.length, other.array.length, IllegalArgumentException.class, "Arrays must have the same length");
        for (int i = 0; i < array.length; i++) {
            if (array[i] <= other.array[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean allSmallerEqualTo(IntArray other) {
        Asserts.equals(array.length, other.array.length, IllegalArgumentException.class, "Arrays must have the same length");
        for (int i = 0; i < array.length; i++) {
            if (array[i] > other.array[i]) {
                return false;
            }
        }
        return true;
    }

    public IntArray replicate() {
        return new IntArray(Arrays.copyOf(array, array.length));
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
