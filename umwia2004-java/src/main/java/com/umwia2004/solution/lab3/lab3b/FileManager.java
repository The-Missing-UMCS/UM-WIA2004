package com.umwia2004.solution.lab3.lab3b;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileManager {
    private final Disk disk;

    public boolean allocate(File file) {
        long blockCount = Math.ceilDiv(file.size(), disk.getBlockSize());
        if (disk.getFreeBlocks() < blockCount) {
            throw new IllegalStateException("Not enough free blocks");
        }

        for (int startIndex = 0; startIndex < disk.getBlockCount() - blockCount; startIndex++) {
            boolean isFree = isAllBlocksFree(startIndex, blockCount);
            if (isFree) {
                return disk.allocateFile(file.name(), startIndex, (int) blockCount);
            }
        }

        throw new IllegalStateException("No enough free continuous blocks");
    }

    private boolean isAllBlocksFree(int startIndex, long blockCount) {
        for (int i = 0; i < blockCount; i++) {
            if (!disk.isBlockFree(startIndex + i)) {
                return false;
            }
        }
        return true;
    }
}
