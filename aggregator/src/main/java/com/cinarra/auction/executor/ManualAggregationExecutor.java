package com.cinarra.auction.executor;

import com.cinarra.auction.aggregator.Aggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ManualAggregationExecutor extends AggregationExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualAggregationExecutor.class);

    @Autowired
    private Aggregator aggregator;

    public ManualAggregationExecutor() {
        super(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new NamedThreadFactory("manual-aggregator")));
    }

    public void aggregateFrom(LocalDate fromDate) {
        LocalDate today = LocalDate.now();
        if(fromDate.isBefore(today)) {
            start(fromDate, today);
        }
    }

    public void start(LocalDate date) {
        start(date, date);
    }

    public void start(LocalDate startDate, LocalDate finishDate) {
        if (finishDate.isBefore(startDate)) throw new IllegalArgumentException("invalid time period");
        LOGGER.debug("starting aggregation");
        while (!startDate.isAfter(finishDate)) {
            LocalDate currentDate = startDate;
            executor.submit(() -> {
                try {
                    aggregator.aggregate(currentDate);
                } catch (RuntimeException ex) {
                    LOGGER.error("RuntimeException thrown from {}#aggregation. Exception was suppressed.", ManualAggregationExecutor.this.getClass().getSimpleName(), ex);
                }
            });
            startDate = startDate.plusDays(1);
        }
    }

}
