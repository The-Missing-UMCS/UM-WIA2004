package com.umwia2004.solution.lab6_8.lab6;

import com.umwia2004.solution.lab6_8.lab6.algo.LRU;
import com.umwia2004.solution.lab6_8.lab6.components.MainMemory;
import com.umwia2004.solution.lab6_8.lab6.components.VirtualMemoryManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] pfns = {"pfn0", "pfn1", "pfn2", "pfn3"};
        VirtualMemoryManager virtualMemoryManager = new VirtualMemoryManager(MainMemory.create(pfns), new LRU());
        List<String> requiredVpn = List.of("vpn2", "vpn3", "vpn7", "vpn1", "vpn2", "vpn4", "vpn5");

        int pageFaults = 0;
        for(String vpn : requiredVpn) {
            System.out.println(vpn);
            pageFaults += virtualMemoryManager.demandPaging(vpn) ? 0 : 1;
            virtualMemoryManager.displayStatus();
        }
        System.out.printf("Total Page Faults: %d%n%n", pageFaults);

    }
}
