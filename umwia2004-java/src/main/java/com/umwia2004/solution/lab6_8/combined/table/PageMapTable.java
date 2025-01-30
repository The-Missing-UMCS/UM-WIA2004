package com.umwia2004.solution.lab6_8.combined.table;

import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Page Map Table (PMT), which tracks the mapping between
 * virtual page numbers (VPNs) and physical frame numbers (PFNs).
 */
public class PageMapTable {
    Map<String, String> pageMapTable = new HashMap<>();

    /**
     * Maps a virtual page number (VPN) to a physical frame number (PFN).
     *
     * @param vpn The virtual page number to map.
     * @param pfn The physical frame number to associate with the VPN.
     * @throws IllegalStateException If the VPN is already mapped.
     */
    public void map(String vpn, String pfn) {
        Asserts.isFalse(hasMapping(vpn), IllegalStateException.class, "Page already mapped");
        pageMapTable.put(vpn, pfn);
    }

    /**
     * Unmaps a virtual page number (VPN), removing its association with a physical frame number (PFN).
     *
     * @param vpn The virtual page number to unmap.
     * @throws IllegalStateException If the VPN is not currently mapped.
     */
    public void unmap(String vpn) {
        Asserts.isTrue(hasMapping(vpn), IllegalStateException.class, "Page not mapped");
        pageMapTable.remove(vpn);
    }

    /**
     * Checks if a virtual page number (VPN) is currently mapped to a physical frame number (PFN).
     *
     * @param vpn The virtual page number to check.
     * @return True if the VPN is mapped, false otherwise.
     */
    public boolean hasMapping(String vpn) {
        return pageMapTable.containsKey(vpn);
    }

    /**
     * Translates a virtual page number (VPN) to its corresponding physical frame number (PFN).
     *
     * @param vpn The virtual page number to translate.
     * @return The associated physical frame number (PFN), or null if the VPN is not mapped.
     */
    public String translate(String vpn) {
        return pageMapTable.getOrDefault(vpn, null);
    }

    public void display() {
        TableUtil.renderTable(
            new String[]{
                "Virtual Page Number (VPN)",
                "Physical Frame Number (PFN)"
            },
            pageMapTable.entrySet().stream()
                .map(entry -> new String[]{entry.getKey(), entry.getValue()})
                .toArray(String[][]::new)
        );
    }
}
