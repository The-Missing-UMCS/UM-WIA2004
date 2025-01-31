package com.umwia2004.solution.lab9.lab9a;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validates actions for the Dining Philosophers problem.
 * Ensures that actions adhere to the rules of alternating between THINK and EAT.
 */
public class ActionValidator {

    /**
     * Checks whether the action type is valid.
     *
     * @param type The action type.
     * @return {@code true} if the type is valid, {@code false} otherwise.
     */
    private static boolean isValidActionType(Action.Type type) {
        return type == Action.Type.THINK || type == Action.Type.EAT;
    }

    /**
     * Validates a list of actions based on philosopher count and sequence correctness.
     *
     * @param actions           List of actions to validate.
     * @param numOfPhilosophers Number of philosophers in the simulation.
     * @return {@code true} if all actions are valid, {@code false} otherwise.
     */
    public boolean validate(List<Action> actions, int numOfPhilosophers) {
        return areActionsValid(actions, numOfPhilosophers) && isValidActionSequence(actions);
    }

    /**
     * Checks whether all actions are within valid philosopher IDs and have valid types.
     *
     * @param actions           List of actions.
     * @param numOfPhilosophers Number of philosophers.
     * @return {@code true} if all actions are valid, {@code false} otherwise.
     */
    private boolean areActionsValid(List<Action> actions, int numOfPhilosophers) {
        return actions.stream().allMatch(action -> isValidAction(action, numOfPhilosophers));
    }

    /**
     * Checks if an individual action is valid based on its philosopher ID and type.
     *
     * @param action            The action to validate.
     * @param numOfPhilosophers The total number of philosophers.
     * @return {@code true} if the action is valid, {@code false} otherwise.
     */
    private boolean isValidAction(Action action, int numOfPhilosophers) {
        return action.pid() >= 0 && action.pid() < numOfPhilosophers && isValidActionType(action.type());
    }

    /**
     * Validates that a sequence of actions follows the alternating THINK and EAT pattern.
     *
     * @param actions List of actions.
     * @return {@code true} if the sequence is valid, {@code false} otherwise.
     */
    private boolean isValidActionSequence(List<Action> actions) {
        Map<Integer, Action.Type> philosopherLastAction = new HashMap<>();

        for (Action action : actions) {
            int philosopherId = action.pid();
            Action.Type currentAction = action.type();

            // If the philosopher already has an action recorded, it must alternate between THINK and EAT.
            if (philosopherLastAction.containsKey(philosopherId)) {
                if (philosopherLastAction.get(philosopherId) == currentAction) {
                    return false; // Repeated action type detected.
                }
            }
            philosopherLastAction.put(philosopherId, currentAction);
        }
        return true;
    }
}