package com.umwia2004.solution.lab4_5.lab4.strategy;

import com.umwia2004.solution.lab4_5.lab4.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.lab4.domain.Process;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

public class BestFit implements MemoryAllocationStrategy {
    @Override
    public BiFunction<List<MemoryBlock>, Process, MemoryBlock> findBlock() {
        return (blockRegistry, process) -> blockRegistry.stream()
            .filter(memoryBlock -> memoryBlock.canAllocate(process))
            .min(Comparator.comparing(MemoryBlock::getBlockSize))
            .orElse(null);
    }

    @Override
    public String name() {
        return "Best Fit";
    }
}
