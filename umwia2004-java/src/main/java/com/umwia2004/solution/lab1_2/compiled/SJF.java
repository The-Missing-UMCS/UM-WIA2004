package com.umwia2004.solution.lab1_2.compiled;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class SJF extends Scheduler {
    public SJF(List<Process> pendingProcesses) {
        super(pendingProcesses);
    }

    @Override
    public void schedule() {
        // 1. Set the current time to the arrival time of the first process.
        LocalDateTime currentTime = pendingProcesses.stream()
            .min(Comparator.comparing(Process::getArrivalTime))
            .map(Process::getArrivalTime)
            .orElseThrow();

        // 2. Schedule the processes.
        while (!pendingProcesses.isEmpty()) {
            // 2.1. Find the process that has already arrived and has the shortest burst time.
            final LocalDateTime finalCurrentTime = currentTime;
            Process nextProcess = pendingProcesses.stream()
                .filter(p -> !p.getArrivalTime().isAfter(finalCurrentTime))
                .min(Comparator.comparing(Process::getBurstTime))
                .orElse(null);

            // 2.2. If no process has arrived, select the next process in the list.
            //      Update the current time to the arrival time of that process.
            if (null == nextProcess) {
                nextProcess = pendingProcesses.stream()
                    .min(Comparator.comparing(Process::getArrivalTime))
                    .orElseThrow();

                currentTime = nextProcess.getArrivalTime();
            }

            // 2.3. Execute the process and update the current time.
            currentTime = executeProcess(nextProcess, currentTime);
        }
    }
}
