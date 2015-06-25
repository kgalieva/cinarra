package com.cinarra.auction.kafka.service;

import com.cinarra.auction.cassandra.service.CassandraService;
import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.KafkaEmbeddedConfig;
import com.cinarra.auction.config.KafkaServiceConfig;
import com.cinarra.auction.domain.MicroTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static com.cinarra.auction.config.KafkaServiceConfig.INPUT_CHANNEL;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {KafkaServiceTest.TestCassandraServiceConfiguration.class, KafkaEmbeddedConfig.class, KafkaServiceConfig.class})
@ActiveProfiles(Embedded.profile)
public class KafkaServiceTest {

    @Configuration
    public static class TestCassandraServiceConfiguration {
        @Bean
        public CassandraService cassandraService() {
            return null;
        }
    }

    @Autowired
    @Qualifier(INPUT_CHANNEL + ".input")
    private MessageChannel producer;

    @InjectMocks
    @Autowired
    KafkaServiceConfig.ConsumerConfiguration consumerConfiguration;

    @Mock
    CassandraService cassandraService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendTest() {
        MicroTransaction tr = new MicroTransaction();
        tr.setProductId("123");
        tr.setTransactionId("456");
        tr.setTransactionTime(System.currentTimeMillis());
        tr.setWinPrice(new BigDecimal(789.11));
        for (int i = 0; i < 15; i++) {
            producer.send(new GenericMessage<>(tr));
        }
        verify(cassandraService, timeout(6000).times(15)).addTransaction(tr);
    }
}
