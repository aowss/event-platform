package event.publishers;

import event.model.SimpleEvent;
import event.model.SimpleLocationEvent;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aowssibrahim on 2016-11-04.
 */
public class FakeSimpleLocationEventPublisher extends PeriodicPublisher<SimpleLocationEvent> {

    private static final int maxBufferCapacity = 10; // the maximum capacity for each subscriber's buffer
    private static final int corePoolSize = 5; // thread pool size
    private static final int period = 10; // period between event generation

    private static final String[] types = new String[] { "Flight"};
    private static final String[] contexts = new String[] { "flights:BA:123:1:2016-11-15", "flights:BA:123:2:2016-11-15", "flights:BA:123:1:2016-11-16", "flights:BA:1:1:2016-11-15"};

    private static final AtomicInteger index = new AtomicInteger();
    private static final Random random = new Random();

    public FakeSimpleLocationEventPublisher() {
        super(new ScheduledThreadPoolExecutor(corePoolSize), maxBufferCapacity, () -> {
            int i = index.getAndIncrement();
            String type = types[i % types.length];
            String context = contexts[i % contexts.length];
            return new SimpleLocationEvent(type, context, type + " message for " + context, random.nextDouble(), random.nextDouble(), random.nextDouble());
        }, period, TimeUnit.SECONDS);
    }

}
