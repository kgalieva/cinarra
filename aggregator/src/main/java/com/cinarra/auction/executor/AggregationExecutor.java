package com.cinarra.auction.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AggregationExecutor implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManualAggregationExecutor.class);

    protected final ExecutorService executor;

    protected AggregationExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void stop() {
        LOGGER.debug("stopping aggregation");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    LOGGER.warn(getClass().getSimpleName() + ": ScheduledExecutorService did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() throws IOException {
        stop();
    }

    @SuppressWarnings("NullableProblems")
    protected static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private static final AtomicInteger FACTORY_ID = new AtomicInteger();

        public NamedThreadFactory(String name) {
            final SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = "aggregation-" + name + '-' + FACTORY_ID.incrementAndGet() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setDaemon(true);
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * Only for monitoring purposes
     */
    public ExecutorService getExecutor() {
        return executor;
    }
}
