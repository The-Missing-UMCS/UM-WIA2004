package com.umwia2004.solution.lab1_2.compiled;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FCFS extends Scheduler {
    public FCFS(List<Process> pendingProcesses) {
        super(pendingProcesses);
    }

    @Override
    public void schedule() {
        // 1. Sort processes by arrival time to follow the FCFS algorithm.
        pendingProcesses.sort(Comparator.comparing(Process::getArrivalTime));

        // 2. Set the current time with the arrival time of the first process.
        LocalDateTime currentTime = Optional.ofNullable(pendingProcesses.getFirst())
            .map(Process::getArrivalTime)
            .orElseThrow();

        // 3. Execute the processes in the order they arrive.
        while (!pendingProcesses.isEmpty()) {
            synchronized (this) {
                Process nextProcess = pendingProcesses.removeFirst();

                // 3.1. Handle cases where a process arrives after the current time.
                //      Example:
                //      - Process A (arrivalTime=0, burstTime=1)
                //      - Process B (arrivalTime=3, burstTime=2)
                //      After A finishes, B has not yet arrived.
                //      Hence, we will update the current time to the arrival time of B.
                if (nextProcess.getArrivalTime().isAfter(currentTime)) {
                    currentTime = nextProcess.getArrivalTime();
                }

                // 3.2. Execute the next process and update the current time.
                currentTime = executeProcess(nextProcess, currentTime);
            }
        }
    }
}
