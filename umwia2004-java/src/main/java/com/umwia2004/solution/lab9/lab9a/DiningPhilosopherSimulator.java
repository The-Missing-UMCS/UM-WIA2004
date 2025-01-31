package com.umwia2004.solution.lab9.lab9a;

import com.umwia2004.solution.util.Asserts;
import com.umwia2004.solution.util.TableUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.umwia2004.solution.util.LogUtil.logBlue;
import static com.umwia2004.solution.util.LogUtil.logRed;

/**
 * Simulates the Dining Philosophers problem using resource allocation and queue management.
 * Ensures that philosophers follow the eating and thinking sequence without deadlocks.
 */
public class DiningPhilosopherSimulator {
    private static final boolean AVAILABLE = false;
    private static final boolean OCCUPIED = true;

    private static final ActionValidator ACTION_VALIDATOR = new ActionValidator();

    private final int numOfPhilosophers;
    private final boolean[] chopstickStatus;
    private final Action.Type[] philosopherState;
    private final List<Action> actions;
    private Deque<Action> waitingQueue;

    /**
     * Constructs a DiningPhilosopherSimulator instance with specified number of philosophers and actions.
     *
     * @param numOfPhilosophers Number of philosophers in the simulation.
     * @param actions           List of actions to be performed in the simulation.
     */
    private DiningPhilosopherSimulator(int numOfPhilosophers, List<Action> actions) {
        this.numOfPhilosophers = numOfPhilosophers;
        this.actions = new ArrayList<>(actions);
        this.chopstickStatus = new boolean[numOfPhilosophers];
        this.philosopherState = new Action.Type[numOfPhilosophers];
        for (int i = 0; i < numOfPhilosophers; i++) {
            this.philosopherState[i] = Action.Type.THINK;
        }
        this.waitingQueue = new ArrayDeque<>();
    }

    /**
     * Factory method to create a new simulator instance.
     *
     * @param numOfPhilosophers Number of philosophers in the simulation.
     * @param actions           List of actions to be performed.
     * @return A new instance of DiningPhilosopherSimulator.
     * @throws IllegalArgumentException If the action list is empty or invalid.
     */
    public static DiningPhilosopherSimulator create(int numOfPhilosophers, List<Action> actions) {
        Asserts.isNotEmpty(actions, IllegalArgumentException.class, "Actions must not be empty");
        Asserts.isTrue(ACTION_VALIDATOR.validate(actions, numOfPhilosophers), IllegalArgumentException.class, "Invalid actions");
        return new DiningPhilosopherSimulator(numOfPhilosophers, actions);
    }

    /**
     * Executes the simulation, processing each action in sequence while managing the waiting queue.
     */
    public void runSimulation() {
        for (Action action : actions) {
            // 1. Process the waiting queue to check if any waiting philosopher can start eating.
            //    A philosopher can eat once the previous philosopher finishes eating,
            //    switches to thinking, and releases the chopsticks.
            processWaitingQueue();

            // 2. Process the current action.
            processAction(action);
            displayStatus(action);
        }
        if (!waitingQueue.isEmpty()) {
            processWaitingQueue();
        }
        processWaitingQueue();

    }

    /**
     * Processes the waiting queue to check if any philosophers can now eat.
     */
    private void processWaitingQueue() {
        Deque<Action> updatedQueue = new ArrayDeque<>();
        while (!waitingQueue.isEmpty()) {
            Action action = waitingQueue.pollFirst();
            if (areChopsticksAvailable(action.pid())) {
                logRed("Philosopher %s in the waiting queue are now eating...".formatted(action.pid()));
                handleEating(action);
                displayStatus(action);
            } else {
                updatedQueue.add(action);
            }
        }
        waitingQueue = updatedQueue;
    }

    /**
     * Processes an action by determining whether the philosopher will think or eat.
     *
     * @param action The action to process.
     */
    private void processAction(Action action) {
        if (action.type() == Action.Type.THINK) {
            handleThinking(action);
        } else {
            handleEating(action);
        }
    }

    /**
     * Handles the transition of a philosopher to the thinking state, releasing resources if applicable.
     *
     * @param action The action representing the philosopher's thinking state.
     */
    private void handleThinking(Action action) {
        if (philosopherState[action.pid()] == Action.Type.EAT) {
            updateChopstickStatus(action.pid(), AVAILABLE);
        }
        philosopherState[action.pid()] = Action.Type.THINK;
    }

    /**
     * Handles the transition of a philosopher to the eating state, acquiring resources if available.
     * If the resources are not available, the philosopher is added to the waiting queue.
     *
     * @param action The action representing the philosopher's eating state.
     */
    private void handleEating(Action action) {
        if (areChopsticksAvailable(action.pid())) {
            updateChopstickStatus(action.pid(), OCCUPIED);
            philosopherState[action.pid()] = Action.Type.EAT;
        } else {
            philosopherState[action.pid()] = Action.Type.WAIT;
            waitingQueue.add(action);
        }
    }

    /**
     * Updates the status of the chopsticks for a given philosopher.
     *
     * @param pid    The philosopher ID.
     * @param status The new status of the chopsticks.
     */
    private void updateChopstickStatus(int pid, boolean status) {
        chopstickStatus[pid] = status;
        chopstickStatus[(pid + 1) % numOfPhilosophers] = status;
    }

    /**
     * Checks whether the chopsticks for a given philosopher are available.
     *
     * @param pid The philosopher ID.
     * @return {@code true} if both chopsticks are available, {@code false} otherwise.
     */
    private boolean areChopsticksAvailable(int pid) {
        return !chopstickStatus[pid] && !chopstickStatus[(pid + 1) % numOfPhilosophers];
    }

    private void displayStatus(Action action) {
        logBlue("Current Action: %s".formatted(action));

        // 1. Display the status of each philosopher and the chopsticks.
        System.out.println("Status:");
        TableUtil.renderTable(
            new String[]{
                "Position",
                "Chopstick",
                "Philosopher"
            },
            IntStream.range(0, numOfPhilosophers)
                .mapToObj(i -> new String[]{
                    String.valueOf(i),
                    chopstickStatus[i] ? "In Use" : "Free",
                    StringUtils.capitalize(philosopherState[i].toString().toLowerCase())
                }).toArray(String[][]::new)
        );

        // 2. Display the waiting queue.
        String waitingQueueStr = waitingQueue.isEmpty()
            ? "[]"
            : waitingQueue.stream().map(Action::toString).collect(Collectors.joining(" --> "));
        System.out.printf("Queue:%n%s%n%n%n".formatted(waitingQueueStr));
    }
}