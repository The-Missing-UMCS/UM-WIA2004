package com.umwia2004.solution.lab6_8.combined.component;

import com.umwia2004.solution.lab6_8.combined.domain.Page;
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

    private final Map<String, Page> loadedPages;

    /**
     * Initializes the main memory with the given physical frame numbers (PFNs).
     * Each PFN is initially unoccupied (null).
     *
     * @param pfns The array of physical frame numbers (PFNs) to initialize.
     * @return A new instance of MainMemory.
     */
    public static MainMemory initialize(String[] pfns) {
        Map<String, Page> pages = new HashMap<>();
        for (String pfn : pfns) {
            pages.put(pfn, null);
        }
        return new MainMemory(pages);
    }

    /**
     * Finds the next available physical frame number (PFN) that is unoccupied.
     *
     * @return An Optional containing the next available PFN, or empty if no PFN is available.
     */
    public Optional<String> findNextPfn() {
        return loadedPages.entrySet().stream()
            .filter(entry -> Objects.isNull(entry.getValue()))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    /**
     * Loads a page into the specified physical frame number (PFN).
     *
     * @param pfn  The physical frame number where the page will be loaded.
     * @param page The page to load into memory.
     * @throws IllegalArgumentException If the PFN or page is null.
     */
    public void loadPage(String pfn, Page page) {
        Asserts.nonNull(pfn, IllegalArgumentException.class, "The pfn is null");
        Asserts.nonNull(page, IllegalArgumentException.class, "The page is null");
        loadedPages.put(pfn, page);
    }

    /**
     * Retrieves the page located at the specified physical frame number (PFN).
     *
     * @param pfn The physical frame number to query.
     * @return The page located at the PFN, or null if the PFN is unoccupied.
     */
    public Page getPageByPfn(String pfn) {
        return loadedPages.get(pfn);
    }

    /**
     * Removes the page from the specified physical frame number (PFN), marking it as unoccupied.
     *
     * @param pfn The physical frame number to clear.
     */
    public void removePage(String pfn) {
        loadedPages.put(pfn, null);
    }

    public void display() {
        System.out.println("Main Memory Status");
        TableUtil.renderTable(
            new String[]{
                "Physical Frame Number (PFN)",
                "Virtual Page Number (VPN)",
                "Page's Content"
            },
            loadedPages.entrySet().stream()
                .map(entry -> new String[]{
                    entry.getKey(),
                    Optional.ofNullable(entry.getValue()).map(Page::getVpn).orElse(""),
                    Optional.ofNullable(entry.getValue()).map(Page::getContent).orElse("")
                })
                .toArray(String[][]::new)
        );
    }
}
