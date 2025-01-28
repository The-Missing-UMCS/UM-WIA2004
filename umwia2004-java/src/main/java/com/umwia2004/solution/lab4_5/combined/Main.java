package com.umwia2004.solution.lab4_5.combined;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;
import com.umwia2004.solution.lab4_5.combined.domain.Process;
import com.umwia2004.solution.lab4_5.combined.strategy.BestFit;
import com.umwia2004.solution.lab4_5.combined.strategy.FirstFit;
import com.umwia2004.solution.lab4_5.combined.strategy.MemoryAllocationStrategy;

import java.util.List;

import static com.umwia2004.solution.util.LogUtil.logBlue;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = getProcesses();
        List<MemoryAllocationStrategy> allocationStrategies = getAllocationStrategies();
        Memory memory = getMemory();

        allocationStrategies.forEach(strategy -> {
            try {
                logBlue("Strategy: " + strategy.name());
                memory.allocate(processes, strategy.findBlock());
                memory.printMemoryState();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                memory.clear();
            }
        });
    }

    private static Memory getMemory() {
        return new Memory(List.of(
            DataSize.bytes(15),
            DataSize.bytes(25),
            DataSize.bytes(20),
            DataSize.bytes(35),
            DataSize.bytes(30),
            DataSize.bytes(10),
            DataSize.bytes(50)
        ));
    }

    private static List<MemoryAllocationStrategy> getAllocationStrategies() {
        return List.of(new BestFit(), new FirstFit());
    }

    private static List<Process> getProcesses() {
        return List.of(
            new Process("P1", DataSize.bytes(10)),
            new Process("P2", DataSize.bytes(20)),
            new Process("P3", DataSize.bytes(30)),
            new Process("P4", DataSize.bytes(15)),
            new Process("P5", DataSize.bytes(5))
        );
    }
}
