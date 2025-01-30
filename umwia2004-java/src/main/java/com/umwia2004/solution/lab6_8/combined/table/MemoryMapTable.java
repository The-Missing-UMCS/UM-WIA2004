package com.umwia2004.solution.lab6_8.combined.table;

import com.umwia2004.solution.lab6_8.combined.domain.FrameMapping;
import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Memory Map Table (MMT), which tracks the mapping between
 * physical frame numbers (PFNs) and virtual page numbers (VPNs).
 */
public class MemoryMapTable {
    private final Map<String, FrameMapping> memoryMapTable;

    /**
     * Initializes the Memory Map Table with the given physical frame numbers (PFNs).
     * Each frame is initially marked as free.
     *
     * @param frames The array of physical frame numbers (PFNs) to initialize.
     */
    public MemoryMapTable(String[] frames) {
        this.memoryMapTable = initializeMemoryMapTable(frames);
    }

    /**
     * Maps a physical frame number (PFN) to a virtual page number (VPN).
     *
     * @param pfn The physical frame number to map.
     * @param vpn   The virtual page number to associate with the PFN.
     * @throws IllegalStateException If the PFN is not found in the table.
     */
    public void map(String pfn, String vpn) {
        getMappingOrThrow(pfn).mapFrame(vpn);
    }

    /**
     * Unmaps a physical frame number (PFN), marking it as free.
     *
     * @param pfn The physical frame number to unmap.
     * @throws IllegalStateException If the PFN is not found in the table.
     */
    public void unmap(String pfn) {
        getMappingOrThrow(pfn).releaseFrame();
    }

    /**
     * Retrieves the memory mapping for a given physical frame number (PFN).
     *
     * @param pfn The physical frame number to query.
     * @return The corresponding MemoryMapping object.
     * @throws IllegalStateException If the PFN is not found in the table.
     */
    private FrameMapping getMappingOrThrow(String pfn) {
        FrameMapping frameMapping = memoryMapTable.get(pfn);
        Asserts.nonNull(frameMapping, IllegalStateException.class, "Frame not found");
        return frameMapping;
    }

    public void display() {
        TableUtil.renderTable(
            new String[]{
                "Physical Frame Number (PFN)",
                "Virtual Page Number (VPN)",
                "Status"
            },
            memoryMapTable.entrySet().stream()
                .map(entry -> new String[]{
                    entry.getKey(),
                    entry.getValue().getStatus() == FrameMapping.STATUS_OCCUPIED
                        ? entry.getValue().getMappedVpn()
                        : "",
                    entry.getValue().getStatus() ? "FREE" : "OCCUPIED"
                })
                .toArray(String[][]::new)
        );
    }

    /**
     * Initializes the Memory Map Table with the given physical frame numbers (PFNs).
     * Each frame is initially marked as free.
     *
     * @param frames The array of physical frame numbers (PFNs) to initialize.
     * @return A map containing the initialized MemoryMapping objects.
     */
    private static Map<String, FrameMapping> initializeMemoryMapTable(String[] frames) {
        Map<String, FrameMapping> memoryMapTable = new HashMap<>();
        for (String frame : frames) {
            memoryMapTable.put(frame, FrameMapping.empty());
        }
        return memoryMapTable;
    }
}
