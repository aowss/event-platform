package event.publishers;

import event.model.SimpleEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Created by aowssibrahim on 2016-11-04.
 */
public class FakeSimpleEventPublisher extends PeriodicPublisher<SimpleEvent> {

    private static final int maxBufferCapacity = 10; // the maximum capacity for each subscriber's buffer
    private static final int corePoolSize = 5; // thread pool size
    private static final int period = 10; // period between event generation

    private static final String[] types = new String[] { "Departure", "Arrival", "Alert"};
    private static final String[] contexts = new String[] { "airports:UK:London:LHR", "airports:UK:London:GTW", "airports:UK:London:LTN", "airports:France:Paris:CDG"};

    private static final AtomicInteger index = new AtomicInteger();

    public FakeSimpleEventPublisher() {
        super(new ScheduledThreadPoolExecutor(corePoolSize), maxBufferCapacity, () -> {
            int i = index.getAndIncrement();
            String type = types[i % types.length];
            String context = contexts[i % contexts.length];
            return new SimpleEvent(type, context, type + " message for " + context);
        }, period, TimeUnit.SECONDS);
    }

}
