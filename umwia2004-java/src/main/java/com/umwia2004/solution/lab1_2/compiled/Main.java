package com.umwia2004.solution.lab1_2.compiled;

import com.umwia2004.solution.util.LogUtil;
import com.umwia2004.solution.util.TableUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LogUtil.logInfo("First Come First Serve (FCFS) Scheduling Algorithm");
        FCFS fcfs = new FCFS(getPendingProcesses());
        fcfs.schedule();
        printStatistic(fcfs.getCompletedProcesses());

        LogUtil.logInfo("Shortest Job First (SJF) Scheduling Algorithm");
        SJF sjf = new SJF(getPendingProcesses());
        sjf.schedule();
        printStatistic(sjf.getCompletedProcesses());
    }

    private static void printStatistic(List<Process> completedProcesses) {
        String[] headers = new String[]{
            "Process ID",
            "Arrival Time",
            "Start Time",
            "Waiting Time",
            "Burst Time",
            "Turnaround Time"
        };

        String[][] rows = completedProcesses.stream()
            .map(process -> {
                final String minuteFormat = "%2d minutes";
                final String timeFormat = "%02d:%02d";

                return new String[]{
                    process.getProcessId(),
                    timeFormat.formatted(process.getArrivalTime().getHour(), process.getArrivalTime().getMinute()),
                    timeFormat.formatted(process.getStartTime().getHour(), process.getStartTime().getMinute()),
                    minuteFormat.formatted(process.getWaitingTime().toMinutes()),
                    minuteFormat.formatted(process.getBurstTime().toMinutes()),
                    minuteFormat.formatted(process.getTurnaroundTime().toMinutes())
                };
            })
            .toArray(String[][]::new);

        TableUtil.renderTable(headers, rows);
    }

    private static List<Process> getPendingProcesses() {
        return new ArrayList<>(List.of(
            new Process("P1", mockedTime(0), mockedDuration(7)),
            new Process("P2", mockedTime(2), mockedDuration(4)),
            new Process("P3", mockedTime(5), mockedDuration(1)),
            new Process("P4", mockedTime(19), mockedDuration(5)),
            new Process("P5", mockedTime(4), mockedDuration(7))
        ));
    }

    private static LocalDateTime mockedTime(int minute) {
        return LocalDateTime.of(2025, 1, 1, 0, minute);
    }

    private static Duration mockedDuration(int minutes) {
        return Duration.ofMinutes(minutes);
    }
}
