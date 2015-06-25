package com.cinarra.auction.executor.jmx;

import com.cinarra.auction.common.profiles.JMX;
import com.cinarra.auction.executor.ScheduledAggregationExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@JMX
@Component
@ManagedResource(objectName = "com.cinarra.auction.aggregator.jmx:name=ScheduledAggregationExecutor",
        description = "Scheduled aggregation executor for current date running with given launch interval")
public class ManagedScheduledAggregationExecutor {

    private final ScheduledAggregationExecutor aggregationExecutor;
    private final ThreadPoolExecutor pool;

    @Autowired
    public ManagedScheduledAggregationExecutor(ScheduledAggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
        pool = (ThreadPoolExecutor) aggregationExecutor.getExecutor();
    }

    @ManagedMetric(description = "Сurrent number of threads in the pool")
    public int getActiveThreads() {
        return pool.getPoolSize();
    }

    @ManagedMetric(description = "Approximate number of threads that are actively executing tasks")
    public int getActiveProducers() {
        return pool.getActiveCount();
    }

    @ManagedMetric(description = "The aggregations queue")
    public int getQueuedTasks() {
        return pool.getQueue().size();
    }

}
