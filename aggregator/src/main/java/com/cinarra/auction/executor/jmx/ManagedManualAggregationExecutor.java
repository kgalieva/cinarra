package com.cinarra.auction.executor.jmx;

import com.cinarra.auction.common.profiles.JMX;
import com.cinarra.auction.executor.ManualAggregationExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadPoolExecutor;

@JMX
@Component
@ManagedResource(objectName = "com.cinarra.auction.aggregator.jmx:name=ManualAggregationExecutor",
        description = "Manual aggregation executor for particular date or date period")
public class ManagedManualAggregationExecutor {

    private final ManualAggregationExecutor aggregationExecutor;
    private final ThreadPoolExecutor pool;

    @Autowired
    public ManagedManualAggregationExecutor(ManualAggregationExecutor aggregationExecutor) {
        this.aggregationExecutor = aggregationExecutor;
        pool = (ThreadPoolExecutor) aggregationExecutor.getExecutor();
    }

    @ManagedOperation(description = "Start aggregation for particular date")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "date", description = "date")})
    public void start(String date) {
        aggregationExecutor.start(LocalDate.parse(date));
    }

    @ManagedOperation(description = "Start aggregation for date period")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "from date inclusive", description = "date"),
            @ManagedOperationParameter(name = "to date inclusive", description = "date")})
    public void start(String startDate, String finishDate) {
        aggregationExecutor.start(LocalDate.parse(startDate), LocalDate.parse(finishDate));
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
