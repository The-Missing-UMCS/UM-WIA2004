package com.umwia2004.solution.lab3.lab3b;

public record DataSize(long size, Unit unit) implements Comparable<DataSize> {
    public DataSize toUnit(Unit unit) {
        return new DataSize(unit.fromBytes(this.unit.toBytes(size)), unit);
    }

    public long toBytes() {
        return unit.toBytes(size);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DataSize dataSize)) return false;
        return size == dataSize.size && unit == dataSize.unit;
    }

    public int hashCode() {
        return Long.hashCode(size) + unit.hashCode();
    }

    @Override
    public int compareTo(DataSize o) {
        return Long.compare(unit.toBytes(size), o.unit.toBytes(o.size));
    }

    @Override
    public String toString() {
        return "%d %s".formatted(size, unit);
    }

    public enum Unit {
        BYTE(1),
        KB(1024),
        MB(1024 * 1024),
        GB(1024 * 1024 * 1024);

        private final long bytes;

        Unit(long bytes) {
            this.bytes = bytes;
        }

        public long toBytes(long size) {
            return size * bytes;
        }

        public long fromBytes(long size) {
            return size / bytes;
        }
    }
}
