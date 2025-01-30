package com.umwia2004.solution.lab6_8.lab6.algo;

import com.umwia2004.solution.util.TableUtil;

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
            .orElseThrow(() -> new IllegalStateException("No pages to replace"));
    }

    /**
     * Loads a new VPN into the registry and updates its reference count.
     *
     * @param newVpn The virtual page number to load.
     */
    public void loadVpn(String newVpn) {
        vpnRegistry.put(newVpn, 0);
        updateLoadRef(newVpn);
    }

    /**
     * Updates the reference count for a VPN when it is accessed.
     *
     * @param vpn The virtual page number that was accessed.
     */
    public void accessVpn(String vpn) {
        updateAccessRef(vpn);
    }

    /**
     * Removes a VPN from the registry.
     *
     * @param oldVpn The virtual page number to remove.
     */
    public void removeVpn(String oldVpn) {
        vpnRegistry.remove(oldVpn);
    }

    public void displayStatus() {
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
     * Updates the reference count for a specific VPN and increments the counts for all other VPNs.
     *
     * @param vpn The virtual page number to update.
     */
    protected void updateRef(String vpn) {
        vpnRegistry.replaceAll((key, value) -> vpn.equals(key) ? 0 : value + 1);
    }

    /**
     * Updates reference counts when a VPN is accessed.
     *
     * @param vpn The virtual page number that was accessed.
     */
    protected abstract void updateAccessRef(String vpn);

    /**
     * Updates reference counts when a new VPN is loaded.
     *
     * @param vpn The virtual page number that was loaded.
     */
    protected abstract void updateLoadRef(String vpn);
}
