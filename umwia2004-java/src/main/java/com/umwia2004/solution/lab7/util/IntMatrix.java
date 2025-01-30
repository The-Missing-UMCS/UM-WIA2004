package com.umwia2004.solution.lab7.util;

import com.umwia2004.solution.util.Asserts;

public class IntMatrix {
    public final int row;
    public final int col;
    public final int[] dimension;
    private final int[][] matrix;

    public IntMatrix(int[][] matrix) {
        this.matrix = matrix;
        this.row = matrix.length;
        this.col = matrix[0].length;
        this.dimension = new int[]{row, col};
    }

    public IntMatrix add(IntMatrix other) {
        Asserts.sameDimension(matrix, other.matrix, IllegalArgumentException.class, "Matrices must have the same dimension");
        int[][] result = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = matrix[i][j] + other.matrix[i][j];
            }
        }
        return new IntMatrix(result);
    }

    public IntMatrix subtract(IntMatrix other) {
        Asserts.sameDimension(matrix, other.matrix, IllegalArgumentException.class, "Matrices must have the same dimension");
        int[][] result = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = matrix[i][j] - other.matrix[i][j];
            }
        }
        return new IntMatrix(result);
    }

    public IntArray get(int index) {
        Asserts.smallerThan(index, row, IllegalArgumentException.class, "Index must be smaller than row");
        return new IntArray(matrix[index]);
    }
}
