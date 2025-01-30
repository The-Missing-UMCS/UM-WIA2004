package com.umwia2004.solution.lab7.lab7b;

import com.umwia2004.solution.lab7.util.IntArray;

/**
 * Represents a sequence in the Banker's algorithm process.
 */
public record Sequence(String id, IntArray need, IntArray allocation, IntArray previousAvailable, IntArray updatedAvailable) {
}
