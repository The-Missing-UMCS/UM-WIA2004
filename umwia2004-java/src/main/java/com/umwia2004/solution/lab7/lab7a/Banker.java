package com.umwia2004.solution.lab7.lab7a;

import com.umwia2004.solution.lab7.util.BoolArray;
import com.umwia2004.solution.lab7.util.IntArray;
import com.umwia2004.solution.lab7.util.IntMatrix;
import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.Recordable;
import com.umwia2004.solution.util.Storage;

import java.util.function.Function;

/**
 * Implements the Banker's Algorithm for deadlock avoidance.
 * This class ensures that system resources are allocated safely to processes without causing deadlocks.
 * It extends {@link Recordable} to maintain a record of the allocation sequence.
 */
public class Banker extends Recordable<Integer, Sequence> {
    private static final boolean FINISHED = true;

    private final IntMatrix allocationMatrix;
    private final IntMatrix maxMatrix;
    private final IntMatrix needMatrix;
    private final BoolArray processStatus;
    private IntArray available;

    /**
     * Constructs a Banker instance with the given matrices and available resources.
     *
     * @param available        The array representing available resources.
     * @param allocationMatrix The matrix representing resource allocation for each process.
     * @param maxMatrix        The matrix representing maximum demand for each process.
     * @param storage          The storage to keep track of the allocation sequence.
     */
    private Banker(IntArray available, IntMatrix allocationMatrix, IntMatrix maxMatrix, Storage<Sequence> storage) {
        super(storage);
        this.available = available;
        this.allocationMatrix = allocationMatrix;
        this.maxMatrix = maxMatrix;
        this.needMatrix = this.maxMatrix.subtract(this.allocationMatrix);
        this.processStatus = new BoolArray(allocationMatrix.row);
    }

    /**
     * Creates a new Banker instance after validating dimensions of input matrices and available resources.
     *
     * @param available        The available resources array.
     * @param allocationMatrix The resource allocation matrix.
     * @param maxMatrix        The maximum demand matrix.
     * @param storage          The storage to keep allocation records.
     * @return A new Banker instance with the given parameters.
     * @throws IllegalArgumentException If the matrix dimensions do not match the expected constraints.
     */
    public static Banker of(int[] available, int[][] allocationMatrix, int[][] maxMatrix, Storage<Sequence> storage) {
        Asserts.sameDimension(allocationMatrix, maxMatrix, IllegalArgumentException.class, "Allocation and max must have the same dimension");
        Asserts.sameDimension(available, allocationMatrix, IllegalArgumentException.class, "Available and allocation must have the same dimension");
        Asserts.sameDimension(available, maxMatrix, IllegalArgumentException.class, "Available and max must have the same dimension");
        return new Banker(new IntArray(available), new IntMatrix(allocationMatrix), new IntMatrix(maxMatrix), storage);
    }

    /**
     * Tries to allocate resources safely while ensuring the system remains in a safe state.
     *
     * @return {@code true} if a safe sequence is found, {@code false} otherwise.
     */
    public boolean tryAllocate() {
        try {
            while (true) {
                // Note:
                // - We track changes each iteration because this loop never explicitly breaks.
                // - The only way to break the loop is to detect whether the sequence is safe.
                // - To determine if the sequence is safe:
                //   - If the sequence is safe, then all processes must be FINISHED.
                //   - If the sequence is not safe, then at least one process must be UNFINISHED.
                //
                // Problem:
                // - We can conclude that an unsafe sequence has at least one process remains UNFINISHED.
                // - However, we can't infer that the sequence is automatically unsafe just because there is at least one UNFINISHED process
                // - It could simply need more iterations.
                //
                // Solution:
                // - We must check if there are any status changes in each iteration.
                // - If there are no changes and not all processes are FINISHED, we infer the sequence is unsafe.
                boolean statusChanged = false;

                for (int processIndex = 0; processIndex < processStatus.length; processIndex++) {
                    // Skip processes that are already finished (their resources have been released).
                    // If a process is unfinished and can safely be allocated, allocate resources to it.
                    if (!processStatus.get(processIndex) && isSafeToAllocate(processIndex)) {
                        performAction(processIndex);
                        statusChanged = true;
                    }
                }

                // If all processes are finished, the sequence is safe.
                if (processStatus.allTrue()) {
                    return true;
                }

                // At this point, processStatus.allTrue() is false, indicating that not all processes are finished.
                // This is logically equivalent to: if (!statusChanged && !processStatus.allTrue()).
                // If there are no changes in process statuses and not all processes are finished.
                // the function will return false, meaning the sequence is unsafe,
                if (!statusChanged) {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred");
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Defines the action of allocating resources to a process and updating its status.
     *
     * @return A function that takes a process index and returns the corresponding allocation sequence.
     */
    @Override
    public Function<Integer, Sequence> action() {
        return (processIndex) -> {
            // 1. Update available resources by adding allocated resources held by the process to current available resources
            //    This simulates the release of resources by the process when it finishes
            IntArray previousAvailable = available.replicate();
            IntArray updatedAvailable = available.add(allocationMatrix.get(processIndex));

            // 2. Update the internal state
            //    - Mark the process as FINISHED, so it won't be considered.
            //    - Update available resources
            this.processStatus.set(processIndex, FINISHED);
            this.available = updatedAvailable;

            // 3. Generate the OUTPUT as required by the Recordable class
            return new Sequence(
                String.valueOf(processIndex),
                needMatrix.get(processIndex),
                allocationMatrix.get(processIndex),
                previousAvailable, updatedAvailable);
        };
    }

    /**
     * Checks whether allocating resources to a given process is safe.
     *
     * @param processIndex The index of the process to check.
     * @return {@code true} if the process can be safely allocated resources, {@code false} otherwise.
     */
    private boolean isSafeToAllocate(int processIndex) {
        return needMatrix.get(processIndex).allSmallerEqualTo(available);
    }
}
