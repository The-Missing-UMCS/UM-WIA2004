package com.umwia2004.solution.lab4_5.combined.strategy;

import com.umwia2004.solution.lab4_5.combined.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.combined.domain.Process;

import java.util.List;
import java.util.function.BiFunction;

public class FirstFit implements MemoryAllocationStrategy {
    @Override
    public BiFunction<List<MemoryBlock>, Process, MemoryBlock> findBlock() {
        return (blockRegistry, process) -> blockRegistry.stream()
            .filter(memoryBlock -> memoryBlock.canAllocate(process))
            .findFirst()
            .orElse(null);
    }

    @Override
    public String name() {
        return "First Fit";
    }
}
