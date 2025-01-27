package com.umwia2004.solution.lab1_2.lab1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Process {
    private final String id;
    private final int burstTime;
    private int waitingTime;
    private int turnaroundTime;

    public void executeAt(int startTime) {
        this.waitingTime = startTime;
        this.turnaroundTime = waitingTime + burstTime;
    }
}
