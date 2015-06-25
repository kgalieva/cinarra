package com.cinarra.auction.executor;

import com.cinarra.auction.aggregator.Aggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledAggregationExecutor extends AggregationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledAggregationExecutor.class);

    @Autowired
    protected Aggregator aggregator;
    protected long period;

    public ScheduledAggregationExecutor(long period, String name) {
        super(new ScheduledThreadPoolExecutor(1, new NamedThreadFactory(name)));
        this.period = period;
    }

    public void start() {
        LOGGER.debug("starting aggregation");
        ((ScheduledExecutorService) executor).scheduleAtFixedRate(() -> {
            try {
                aggregator.aggregate(LocalDate.now());
            } catch (RuntimeException ex) {
                LOGGER.error("RuntimeException thrown from {}#aggregation. Exception was suppressed.", ScheduledAggregationExecutor.this.getClass().getSimpleName(), ex);
            }
        }, period, period, TimeUnit.SECONDS);
    }
}
