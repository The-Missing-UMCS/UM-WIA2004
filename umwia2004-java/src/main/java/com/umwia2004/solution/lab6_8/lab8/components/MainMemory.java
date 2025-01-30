package com.umwia2004.solution.lab6_8.lab8.components;

import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the main memory in a virtual memory system.
 * It manages the mapping between physical frame numbers (PFNs) and virtual page numbers (VPNs).
 *
 * @author fyiernzy
 * @version 1.0
 */
@RequiredArgsConstructor
public class MainMemory {
    private final Map<String, String> loadedPages;

    /**
     * Creates a MainMemory instance with the specified physical frame numbers (PFNs).
     * All PFNs are initially unoccupied (mapped to null).
     *
     * @param pfns The array of physical frame numbers to initialize.
     * @return A new MainMemory instance.
     */
    public static MainMemory create(String[] pfns) {
        Map<String, String> loadedPages = new HashMap<>();
        for (String pfn : pfns) {
            loadedPages.put(pfn, null);
        }
        return new MainMemory(loadedPages);
    }

    /**
     * Finds the next available physical frame number (PFN) that is unoccupied.
     *
     * @return An Optional containing the next available PFN, or empty if no PFN is available.
     */
    public Optional<String> findNextPfn() {
        return loadedPages.entrySet().stream()
            .filter(entry -> entry.getValue() == null)
            .map(Map.Entry::getKey)
            .findFirst();
    }

    /**
     * Loads a page into the specified physical frame number (PFN).
     *
     * @param pfn The physical frame number where the page will be loaded.
     * @param vpn The vpn to load into memory.
     * @throws IllegalArgumentException If the PFN or vpn is null.
     */
    public void loadVpn(String pfn, String vpn) {
        Asserts.isFalse(isPfnOccupied(pfn), IllegalStateException.class, "The pfn is already occupied");
        loadedPages.put(pfn, vpn);
    }

    /**
     * Removes a virtual page from memory by its VPN and returns the freed PFN.
     *
     * @param vpn The virtual page number to remove.
     * @return The physical frame number (PFN) that was freed, or null if the VPN was not found.
     */
    public String removeByVpn(String vpn) {
        for (Map.Entry<String, String> entry : loadedPages.entrySet()) {
            if (Objects.equals(entry.getValue(), vpn)) {
                loadedPages.put(entry.getKey(), null);
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if a virtual page is currently loaded in memory.
     *
     * @param vpn The virtual page number to check.
     * @return True if the VPN is loaded, false otherwise.
     */
    public boolean isVpnLoaded(String vpn) {
        return loadedPages.containsValue(vpn);
    }

    /**
     * Checks if a physical frame number (PFN) is occupied by a virtual page.
     *
     * @param pfn The physical frame number to check.
     * @return True if the PFN is occupied, false otherwise.
     */
    public boolean isPfnOccupied(String pfn) {
        return loadedPages.get(pfn) != null;
    }

    public void displayStatus() {
        TableUtil.renderTable(
            new String[]{
                "Physical Frame Number (PFN)",
                "Virtual Page Number (VPN)"
            },
            loadedPages.entrySet().stream()
                .map(entry -> new String[]{
                    entry.getKey(),
                    Objects.nonNull(entry.getValue()) ? entry.getValue() : ""})
                .toArray(String[][]::new)
        );
    }
}
