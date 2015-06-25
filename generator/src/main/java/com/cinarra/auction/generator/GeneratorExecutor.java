package com.cinarra.auction.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class GeneratorExecutor implements Generator {

    @Value("${generator.totalProducers}")
    private int totalProducers;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ExecutorService executorService;
    private final ProducerFactory producerFactory;
    private final String PRODUCER_NAME_PREFIX = "producer-thread-";
    private final Logger LOGGER = LoggerFactory.getLogger(GeneratorExecutor.class);

    public GeneratorExecutor(ProducerFactory producerFactory) {
        this.producerFactory = producerFactory;
    }

    @Override
    public void start() {
        LOGGER.debug("Starting generator");
        executorService = Executors.newFixedThreadPool(totalProducers);
        if (running.compareAndSet(false, true)) {
            for (int x = 0; x < totalProducers; x++) {
                Producer producer = producerFactory.createInstance(PRODUCER_NAME_PREFIX + x);
                executorService.submit(producer);
            }
        }
    }

    @Override
    public void stop() {
        if (running.compareAndSet(true, false)) {
            executorService.shutdown();
            LOGGER.debug("Generator is stopped");
        }
    }

    public int getTotalProducers() {
        return totalProducers;
    }

    public ThreadPoolExecutor getPool() {
        return (ThreadPoolExecutor)executorService;
    }
}