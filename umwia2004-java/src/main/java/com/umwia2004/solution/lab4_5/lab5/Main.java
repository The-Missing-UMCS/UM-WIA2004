package com.umwia2004.solution.lab4_5.lab5;

import com.umwia2004.solution.lab4_5.lab5.domain.Process;
import com.umwia2004.solution.lab4_5.lab5.strategy.MemoryAllocationStrategy;

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
                memory.clear();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static Memory getMemory() {
        return new Memory(List.of(15, 25, 20, 35, 30, 10, 50));
    }

    private static List<MemoryAllocationStrategy> getAllocationStrategies() {
        return List.of(new BestFit());
    }

    private static List<Process> getProcesses() {
        return List.of(
            new Process("P1", 10),
            new Process("P2", 20),
            new Process("P3", 30),
            new Process("P4", 15),
            new Process("P5", 5)
        );
    }
}
