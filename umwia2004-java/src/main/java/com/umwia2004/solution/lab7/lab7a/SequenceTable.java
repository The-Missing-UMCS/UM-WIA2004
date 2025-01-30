package com.umwia2004.solution.lab7.lab7a;

import com.umwia2004.solution.util.Storage;
import com.umwia2004.solution.util.TableUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores and displays sequences of the Banker's algorithm execution.
 * This class maintains a list of sequences and provides methods
 * to add and display them in a tabular format.
 */
public class SequenceTable implements Storage<Sequence> {
    private final List<Sequence> sequences = new LinkedList<>();

    /**
     * Adds a sequence to the storage.
     *
     * @param sequence The sequence to be added.
     * @return true if the sequence was added successfully.
     */
    @Override
    public boolean add(Sequence sequence) {
        sequences.add(sequence);
        return true;
    }

    /**
     * Displays the stored sequences in a formatted table.
     */
    @Override
    public void display() {
        TableUtil.renderTable(
            new String[]{
                "ID",
                "Need",
                "Previous Available",
                "Allocation",
                "Updated Available"
            },
            sequences.stream().map(sequence -> new String[]{
                sequence.id(),
                sequence.need().toString(),
                sequence.previousAvailable().toString(),
                sequence.allocation().toString(),
                sequence.updatedAvailable().toString()
            }).toArray(String[][]::new)
        );
    }
}
