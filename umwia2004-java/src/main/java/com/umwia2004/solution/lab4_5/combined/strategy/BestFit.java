package com.umwia2004.solution.lab4_5.combined.strategy;

import com.umwia2004.solution.lab4_5.combined.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.combined.domain.Process;

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
