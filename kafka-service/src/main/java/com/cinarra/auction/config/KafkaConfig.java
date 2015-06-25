package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.NotEmbedded;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@NotEmbedded
@PropertySource("classpath:kafka.properties")
public class KafkaConfig implements KafkaConfiguration {

    @Value("${kafka.topic}")
    private String topic;

    @Value("${kafka.address}")
    private String brokerAddress;

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    public String getTopic() {
        return topic;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }
}