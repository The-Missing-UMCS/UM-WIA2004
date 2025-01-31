package com.umwia2004.solution.lab9.lab9b;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import static com.umwia2004.solution.util.LogUtil.*;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;

    private static final Semaphore ROOM = new Semaphore(4);

    private static final Semaphore[] FORKS = IntStream.range(0, NUM_PHILOSOPHERS)
        .mapToObj(_ -> new Semaphore(1))
        .toArray(Semaphore[]::new);

    public static void main(String[] args) {
        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i));
            philosophers[i].start();
        }

        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException _) {
            }
        }

        System.out.println();
        System.out.println("All Philosophers have finished eating...");
    }

    @RequiredArgsConstructor
    static class Philosopher implements Runnable {
        private final int num;

        @Override
        public void run() {
            try {
                // 1. Get permission to enter the room
                ROOM.acquire();
                logBlue("[WAIT] Philosopher %d enters the room, waiting to eat".formatted(num));

                // 2. Get the right and left forks and start eating
                Semaphore rightFork = FORKS[num];
                Semaphore leftFork = FORKS[(num + 1) % NUM_PHILOSOPHERS];

                rightFork.acquire();
                leftFork.acquire();

                logRed("[EAT] Philosopher %d gets fork %d and %d and starts eating".formatted(num, num, (num + 1) % NUM_PHILOSOPHERS));

                // 3. Finish eating and start thinking
                Thread.sleep(2000);

                logGreen("[THINK] Philosopher %d finishes eating and put down forks %d and %d".formatted(
                    num, num, (num + 1) % NUM_PHILOSOPHERS));

                // Release the forks
                leftFork.release();
                rightFork.release();
                ROOM.release();

            } catch (InterruptedException _) {
            }
        }
    }
}
