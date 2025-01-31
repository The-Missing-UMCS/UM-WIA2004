package com.umwia2004.solution.lab9.lab9c;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.umwia2004.solution.util.LogUtil.logGreen;
import static com.umwia2004.solution.util.LogUtil.logRed;

public class DiningPhilosophers {
    private static final int NUM_OF_PHILOSOPHERS = 5;

    private static final Fork[] FORKS = IntStream.range(0, NUM_OF_PHILOSOPHERS)
        .mapToObj(Fork::new)
        .toArray(Fork[]::new);

    public static void main(String[] args) {
        Thread[] philosophers = new Thread[NUM_OF_PHILOSOPHERS];

        for (int i = 0; i < NUM_OF_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i));
            philosophers[i].start();
        }

        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException _) {
            }
        }

        System.out.println("\nAll Philosophers have finished eating...");
    }

    enum Operation {
        PICK_UP, PUT_DOWN
    }

    interface ForkOperation extends Consumer<Fork> {
        @Override
        void accept(Fork fork);

        Operation name();
    }

    static class Fork extends Semaphore {
        private final int id;

        public Fork(int id) {
            super(1);
            this.id = id;
        }

        @Override
        public void acquire() {
            try {
                super.acquire();
            } catch (InterruptedException _) {
            }
        }

        @Override
        public void release() {
            super.release();
        }

        @Override
        public String toString() {
            return "Fork (id: %d, status: %d)".formatted(id, availablePermits());
        }
    }

    static class Philosopher implements Runnable {
        private static final Random RANDOM = new Random();
        private final int id;
        private final int leftFork;
        private final int rightFork;

        public Philosopher(int id) {
            this.id = id;
            this.leftFork = id;
            this.rightFork = (id + 1) % NUM_OF_PHILOSOPHERS;
        }

        @Override
        public void run() {
            eat();
        }

        public void eat() {
            logRed("Philosopher %d >>> starts eating".formatted(id));
            pickUpForks();

            try {
                Thread.sleep(RANDOM.nextInt(2000) + 1000); // Simulate eating time
            } catch (InterruptedException _) {
            }

            logGreen("Philosopher %d >>> finishes eating".formatted(id));
            putDownForks();
        }

        private void pickUpForks() {
            handleForks(new ForkOperation() {
                @Override
                public void accept(Fork fork) {
                    fork.acquire();
                }

                @Override
                public Operation name() {
                    return Operation.PICK_UP;
                }
            });
        }

        private void putDownForks() {
            handleForks(new ForkOperation() {
                @Override
                public void accept(Fork fork) {
                    fork.release();
                }

                @Override
                public Operation name() {
                    return Operation.PUT_DOWN;
                }
            });
        }

        private void handleForks(ForkOperation operation) {
            operation.accept(FORKS[leftFork]);
            operation.accept(FORKS[rightFork]);
            printForks(operation);
        }

        private void printForks(ForkOperation operation) {
            System.out.println();
            for (Fork fork : FORKS) {
                System.out.printf("%s [%s] Philosopher (id: %d), %s%n",
                    (operation.name() == Operation.PICK_UP ? "+" : "-").repeat(6),
                    operation.name(), id, fork);
            }
            System.out.println();
        }
    }
}