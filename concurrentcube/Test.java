import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import concurrentcube.Cube;
import concurrentcube.Stopwatch;

import org.junit.jupiter.api.Test;
import static java.time.Duration.ofMinutes;

public class Test {
    /**
     * Checks if the count of beforeShowing() and afterShowing() executions is correct
     * @return has the program passed the test?
     */
    @Test
    public static boolean SimpleShowTest() {
        int iterations = 100;

        var counter = new AtomicInteger(0);

        Cube cube = new Cube(1000,
                (x, y) -> { counter.incrementAndGet(); },
                (x, y) -> { counter.incrementAndGet(); },
                counter::incrementAndGet,
                counter::incrementAndGet
        );

        Runnable task = () -> { 
            for (int i = 0; i < iterations; i++) {
                try { cube.show(); }
                catch (InterruptedException e) { }
            }
        };

        int numOfThreads = 50;

        Thread[] threads = new Thread[numOfThreads];
        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new Thread(task);
        }
        for (Thread t : threads) {
            t.start();
        }
        try {
            for (Thread t : threads) {
                t.join();
            }
        }
        catch (InterruptedException e) { }

        assertEquals(counter.get(), 2 * iterations * numOfThreads);
    }

    /**
     * Checks if the count of beforeRotation() and afterRotation() executions is correct
     * @return has the program passed the test?
     */
    @Test
    public static boolean SimpleRotateTest() {
        int iterations = 10000;

        var counter = new AtomicInteger(0);

        int size = 1000;

        Cube cube = new Cube(size,
                (x, y) -> { counter.incrementAndGet(); },
                (x, y) -> { counter.incrementAndGet(); },
                counter::incrementAndGet,
                counter::incrementAndGet
        );

        Runnable task1 = () -> { 
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 0); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(5, size - 1); }
                catch (InterruptedException e) { }
            }
        };

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {

        }

        assertEquals(counter.get(), 4 * iterations);
    }

    /**
     * Checks if the concurrent execution of rotations is actually faster than the sequential one
     * @return has the program passed the test?
     */
    public static boolean SpeedRotationTest() {
        int iterations = 10000;

        int size = 1000;

        Cube cube = new Cube(size,
                (x, y) -> { },
                (x, y) -> { },
                () -> { },
                () -> { }
        );

        Runnable task0 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 0); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task1 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 1); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 2); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task3 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 3); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task4 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, size - 1); }
                catch (InterruptedException e) { }
            }
        };

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        task0.run();
        task1.run();
        task2.run();
        task3.run();
        task4.run();
        Duration durationSequential = stopwatch.stop();

        Thread[] threads = new Thread[] 
            { new Thread(task0), new Thread(task1), new Thread(task2), new Thread(task3), new Thread(task4)};
        stopwatch.start();
        for (Thread t : threads) t.start();
        try {
            for (Thread t : threads) t.join();
        }
        catch (InterruptedException e) { }
        Duration durationConcurrent = stopwatch.stop();

        assertTrue(durationSequential.compareTo(durationConcurrent) >= 0);

    }

    /**
     * Checks if rotations efficiently use concurrency
     * @return has the program passed the test?
     */
    public static boolean ConcurrentRotationTest() {
        int iterations = 100;
        int size = 1000;
        AtomicInteger counterEntries = new AtomicInteger(0);
        AtomicInteger counterExits = new AtomicInteger(0);
        CyclicBarrier barrierEntry = new CyclicBarrier(5, () -> { counterEntries.incrementAndGet(); });
        CyclicBarrier barrierExit = new CyclicBarrier(5, () -> { counterExits.incrementAndGet(); });
        Cube cube = new Cube(size, 
                             (x,y) -> { try { barrierEntry.await(); } catch (Exception e) { } },
                             (x,y) -> { try { barrierExit.await(); } catch (Exception e) { } },
                             () -> { },
                             () -> { });

        Runnable task0 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 0); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task1 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 1); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task2 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 2); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task3 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, 3); }
                catch (InterruptedException e) { }
            }
        };

        Runnable task4 = () -> {
            for (int i = 0; i < iterations; i++) {
                try { cube.rotate(0, size - 1); }
                catch (InterruptedException e) { }
            }
        };

        Thread[] threads = new Thread[] 
            { new Thread(task0), new Thread(task1), new Thread(task2), new Thread(task3), new Thread(task4)};
        for (Thread t : threads) t.start();
        try {
            for (Thread t : threads) {
                t.join();
            }
        }
        catch (InterruptedException e) { }

        assertEquals(counterEntries.get(), iterations);
        assertEquals(counterExits.get(), iterations);
    }

    /**
     * Checks if program doesn't fail when a thread doing computation is interrupted
     * @return
     */
    public static boolean InterruptTest() {
        assertTimeout(ofMinutes(2),() -> { 
            var isDone = new Object() { volatile boolean value = false; };

            int size = 100;
            int iterations = 100;

            Cube cube = new Cube(size,
                    (x, y) -> { },
                    (x, y) -> { isDone.value = true; try { Thread.sleep(100); } catch (InterruptedException e) { } },
                    () -> { },
                    () -> { }
            );

            Runnable task1 = () -> { 
                System.out.println(Thread.currentThread().getName());
                for (int i = 0; i < iterations; i++) {
                    try { cube.rotate(0, 0); }
                    catch (InterruptedException e) { }
                }
            };

            Runnable task2 = () -> {
                try { System.out.println(Thread.currentThread().getName()); cube.rotate(1, 0); } 
                catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                }
            };

            Thread t1 = new Thread(task1);
            Thread t2 = new Thread(task2);
            Thread t3 = new Thread(task1);

            t1.start();
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) { }
            t2.start();

            while (!isDone.value) { }
            t2.interrupt();
            t3.start();

            try {
                t1.join();
                t2.join();
                t3.join();
            }
            catch (InterruptedException e) { 
                System.out.println("Thread interrupted on join!"); // this shouldn't happen
            }
        }
        );
    }
}
