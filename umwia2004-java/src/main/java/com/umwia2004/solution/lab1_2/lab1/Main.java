package com.umwia2004.solution.lab1_2.lab1;

import com.umwia2004.solution.util.TableUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Preparing the data
        //    Assuming that all the processes arrive at time 0
        List<Process> processes = List.of(
            new Process("P1", 5),
            new Process("P2", 3),
            new Process("P3", 8),
            new Process("P4", 6),
            new Process("P5", 4)
        );

        // 2. Calculating the waiting time and turnaround time
        for(int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            process.executeAt(i == 0 ? 0 : processes.get(i - 1).getTurnaroundTime());
        }

        // 3. Printing the result
        System.out.println("First Come First Serve (FCFS) Scheduling Algorithm");
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
