package com.umwia2004.solution.lab3.lab3b;

import com.umwia2004.solution.util.LogUtil;
import com.umwia2004.solution.util.TableUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Disk {
    private static final boolean FREE = true;
    private static final boolean OCCUPIED = false;

    @Getter
    private final int totalSize = 12288;

    @Getter
    private final int blockSize = 512;

    @Getter
    private final int blockCount = 24;

    private final ArrayList<Boolean> diskBlocks;

    private final FileRegistry fileRegistry;

    @Getter
    private long freeBlocks;

    public Disk() {
        this.diskBlocks = new ArrayList<>(blockCount);
        IntStream.range(0, blockCount).forEach(i -> diskBlocks.add(FREE));
        this.freeBlocks = blockCount;
        this.fileRegistry = new FileRegistry();
    }

    public boolean isBlockFree(int index) {
        return diskBlocks.get(index);
    }

    public void printDiskStatus() {
        LogUtil.logInfo("Disk Status:");
        TableUtil.renderTable(
            new String[]{"Total Size", "Block Size", "Block Count", "Free Blocks"},
            new String[][]{{String.valueOf(totalSize), String.valueOf(blockSize), String.valueOf(blockCount), String.valueOf(freeBlocks)}}
        );

        System.out.println();

        LogUtil.logInfo("Disk Blocks:");
        fileRegistry.printRegistry();
        TableUtil.printRowWithIndices(diskBlocks);
    }

    public boolean allocateFile(String fileName, int startIndex, int blockCount) {
        if (fileRegistry.isFileExisted(fileName)) {
            throw new IllegalArgumentException("File already exists");
        }
        fileRegistry.addRecord(fileName, startIndex, blockCount);
        updateBlockStatus(startIndex, blockCount, OCCUPIED);
        return true;
    }

    private void updateBlockStatus(int startIndex, int blockCount, boolean isFree) {
        for (int i = startIndex; i < startIndex + blockCount; i++) {
            diskBlocks.set(i, isFree);
        }
        freeBlocks += isFree ? blockCount : -blockCount;
    }
}
