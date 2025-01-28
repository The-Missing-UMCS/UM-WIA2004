package com.umwia2004.solution.lab4_5.combined.domain;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;

import java.util.List;

public record MemoryStatistic(List<MemoryBlock> memoryBlocks,
                              DataSize totalMemory,
                              DataSize totalMemoryUsed,
                              DataSize totalInternalFragmentation) {
}
