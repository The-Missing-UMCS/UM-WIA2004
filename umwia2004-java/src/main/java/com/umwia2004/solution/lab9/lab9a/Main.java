package com.umwia2004.solution.lab9.lab9a;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Action> actions = Arrays.asList(
            new Action(0, Action.Type.EAT),
            new Action(1, Action.Type.EAT),
            new Action(2, Action.Type.EAT),
            new Action(0, Action.Type.THINK),
            new Action(3, Action.Type.EAT),
            new Action(2, Action.Type.THINK),
            new Action(4, Action.Type.EAT)
        );

        DiningPhilosopherSimulator
            .create(5, actions)
            .runSimulation();
    }
}