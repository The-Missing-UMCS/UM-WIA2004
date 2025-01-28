package com.umwia2004.solution.lab4_5.lab5.strategy;

import com.umwia2004.solution.lab4_5.lab5.domain.MemoryBlock;
import com.umwia2004.solution.lab4_5.lab5.domain.Process;

import java.util.List;
import java.util.function.BiFunction;

public interface MemoryAllocationStrategy {
    BiFunction<List<MemoryBlock>, Process, MemoryBlock> findBlock();

    String name();
}
