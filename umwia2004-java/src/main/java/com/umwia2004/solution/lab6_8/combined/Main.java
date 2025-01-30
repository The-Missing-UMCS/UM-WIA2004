package com.umwia2004.solution.lab6_8.combined;

import com.umwia2004.solution.lab6_8.combined.algorithm.LRU;
import com.umwia2004.solution.lab6_8.combined.algorithm.ReplacementAlgorithm;
import com.umwia2004.solution.lab6_8.combined.component.MainMemory;
import com.umwia2004.solution.lab6_8.combined.component.VirtualMemoryManager;
import com.umwia2004.solution.lab6_8.combined.table.MemoryMapTable;
import com.umwia2004.solution.lab6_8.combined.table.PageMapTable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 1. Initialize the components
        VirtualMemoryManager virtualMemoryManager = getVirtualMemoryManager();

        // 2. Simulate Demand Paging
        int pageFaults = 0;
        List<String> requiredVpn = List.of("vpn2", "vpn3", "vpn7", "vpn1", "vpn2", "vpn4", "vpn5");
        for (String vpn : requiredVpn) {
            System.out.println(vpn);
            boolean isVpnLoaded = virtualMemoryManager.demandPaging(vpn);
            virtualMemoryManager.displayStatus();
            pageFaults += !isVpnLoaded ? 1 : 0;
        }

        System.out.printf("Total number of page faults: %d%n", pageFaults);
    }

    private static @NotNull VirtualMemoryManager getVirtualMemoryManager() {
        String[] pfns = new String[]{"pfn1", "pfn2", "pfn3", "pfn4"};

        MainMemory mainMemory = MainMemory.initialize(pfns);
        PageMapTable pageMapTable = new PageMapTable();
        MemoryMapTable memoryMapTable = new MemoryMapTable(pfns);
        ReplacementAlgorithm replacementAlgorithm = new LRU();

        return new VirtualMemoryManager(
            mainMemory, pageMapTable, memoryMapTable, replacementAlgorithm
        );
    }
}
