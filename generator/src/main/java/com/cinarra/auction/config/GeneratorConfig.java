package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.DisabledJMX;
import com.cinarra.auction.common.profiles.JMX;
import com.cinarra.auction.generator.Generator;
import com.cinarra.auction.generator.GeneratorExecutor;
import com.cinarra.auction.generator.Producer;
import com.cinarra.auction.generator.ProducerFactory;
import com.cinarra.auction.generator.jmx.ManagedProducer;
import com.cinarra.auction.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import static com.cinarra.auction.config.KafkaServiceConfig.INPUT_CHANNEL;

@Configuration
public class GeneratorConfig {

    @Autowired
    KafkaService kafkaService;

    @Bean
    @DependsOn(INPUT_CHANNEL)
    public Generator generator() {
        return new GeneratorExecutor(producerFactory());
    }

    @Bean(name = "producer")
    @JMX
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Producer managedProducer(String name) {
        return new ManagedProducer(name, kafkaService);
    }

    @Bean
    @DisabledJMX
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Producer producer(String name) {
        return new Producer(kafkaService);
    }

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory() {
            @Override
            public Producer createInstance(String name) {
                return producer(name);
            }
        };
    }

}
