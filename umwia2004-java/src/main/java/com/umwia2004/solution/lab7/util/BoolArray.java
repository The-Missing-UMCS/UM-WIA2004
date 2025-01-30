package com.umwia2004.solution.lab7.util;

import com.umwia2004.solution.util.Asserts;

public class BoolArray {
    public final int length;
    private final boolean[] array;

    public BoolArray(boolean[] array) {
        this.array = array;
        this.length = array.length;
    }

    public BoolArray(int size) {
        this(new boolean[size]);
    }

    public boolean get(int index) {
        Asserts.smallerThan(index, array.length, IllegalArgumentException.class, "Index must be smaller than array length");
        return array[index];
    }

    public void set(int index, boolean value) {
        Asserts.smallerThan(index, array.length, IllegalArgumentException.class, "Index must be smaller than array length");
        array[index] = value;
    }

    public boolean allTrue() {
        for (boolean b : array) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    public boolean allFalse() {
        for (boolean b : array) {
            if (b) {
                return false;
            }
        }
        return true;
    }
}
