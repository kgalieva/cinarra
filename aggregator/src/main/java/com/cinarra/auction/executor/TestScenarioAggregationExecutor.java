package com.cinarra.auction.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScenarioAggregationExecutor extends ScheduledAggregationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestScenarioAggregationExecutor.class);

    public TestScenarioAggregationExecutor(long period, String name) {
        super(period, name);
    }

    public void start(LocalDate startDate, LocalDate finishDate) {
        if (finishDate.isBefore(startDate)) throw new IllegalArgumentException("invalid time period");
        LOGGER.debug("starting aggregation");
        while (!startDate.isAfter(finishDate)) {
            LocalDate currentDate = startDate;
            ((ScheduledExecutorService) executor).schedule(() -> {
                try {
                    aggregator.aggregate(currentDate);
                } catch (RuntimeException ex) {
                    LOGGER.error("RuntimeException thrown from {}#aggregation. Exception was suppressed.", TestScenarioAggregationExecutor.this.getClass().getSimpleName(), ex);
                }
            }, period, TimeUnit.SECONDS);
            startDate = startDate.plusDays(1);
        }
    }
}
