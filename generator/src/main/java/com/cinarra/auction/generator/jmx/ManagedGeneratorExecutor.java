package com.cinarra.auction.generator.jmx;

import com.cinarra.auction.common.profiles.JMX;
import com.cinarra.auction.generator.GeneratorExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@JMX
@Component
@ManagedResource(objectName="com.cinarra.auction.generator.jmx:name=ManagedGeneratorExecutor",
        description="Micro transactions load generator")
public class ManagedGeneratorExecutor {

    @Autowired
    GeneratorExecutor generatorExecutor;

    @ManagedMetric(description="Number of threads producing transactions")
    public int getTotalProducers() {
        return generatorExecutor.getTotalProducers();
    }

    @ManagedMetric(description="Сurrent number of threads in the pool")
    public int getActiveThreads() {
        return generatorExecutor.getPool().getPoolSize();
    }

    @ManagedMetric(description="Approximate number of threads that are actively executing tasks")
    public int getActiveProducers() {
        return generatorExecutor.getPool().getActiveCount();
    }

    @ManagedMetric(description="The producer queue")
    public int getQueuedTasks() {
        return generatorExecutor.getPool().getQueue().size();
    }
}
