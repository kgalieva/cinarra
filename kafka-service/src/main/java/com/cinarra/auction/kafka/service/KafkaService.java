package com.cinarra.auction.kafka.service;

import com.cinarra.auction.domain.MicroTransaction;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static com.cinarra.auction.config.KafkaServiceConfig.INPUT_CHANNEL;

@MessagingGateway
@DependsOn(INPUT_CHANNEL)
public interface KafkaService {
    @Gateway(requestChannel = INPUT_CHANNEL + ".input")
    void send(MicroTransaction transaction);
}