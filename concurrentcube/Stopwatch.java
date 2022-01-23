package concurrentcube;

import java.time.Duration;
import java.time.Instant;

public class Stopwatch {

    private Instant start;

    public void start() {
        start = Instant.now();
    }

    public Duration stop() {
        Duration duration = Duration.between(start, Instant.now());
        start = null;
        return duration;
    }

    public void runWithStopwatch(Runnable runnable) {
        start();
        runnable.run();
        Duration duration = stop();
        System.out.println("It took " + duration);
    }
}
