package com.umwia2004.solution.lab1_2.compiled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Scheduler {
    protected final List<Process> pendingProcesses;
    protected final List<Process> completedProcesses;

    public Scheduler(List<Process> pendingProcesses) {
        this.pendingProcesses = pendingProcesses;
        this.completedProcesses = new ArrayList<>();
    }

    public abstract void schedule();

    protected synchronized LocalDateTime executeProcess(Process nextProcess, LocalDateTime currentTime) {
        nextProcess.executeAt(currentTime);
        completedProcesses.add(nextProcess);
        pendingProcesses.remove(nextProcess);
        return currentTime.plus(nextProcess.getBurstTime());
    }

    public List<Process> getPendingProcesses() {
        return Collections.unmodifiableList(pendingProcesses);
    }

    public List<Process> getCompletedProcesses() {
        return Collections.unmodifiableList(completedProcesses);
    }
}
