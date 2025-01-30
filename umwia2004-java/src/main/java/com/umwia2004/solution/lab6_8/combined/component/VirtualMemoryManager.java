package com.umwia2004.solution.lab6_8.combined.component;

import com.umwia2004.solution.lab6_8.combined.algorithm.ReplacementAlgorithm;
import com.umwia2004.solution.lab6_8.combined.domain.Page;
import com.umwia2004.solution.lab6_8.combined.table.MemoryMapTable;
import com.umwia2004.solution.lab6_8.combined.table.PageMapTable;
import lombok.RequiredArgsConstructor;

import static com.umwia2004.solution.util.LogUtil.logBlue;
import static com.umwia2004.solution.util.LogUtil.logGreen;

/**
 * Manages virtual memory operations, including demand paging and page replacement.
 * <p>
 * This class handles the loading of virtual pages into main memory and integrates
 * with a {@link ReplacementAlgorithm} to determine page replacements when memory is full.
 *
 * @author fyiernzy
 * @version 1.0
 */
@RequiredArgsConstructor
public class VirtualMemoryManager {
    private final MainMemory mainMemory;
    private final PageMapTable pageMapTable;
    private final MemoryMapTable memoryMapTable;
    private final ReplacementAlgorithm replacementAlgorithm;

    /**
     * Simulates the component used when the VirtualMemoryManager encounters a page fault.
     * Theoretically, a component should exist to partition a program into virtual pages,
     * allowing the {@code VirtualMemoryManager} to load these pages during page faults.
     * However, implementing such a component would introduce unnecessary complexity,
     * diverting focus from the {@code ReplacementAlgorithm}
     * Instead, we use a {@code PageProvider} to simulate the partitioning process and the retrieval of pages.
     */
    private final PageProvider pageProvider = new PageProvider();

    /**
     * Handles demand paging for a given virtual page number (VPN).
     * <p>
     * If the page (represented by the VPN) exists in main memory, it is a <b>page hit</b>.
     * In this case, the method updates the reference in the replacement algorithm without reloading the page.
     * <p>
     * If the page does not exist in main memory, it is a <b>page fault</b>, which triggers
     * loading the page into memory and updating the corresponding tables.
     *
     * @param vpn The virtual page number to load.
     * @return True if the page is already loaded (page hit), false otherwise (page fault).
     */
    public boolean demandPaging(String vpn) {
        boolean isPageHit = pageMapTable.hasMapping(vpn);
        if (!isPageHit) {
            // Simulating the page loading process
            loadPageIntoMainMemory(pageProvider.providePage(vpn));
        }
        replacementAlgorithm.accessVpn(vpn);
        return isPageHit;
    }

    /**
     * Loads a page into the main memory, handling page faults if necessary.
     * <p>
     * If the main memory has available physical frames, the page is loaded directly.
     * If no frames are available, a page fault is handled by replacing an existing page.
     *
     * @param page The page to load into the main memory.
     */
    public void loadPageIntoMainMemory(Page page) {
        //  1. Request the next available physical frame number (PFN) from main memory.
        //  - If the operation is successful, it means main memory has available physical frames for locating the new page.
        //    No further action is needed in this case.
        //
        //  - If the operation fails due to a lack of available frames, a page fault occurs.
        //    This triggers the replacement of an existing page, and the PFN held by the replaced page is reused.
        //
        //  Notes
        //  - Using `orElseGet` ensures `handlePageFault` is only called when `findNextPfn` returns an empty Optional.
        //  - Using `orElse` would invoke `handlePageFault` regardless, potentially causing unintended side effects.
        String pfn = mainMemory.findNextPfn().orElseGet(this::handlePageFault);

        // 2. Map the new page to the retrieved PFN.
        mapPage(pfn, page);
    }

    /**
     * Handles a page fault by selecting a page to replace in memory.
     * The replacement algorithm determines which page to remove, freeing up space for the new page.
     *
     * @return The physical frame number (PFN) that will be reused.
     */
    private String handlePageFault() {
        // 1. Find the VPN of the page to replace and its corresponding PFN.
        String vpnToReplace = replacementAlgorithm.findVpnToReplace();
        String pfnToReplace = pageMapTable.translate(vpnToReplace);
        Page pageToReplace = mainMemory.getPageByPfn(pfnToReplace);

        // 2. Remove the page and update all relevant tables.
        unmapPage(pfnToReplace, pageToReplace);

        // 1. Find the VPN of the page to replace and its corresponding PFN.
        return pfnToReplace;
    }

    /**
     * Maps a page to a physical frame number (PFN) in main memory.
     *
     * @param pfn     The physical frame number where the page should be loaded.
     * @param newPage The page to be loaded.
     */
    private void mapPage(String pfn, Page newPage) {
        newPage.setAccessBit(Page.REFERENCED);
        mainMemory.loadPage(pfn, newPage);
        pageMapTable.map(newPage.getVpn(), pfn);
        memoryMapTable.map(pfn, newPage.getVpn());
        replacementAlgorithm.addVpn(newPage.getVpn());
    }

    /**
     * Unmaps a page from a physical frame number (PFN) in main memory.
     *
     * @param pfn     The physical frame number where the page is located.
     * @param oldPage The page to be removed.
     */
    private void unmapPage(String pfn, Page oldPage) {
        oldPage.setAccessBit(Page.NOT_REFERENCED);
        mainMemory.removePage(pfn);
        memoryMapTable.unmap(pfn);
        pageMapTable.unmap(oldPage.getVpn());
        replacementAlgorithm.removeVpn(oldPage.getVpn());
    }

    public void displayStatus() {
        logBlue("Virtual Memory Manager Status");

        logGreen("Page Map Table (PMT)");
        pageMapTable.display();
        System.out.println();

        logGreen("Memory Map Table (MMT)");
        memoryMapTable.display();
        System.out.println();

        logGreen("Loaded Pages");
        mainMemory.display();
        System.out.println();

        logGreen("Replacement Algorithm");
        replacementAlgorithm.display();
        System.out.println();
    }
}
