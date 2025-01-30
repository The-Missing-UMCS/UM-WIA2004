package com.umwia2004.solution.lab7.lab7b;

import com.umwia2004.solution.lab7.util.BoolArray;
import com.umwia2004.solution.lab7.util.IntArray;
import com.umwia2004.solution.lab7.util.IntMatrix;
import com.umwia2004.solution.util.TableUtil;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Banker {

    private final IntMatrix allocationMatrix;
    private final IntMatrix needMatrix;
    private final BoolArray processStatus;
    @Getter
    private final List<Sequence> storage;
    private IntArray availableArray;

    private Banker(IntArray availableArray, IntMatrix allocationMatrix, IntMatrix maxMatrix) {
        this.availableArray = availableArray;
        this.allocationMatrix = allocationMatrix;
        this.needMatrix = maxMatrix.subtract(allocationMatrix);
        this.processStatus = new BoolArray(allocationMatrix.row);
        this.storage = new LinkedList<>();
    }

    public static Banker of(int[] available, int[][] allocationMatrix, int[][] maxMatrix) {
        // Assuming that the dimensions of the allocationMatrix and maxMatrix are the same
        // Assuming that the dimensions of the available and allocationMatrix are the same
        return new Banker(new IntArray(available), new IntMatrix(allocationMatrix), new IntMatrix(maxMatrix));
    }

    public boolean tryAllocate() {

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
                    handleProcess(processIndex);
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

    }

    private void handleProcess(int processIndex) {
        // 1. Update available resources by adding allocated resources held by the process to current available resources
        //    This simulates the release of resources by the process when it finishes
        IntArray previousAvailable = availableArray.replicate();
        IntArray updatedAvailable = availableArray.add(allocationMatrix.get(processIndex));

        // 2. Update the internal state
        //    - Mark the process as true indicating it is finished, so it won't be considered.
        //    - Update available resources
        this.processStatus.set(processIndex, true);
        this.availableArray = updatedAvailable;

        // 3. Store the sequence
        storage.add(new Sequence(
            String.valueOf(processIndex),
            needMatrix.get(processIndex),
            allocationMatrix.get(processIndex),
            previousAvailable, updatedAvailable));
    }

    private boolean isSafeToAllocate(int processIndex) {
        return needMatrix.get(processIndex).allSmallerEqualTo(availableArray);
    }

    public void displaySequence() {
        TableUtil.renderTable(
            new String[]{
                "ID",
                "Need",
                "Previous Available",
                "Allocation",
                "Updated Available"
            },
            storage.stream()
                .map(sequence ->
                    new String[]{
                        sequence.id(),
                        sequence.need().toString(),
                        sequence.previousAvailable().toString(),
                        sequence.allocation().toString(),
                        sequence.updatedAvailable().toString()
                    })
                .toArray(String[][]::new)
        );
    }
}
