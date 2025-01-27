package com.umwia2004.solution.lab3.lab3a.helper;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;
import com.umwia2004.solution.util.LogUtil;
import com.umwia2004.solution.util.TableUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Disk {
    private static final boolean FREE = true;
    private static final boolean OCCUPIED = false;
    private static final DataSize DEFAULT_DISK_SIZE = new DataSize(12288, DataSize.Unit.BYTE);
    private static final DataSize DEFAULT_BLOCK_SIZE = new DataSize(512, DataSize.Unit.BYTE);

    @Getter
    private final DataSize totalSize;

    @Getter
    private final DataSize blockSize;

    @Getter
    private final int blockCount;

    private final ArrayList<Boolean> diskBlocks;

    private final FileRegistry fileRegistry;
    @Getter
    private long freeBlocks;
    @Getter
    private long usedBlocks;

    public Disk() {
        this(DEFAULT_DISK_SIZE, DEFAULT_BLOCK_SIZE);
    }

    public Disk(DataSize totalSize, DataSize blockSize) {
        this.totalSize = totalSize;
        this.blockSize = blockSize;
        this.blockCount = (int) Math.ceilDiv(totalSize.toBytes(), blockSize.toBytes());
        this.diskBlocks = new ArrayList<>(blockCount);
        IntStream.range(0, blockCount).forEach(i -> diskBlocks.add(FREE));
        this.freeBlocks = blockCount;
        this.usedBlocks = 0;
        this.fileRegistry = new FileRegistry();
    }

    public boolean isBlockFree(int index) {
        return diskBlocks.get(index);
    }

    public void printDiskStatus() {
        LogUtil.logInfo("Disk Status:");
        TableUtil.renderTable(
            new String[]{"Total Size", "Block Size", "Block Count", "Free Blocks", "Used Blocks"},
            new String[][]{{
                totalSize.toString(), blockSize.toString(), String.valueOf(blockCount), String.valueOf(freeBlocks), String.valueOf(usedBlocks)
            }}
        );

        System.out.println();

        LogUtil.logInfo("Disk Blocks:");
        fileRegistry.printRegistry();
        TableUtil.printRowWithIndices(diskBlocks);
    }

    public synchronized boolean allocateFile(String fileName, int startIndex, int blockCount) {
        if (fileRegistry.isFileExisted(fileName)) {
            throw new IllegalArgumentException("File already exists");
        }
        fileRegistry.addRecord(fileName, startIndex, blockCount);
        updateBlockStatus(startIndex, blockCount, OCCUPIED);
        return true;
    }

    public synchronized boolean removeFile(String fileName) {
        if (!fileRegistry.isFileExisted(fileName)) {
            throw new IllegalArgumentException("File does not exist");
        }
        var record = fileRegistry.removeRecord(fileName);
        updateBlockStatus(record.startIndex(), record.blockCount(), FREE);
        return true;
    }

    private void updateBlockStatus(int startIndex, int blockCount, boolean isFree) {
        for (int i = startIndex; i < startIndex + blockCount; i++) {
            diskBlocks.set(i, isFree);
        }
        freeBlocks += isFree ? blockCount : -blockCount;
        usedBlocks += isFree ? -blockCount : blockCount;
    }
}
