package com.cinarra.auction.config;

import com.cinarra.auction.cassandra.service.CassandraService;
import com.cinarra.auction.domain.MicroTransaction;
import com.cinarra.auction.kafka.serializer.MicroTransactionSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.SourcePollingChannelAdapterSpec;
import org.springframework.integration.dsl.kafka.Kafka;
import org.springframework.integration.dsl.kafka.KafkaHighLevelConsumerMessageSourceSpec;
import org.springframework.integration.dsl.kafka.KafkaProducerMessageHandlerSpec;
import org.springframework.integration.dsl.support.Consumer;
import org.springframework.integration.kafka.support.ZookeeperConnect;

import java.util.List;
import java.util.Map;

@Configuration
public class KafkaServiceConfig {

    public static final String INPUT_CHANNEL = "transactions";

    @Configuration
    @PropertySource("classpath:kafkaProducer.properties")
    public static class ProducerConfiguration {

        @Autowired
        private KafkaConfiguration kafkaConfig;
        private final MicroTransactionSerializer microTransactionSerializer = new MicroTransactionSerializer(null);
        private final Logger LOGGER = LoggerFactory.getLogger(ProducerConfiguration.class);

        @Value("${kafka.producer.batchNumMessages}")
        private int batchNumMessages;
        @Value("${queue.buffering.max.ms}")
        private String queueBufferingMaxMs;

        @Bean(name = INPUT_CHANNEL)
        IntegrationFlow producer() {

            LOGGER.info("starting producer flow..");

            return flowDefinition -> {
                Consumer<KafkaProducerMessageHandlerSpec.ProducerMetadataSpec> producerMetadataSpecConsumer =
                        (KafkaProducerMessageHandlerSpec.ProducerMetadataSpec metadata) ->
                                metadata.async(true)
                                        .batchNumMessages(batchNumMessages)
                                        .valueClassType(MicroTransaction.class)
                                        .<MicroTransaction>valueEncoder(microTransactionSerializer);

                KafkaProducerMessageHandlerSpec messageHandlerSpec =
                        Kafka.outboundChannelAdapter(props -> props.put("queue.buffering.max.ms", queueBufferingMaxMs))
                                .messageKey(m -> m.getHeaders().get(IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER))
                                .addProducer(kafkaConfig.getTopic(), kafkaConfig.getBrokerAddress(), producerMetadataSpecConsumer);
                flowDefinition
                        .handle(messageHandlerSpec);
            };
        }
    }

    @Configuration
    @PropertySource("classpath:kafkaConsumer.properties")
    public static class ConsumerConfiguration {

        @Autowired
        private KafkaConfiguration kafkaConfig;
        @Autowired
        private CassandraService cassandraService;
        private final MicroTransactionSerializer microTransactionSerializer = new MicroTransactionSerializer(null);
        private final Logger LOGGER = LoggerFactory.getLogger(ConsumerConfiguration.class);


        @Value("${auto.offset.reset}")
        private String offsetReset;
        @Value("${auto.commit.interval.ms}")
        private String commitIntervalMs;
        @Value("${kafka.consumer.groupId}")
        private String groupId;
        @Value("${kafka.consumer.timeout.ms}")
        private int timeoutMs;
        @Value("${kafka.consumer.poller.fixedDelay}")
        private int pollerFixedDelay;
        @Value("${kafka.consumer.numberOfThreads}")
        private int numberOfThreads;
        @Value("${kafka.consumer.maxMessages}")
        private int maxMessages;

        @Bean
        public IntegrationFlow consumer() {

            LOGGER.info("starting consumer..");

            KafkaHighLevelConsumerMessageSourceSpec messageSourceSpec = Kafka.inboundChannelAdapter(
                    new ZookeeperConnect(kafkaConfig.getZookeeperAddress()))
                    .consumerProperties(props ->
                            props.put("auto.offset.reset", offsetReset)
                                    .put("auto.commit.interval.ms", commitIntervalMs))
                    .addConsumer(groupId, metadata -> metadata.consumerTimeout(timeoutMs)
                            .topicStreamMap(m -> m.put(kafkaConfig.getTopic(), numberOfThreads))
                            .maxMessages(maxMessages)
                            .valueDecoder(microTransactionSerializer));

            Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer = e -> e.poller(p -> p.fixedDelay(pollerFixedDelay));

            return IntegrationFlows
                    .from(messageSourceSpec, endpointConfigurer)
                    .<Map<String, Map<String, List<MicroTransaction>>>>handle((payload, headers) -> {
                        payload.entrySet().forEach(e -> {
                            LOGGER.debug(e.getKey() + "=" + e.getValue());
                            e.getValue().values().forEach(v -> v.forEach(cassandraService::addTransaction));
                        });
                        return null;
                    })
                    .get();
        }

    }
}
