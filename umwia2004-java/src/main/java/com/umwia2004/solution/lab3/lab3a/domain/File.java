package com.umwia2004.solution.lab3.lab3a.domain;

import java.util.Objects;

public record File(DataSize size, String name) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File string)) return false;
        return size == string.size && (Objects.equals(name, string.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, name);
    }
}
