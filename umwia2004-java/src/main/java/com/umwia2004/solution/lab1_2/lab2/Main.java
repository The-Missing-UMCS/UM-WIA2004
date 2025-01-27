package com.umwia2004.solution.lab1_2.lab2;

import com.umwia2004.solution.util.TableUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Preparing the data
        // Assuming that all the processes arrive at time 0
        List<Process> processes = new ArrayList<>(List.of(
            new Process("P1", 5),
            new Process("P2", 3),
            new Process("P3", 8),
            new Process("P4", 6),
            new Process("P5", 4)
        ));

        // 2. Sorted the processes by burst time, simulating the SJF algorithm
        processes.sort(Comparator.comparing(Process::getBurstTime));

        // 3. Calculating the waiting time and turnaround time
        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            process.executeAt(i == 0 ? 0 : processes.get(i - 1).getTurnaroundTime());
        }

        // 4. Printing the result
        System.out.println("Shortest Job First (SJF) Scheduling Algorithm");
        renderResult(processes);
    }

    private static void renderResult(List<Process> processes) {
        String[] headers = new String[]{
            "Process ID",
            "Burst Time",
            "Waiting Time",
            "Turnaround Time"
        };

        String[][] rows = new String[processes.size()][headers.length];

        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            rows[i] = new String[]{
                process.getId(),
                String.valueOf(process.getBurstTime()),
                String.valueOf(process.getWaitingTime()),
                String.valueOf(process.getTurnaroundTime())
            };
        }

        TableUtil.renderTable(headers, rows);
    }
}
