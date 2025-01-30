package com.umwia2004.solution.lab6_8.combined.algorithm;

/**
 * Implements the First-In-First-Out (FIFO) page replacement algorithm.
 * <p>
 * In FIFO, the oldest loaded page is replaced when a new page needs to be loaded
 * and memory is full. This algorithm does not consider the frequency of access
 * or recent usage of pages.
 * </p>
 *
 * <p>
 * Key characteristics:
 * <ul>
 *     <li>Maintains a queue-like structure where pages are removed in the order they were added.</li>
 *     <li>Does not update reference tracking upon page access.</li>
 *     <li>Only updates the reference when a page is loaded.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class extends {@link com.umwia2004.solution.lab6_8.lab8.algo.ReplacementAlgorithm} and overrides methods for updating references.
 * </p>
 *
 * @author fyiernzy
 * @version 1.0
 */
public class FIFO extends ReplacementAlgorithm {
    @Override
    public void updateLoadRef(String vpnLoaded) {
        updateReference(vpnLoaded);
    }

    @Override
    public void updateAccessRef(String vpnAccessed) {
        // Do nothing as the accessing a page has no effect on the reference
        // The only factor that affects the reference is when the new page has just been loaded.
    }
}
