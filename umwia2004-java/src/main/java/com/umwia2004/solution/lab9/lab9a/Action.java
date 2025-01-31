package com.umwia2004.solution.lab9.lab9a;

public record Action(int pid, Type type) {
    @Override
    public String toString() {
        return "Action(pid=%s, type=%s)".formatted(pid, type);
    }

    enum Type {
        THINK, EAT, WAIT
    }
}