package com.umwia2004.solution.lab4_5.combined.domain;

import com.umwia2004.solution.lab3.lab3a.domain.DataSize;
import com.umwia2004.solution.util.Asserts;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class MemoryBlock {
    public static final boolean FREE = true;
    public static final boolean OCCUPIED = false;

    private final DataSize blockSize;
    private boolean status;
    private Process process;

    public synchronized void allocate(Process process) {
        Asserts.isTrue(canAllocate(process), IllegalStateException.class, "Memory block cannot be allocated");
        this.status = OCCUPIED;
        this.process = process;
    }

    public synchronized void release() {
        Asserts.isTrue(isOccupied(), IllegalStateException.class, "Memory block is not occupied");
        releaseSafely();
    }
    public void releaseSafely() {
        this.status = FREE;
        this.process = null;
    }

    public boolean canAllocate(Process process) {
        return isFree() && canFit(process);
    }

    public boolean canFit(Process process) {
        return blockSize.compareTo(process.size()) > 0;
    }

    public boolean isFree() {
        return Objects.isNull(this.process) && this.status == FREE;
    }

    public boolean isOccupied() {
        return Objects.nonNull(this.process) && this.status == OCCUPIED;
    }

    public DataSize internalFragmentation() {
        return isFree() ? DataSize.ZERO : blockSize.subtract(process.size());
    }
}