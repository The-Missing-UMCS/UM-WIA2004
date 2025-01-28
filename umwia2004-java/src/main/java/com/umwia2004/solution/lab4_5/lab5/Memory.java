package com.umwia2004.solution.lab4_5.lab5;

import com.umwia2004.solution.lab4_5.lab5.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.lab5.domain.Process;
import com.umwia2004.solution.util.TableUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Memory {
    private final ArrayList<MemoryBlock> blockRegistry;

    public Memory(List<Integer> partitions) {
        this.blockRegistry = new ArrayList<>(partitions.size());
        for (Integer partition : partitions) {
            this.blockRegistry.add(new MemoryBlock(partition, MemoryBlock.FREE, null));
        }
    }

    public void allocate(List<Process> processes, BiFunction<List<MemoryBlock>, Process, MemoryBlock> allocationStrategy) {
        for (Process process : processes) {
            MemoryBlock memoryBlock = allocationStrategy.apply(blockRegistry, process);
            memoryBlock.allocate(process);
        }
    }

    public void clear() {
        blockRegistry.forEach(MemoryBlock::release);
    }

    public void printMemoryState() {
        String[] headers = {"Block Size", "Status", "Process ID", "Process Size"};
        String[][] rows = blockRegistry.stream()
            .map(memoryBlock -> new String[]{
                String.valueOf(memoryBlock.getBlockSize()),
                memoryBlock.isFree() ? "Free" : "Occupied",
                memoryBlock.isOccupied() ? memoryBlock.getProcess().id() : "",
                memoryBlock.isOccupied() ? String.valueOf(memoryBlock.getProcess().size()) : ""
            })
            .toArray(String[][]::new);
        TableUtil.renderTable(headers, rows);
    }
}
