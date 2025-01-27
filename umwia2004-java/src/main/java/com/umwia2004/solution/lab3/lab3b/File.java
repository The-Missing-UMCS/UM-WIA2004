package com.umwia2004.solution.lab3.lab3b;

import java.util.Objects;

public record File(int size, String name) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File file)) return false;
        return size == file.size && Objects.equals(name, file.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, name);
    }
}
