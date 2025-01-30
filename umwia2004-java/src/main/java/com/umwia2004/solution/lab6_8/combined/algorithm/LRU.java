package com.umwia2004.solution.lab6_8.combined.algorithm;

/**
 * Implements the Least Recently Used (LRU) page replacement algorithm.
 * <p>
 * In LRU, the page that has not been used for the longest time is replaced
 * when a new page needs to be loaded and memory is full. This algorithm
 * takes into account the recent access history of pages to optimize page retention.
 * </p>
 *
 * <p>
 * Key characteristics:
 * <ul>
 *     <li>Keeps track of page accesses to determine the least recently used page.</li>
 *     <li>On page access, updates the reference unless a replacement has just occurred.</li>
 *     <li>On page load, marks that a replacement has happened and updates the reference.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class extends {@link com.umwia2004.solution.lab6_8.lab6.algo.ReplacementAlgorithm} and overrides methods for updating references
 * based on recent usage patterns.
 * </p>
 *
 * @author Ng Zhi Yang
 * @version 1.0
 */
public class LRU extends ReplacementAlgorithm {
    private boolean hasJustReplace;

    @Override
    public void updateLoadRef(String vpnLoaded) {
        updateReference(vpnLoaded);
        hasJustReplace = true;
    }

    @Override
    public void updateAccessRef(String vpnAccessed) {
        if(hasJustReplace) {
            // If the vpn is new and has just replaced a new one, then all the references has been updated recently
            // As called in the updateLoadRef, hence it requires no further updating to avoid malformed reference record
            // However, removing this variable and call update Reference directly shouldn't have effect on the
            // findPageToLoad
            hasJustReplace = false;
        } else {
            updateReference(vpnAccessed);
        }
    }
}
