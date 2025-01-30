package com.umwia2004.solution.lab6_8.combined.algorithm;

import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class representing a page replacement algorithm.
 * It maintains a registry of virtual page numbers (VPNs) and their reference counts.
 *
 * @author fyiernzy
 * @version 1.0
 */
public abstract class ReplacementAlgorithm {
    protected final Map<String, Integer> vpnRegistry = new HashMap<>();

    /**
     * Finds the VPN to replace based on the replacement algorithm's policy.
     *
     * @return The VPN to replace.
     * @throws IllegalStateException If no pages are available to replace.
     */
    public String findVpnToReplace() {
        return vpnRegistry.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    /**
     * Adds a new VPN to the registry when a page is loaded into memory.
     * Ensures that duplicate VPNs are not added.
     *
     * @param newVpn The VPN of the newly loaded page.
     * @throws IllegalStateException If the VPN is already present in the registry.
     */
    public void addVpn(String newVpn) {
        Asserts.isFalse(vpnRegistry.containsKey(newVpn), IllegalStateException.class, "The vpn is already added.");
        vpnRegistry.put(newVpn, 0);
        updateLoadRef(newVpn);
    }

    /**
     * Removes a virtual page number (VPN) from the registry when a page is evicted from memory.
     *
     * @param oldVpn The VPN of the page to be removed.
     */
    public void removeVpn(String oldVpn) {
        vpnRegistry.remove(oldVpn);
    }

    /**
     * Updates the reference count for a VPN when the corresponding page is accessed.
     *
     * @param vpnAccessed The VPN of the accessed page.
     */
    public void accessVpn(String vpnAccessed) {
        updateAccessRef(vpnAccessed);
    }

    public void display() {
        TableUtil.renderTable(
            new String[]{
                "Virtual Page Number (VPN)",
                "Reference Count"
            },
            vpnRegistry.entrySet().stream()
                .map(entry -> new String[]{entry.getKey(), String.valueOf(entry.getValue())})
                .toArray(String[][]::new)
        );
    }

    /**
     * Updates the reference counts for all VPNs in the registry.
     *
     * @param vpnUsed The VPN that was recently used.
     * @throws IllegalArgumentException If the provided VPN is null.
     */
    protected void updateReference(String vpnUsed) {
        Asserts.nonNull(vpnUsed, IllegalArgumentException.class, "The vpnUsed is null");
        vpnRegistry.replaceAll((key, value) -> vpnUsed.equals(key) ? 0 : value + 1);
    }

    /**
     * Updates the reference count when a new page is loaded into memory.
     *
     * @param vpnLoaded The VPN of the newly loaded page.
     */
    public abstract void updateLoadRef(String vpnLoaded);

    /**
     * Updates the reference count when an existing page is accessed.
     *
     * @param vpnAccessed The VPN of the accessed page.
     */
    public abstract void updateAccessRef(String vpnAccessed);
}
