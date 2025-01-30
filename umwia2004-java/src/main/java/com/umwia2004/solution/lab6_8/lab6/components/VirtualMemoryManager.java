package com.umwia2004.solution.lab6_8.lab6.components;

import com.umwia2004.solution.lab6_8.lab6.algo.ReplacementAlgorithm;
import lombok.RequiredArgsConstructor;

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
    private final ReplacementAlgorithm replacementAlgorithm;

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
        boolean isPageHit = mainMemory.isVpnLoaded(vpn);
        if (!isPageHit) {
            loadPageIntoMainMemory(vpn);
        }
        replacementAlgorithm.accessVpn(vpn);
        return isPageHit;
    }

    /**
     * Loads a page into main memory, handling page faults if necessary.
     * <p>
     * If main memory has available physical frames, the vpn is loaded directly.
     * If no frames are available, a page fault is handled by replacing an existing vpn.
     *
     * @param vpn The VPN to load into main memory.
     */
    public void loadPageIntoMainMemory(String vpn) {
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
        loadVpn(pfn, vpn);
    }

    /**
     * Handles a page fault by selecting a vpn to replace in memory.
     * The replacement algorithm determines which vpn to remove, freeing up space for the new page.
     *
     * @return The physical frame number (PFN) of the replaced page.
     */
    private String handlePageFault() {
        // 1. Find the VPN of the page to replace
        String vpnToReplace = replacementAlgorithm.findVpnToReplace();

        // 2. Remove the VPN from main memory and the VPN registry in the replacement algorithm.
        //    Return the PFN of the removed VPN.
        return removeVpn(vpnToReplace);
    }

    /**
     * Maps a page to a physical frame number (PFN) in main memory.
     *
     * @param pfn The physical frame number where the page should be loaded.
     * @param vpn The VPN to load.
     */
    private void loadVpn(String pfn, String vpn) {
        mainMemory.loadVpn(pfn, vpn);
        replacementAlgorithm.loadVpn(vpn);
    }

    /**
     * Unmaps a page from a physical frame number (PFN) in main memory.
     *
     * @param oldVpn The VPN of the page to remove.
     * @return The physical frame number (PFN) that was freed.
     */
    private String removeVpn(String oldVpn) {
        replacementAlgorithm.removeVpn(oldVpn);
        return mainMemory.removeByVpn(oldVpn);
    }

    public void displayStatus() {
        logGreen("Loaded Pages");
        mainMemory.displayStatus();
        System.out.println();

        logGreen("VPN Registry");
        replacementAlgorithm.displayStatus();
        System.out.println();
    }
}
