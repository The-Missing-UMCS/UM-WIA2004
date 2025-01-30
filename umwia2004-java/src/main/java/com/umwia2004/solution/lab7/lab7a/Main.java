package com.umwia2004.solution.lab7.lab7a;

import com.umwia2004.solution.util.Storage;

import static com.umwia2004.solution.util.LogUtil.*;

public class Main {
    public static void main(String[] args) {
        // Has safe sequence
        runCase("case1",
            new int[] {3, 3, 2},
            new int[][] {
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2},
                {2, 1, 1},
                {0, 0, 2}
            },
            new int[][] {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
                {4, 3, 3}
            });

        // Has safe sequence
        runCase("case2",
            new int[] {1, 5, 2, 0},
            new int[][] {
                {0, 0, 1, 2},
                {1, 0, 0, 0},
                {1, 3, 5, 4},
                {0, 6, 3, 2},
                {0, 0, 1, 4}
            },
            new int[][] {
                {0, 0, 1, 2},
                {1, 7, 5, 0},
                {2, 3, 5, 6},
                {0, 6, 5, 2},
                {0, 6, 5, 6}
            });

        // No safe sequence
        runCase("case3",
            new int[] {1, 1, 1},
            new int[][] {
                {0, 2, 0},
                {2, 0, 2},
                {1, 1, 1}
            },
            new int[][] {
                {3, 3, 3},
                {2, 2, 2},
                {3, 3, 3}
            });

        // No safe sequence
        runCase("case4",
            new int[] {2, 1, 1},
            new int[][] {
                {1, 0, 1},
                {0, 1, 0},
                {1, 1, 1}
            },
            new int[][] {
                {2, 2, 2},
                {5, 4, 3},
                {3, 3, 3}
            });
    }

    private static void runCase(String caseId, int[] available, int[][] allocation, int[][] max) {
        Storage<Sequence> storage = new SequenceTable();
        logBlue("Running case: %s".formatted(caseId));
        Banker banker = Banker.of(available, allocation, max, storage);
        boolean isSafeSequence = banker.tryAllocate();
        storage.display();
        if (isSafeSequence) {
            logGreen("Safe sequence found");
        } else {
            logRed("No safe sequence found");
        }
        System.out.println();
    }
}
