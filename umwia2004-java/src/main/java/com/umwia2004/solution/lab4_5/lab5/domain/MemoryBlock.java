package com.umwia2004.solution.lab4_5.lab5.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class MemoryBlock {
    public static final boolean FREE = true;
    public static final boolean OCCUPIED = false;

    private final int blockSize;
    private boolean status;
    private Process process;

    public synchronized void release() {
        this.status = FREE;
        this.process = null;
    }

    public boolean canAllocate(Process process) {
        return isFree() && canFit(process);
    }

    public boolean canFit(Process process) {
        return blockSize > process.size();
    }

    public boolean isFree() {
        return Objects.isNull(this.process) && this.status == FREE;
    }

    public boolean isOccupied() {
        return Objects.nonNull(this.process) && this.status == OCCUPIED;
    }

    public void allocate(Process process) {
        this.status = OCCUPIED;
        this.process = process;
    }
}