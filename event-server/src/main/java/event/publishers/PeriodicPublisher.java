package event.publishers;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Created by aowssibrahim on 2016-11-04.
 */
public class PeriodicPublisher<T> extends SubmissionPublisher<T> {

    final ScheduledFuture<?> periodicTask;
    final ScheduledExecutorService scheduler;

    PeriodicPublisher(Executor executor, int maxBufferCapacity,
                      Supplier<? extends T> supplier,
                      long period, TimeUnit unit) {
        super(executor, maxBufferCapacity);
        scheduler = new ScheduledThreadPoolExecutor(1);
        periodicTask = scheduler.scheduleAtFixedRate(() -> submit(supplier.get()), 0, period, unit);
    }

    public void close() {
        periodicTask.cancel(false);
        scheduler.shutdown();
        super.close();
    }

}
