package com.umwia2004.solution.lab1_2.compiled;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Process {
    private final String processId;
    private final LocalDateTime arrivalTime;
    private final Duration burstTime;

    private LocalDateTime startTime;
    private Duration turnaroundTime;
    private Duration waitingTime;

    public void executeAt(LocalDateTime startTime) {
        this.startTime = startTime;
        this.waitingTime = Duration.between(arrivalTime, startTime);
        this.turnaroundTime = waitingTime.plus(burstTime);
    }
}
