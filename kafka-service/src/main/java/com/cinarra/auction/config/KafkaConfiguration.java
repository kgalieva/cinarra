package com.cinarra.auction.config;

public interface KafkaConfiguration {

    public String getTopic();

    public String getBrokerAddress();

    public String getZookeeperAddress();
}
