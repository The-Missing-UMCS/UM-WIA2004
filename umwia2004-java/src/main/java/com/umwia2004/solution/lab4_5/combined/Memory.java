package com.umwia2004.solution.lab4_5.combined;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;
import com.umwia2004.solution.lab4_5.combined.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.combined.domain.MemoryStatistic;
import com.umwia2004.solution.lab4_5.combined.domain.Process;
import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

public class Memory {
    private final ArrayList<MemoryBlock> blockRegistry;
    private final ReentrantLock lock;

    public Memory(List<DataSize> partitions) {
        // 1. Validate the partitions list
        Asserts.isNotEmpty(partitions, IllegalArgumentException.class, "Partitions list cannot be empty");
        this.blockRegistry = new ArrayList<>(partitions.size());

        // 2. Validate each partition
        for (DataSize partition : partitions) {
            Asserts.nonNull(partition, IllegalArgumentException.class, "Partition size cannot be null");
            Asserts.greaterThan(partition, DataSize.ZERO, IllegalArgumentException.class, "Partition size must be greater than zero");
            this.blockRegistry.add(new MemoryBlock(partition, MemoryBlock.FREE, null));
        }

        // 3. Initialize the lock
        this.lock = new ReentrantLock();
    }

    public void allocate(List<Process> processes, BiFunction<List<MemoryBlock>, Process, MemoryBlock> allocationStrategy) {
        processes.forEach(process -> findAndAllocate(process, allocationStrategy));
    }

    private void findAndAllocate(Process process, BiFunction<List<MemoryBlock>, Process, MemoryBlock> allocationStrategy) {
        Asserts.nonNull(process, IllegalArgumentException.class, "Process cannot be null");
        lock.lock();
        try {
            MemoryBlock memoryBlock = allocationStrategy.apply(blockRegistry, process);
            Asserts.nonNull(memoryBlock, IllegalStateException.class, "No memory block found for process: " + process.id());
            memoryBlock.allocate(process);
        } finally {
            lock.unlock();
        }
    }

    public void deallocate(int blockIndex) {
        Asserts.between(blockIndex, 0, blockRegistry.size() - 1, IllegalArgumentException.class, "Invalid block index");
        lock.lock();
        try {
            MemoryBlock blockRecord = blockRegistry.get(blockIndex);
            Asserts.isTrue(blockRecord.isOccupied(), IllegalStateException.class, "Block is already free");
            blockRecord.release();
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        blockRegistry.forEach(MemoryBlock::releaseSafely);
    }

    public MemoryStatistic getMemoryStatistic() {
        DataSize totalMemory = blockRegistry.stream()
            .map(MemoryBlock::getBlockSize)
            .reduce(DataSize.ZERO, DataSize::add);

        DataSize totalMemoryUsed = blockRegistry.stream()
            .filter(MemoryBlock::isOccupied)
            .map(memoryBlock -> memoryBlock.getProcess().size())
            .reduce(DataSize.ZERO, DataSize::add);

        DataSize totalInternalFragmentation = blockRegistry.stream()
            .filter(MemoryBlock::isOccupied)
            .map(MemoryBlock::internalFragmentation)
            .reduce(DataSize.ZERO, DataSize::add);

        return new MemoryStatistic(Collections.unmodifiableList(blockRegistry),
            totalMemory, totalMemoryUsed, totalInternalFragmentation);
    }

    public void printMemoryState() {
        MemoryStatistic memoryStatistic = getMemoryStatistic();

        // 1. Print the memory state
        String[] headers = {"Block Size", "Status", "Process ID", "Process Size", "Internal Fragmentation"};
        String[][] rows = memoryStatistic.memoryBlocks().stream()
            .map(memoryBlock -> new String[]{
                String.valueOf(memoryBlock.getBlockSize()),
                memoryBlock.isFree() ? "Free" : "Occupied",
                memoryBlock.isOccupied() ? memoryBlock.getProcess().id() : "",
                memoryBlock.isOccupied() ? String.valueOf(memoryBlock.getProcess().size()) : "",
                memoryBlock.isOccupied() ? memoryBlock.internalFragmentation().toString() : "N/A"
            })
            .toArray(String[][]::new);
        TableUtil.renderTable(headers, rows);

        // 2. Print the aggregated statistics
        TableUtil.renderTable(
            new String[]{
                "Total Memory",
                "Total Memory Used",
                "Total Internal Fragmentation"
            },
            new String[][]{{
                memoryStatistic.totalMemory().toString(),
                memoryStatistic.totalMemoryUsed().toString(),
                memoryStatistic.totalInternalFragmentation().toString()
            }}
        );
    }
}
