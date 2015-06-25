package com.cinarra.auction.generator.jmx;

import com.cinarra.auction.generator.Producer;
import com.cinarra.auction.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.ManagedMetric;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.naming.SelfNaming;

import javax.annotation.PostConstruct;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

@ManagedResource
public class ManagedProducer extends Producer implements SelfNaming {

    private final String name;
    private long startTime;

    @Autowired
    MBeanExporter exporter;

    @PostConstruct
    private void init() throws Exception {
        exporter.registerManagedResource(this);
    }

    public ManagedProducer(String name, KafkaService kafkaService) {
        super(kafkaService);
        this.name = name;
    }

    public void run() {
        startTime = System.currentTimeMillis();
        super.run();
    }

    @ManagedMetric(description="Number of messages sent")
    public long getMessagesSent() {
        return messagesSent;
    }

    @ManagedMetric(description="Producer thread state")
    public Thread.State getState(){
        return Thread.currentThread().getState();
    }

    @ManagedMetric(description="Start time")
    public long getStartTime() {
        return startTime;
    }

    @ManagedMetric(description = "Mean sending time")
    public long getMeanSendingTime(){
        if (messagesSent == 0) return 0;
        return (System.currentTimeMillis() - startTime)/messagesSent;
    }

    @Override
    public ObjectName getObjectName() throws MalformedObjectNameException {
        return new ObjectName("com.cinarra.auction.generator.jmx", "ManagedProducer", name);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
