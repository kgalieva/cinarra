package com.cinarra.auction.generator;

import com.cinarra.auction.domain.MicroTransaction;
import com.cinarra.auction.generator.data.TransactionsGenerator;
import com.cinarra.auction.kafka.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Producer implements Runnable {
    private final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("${producer.totalTransactionsNumberToSend}")
    private long totalTransactionsNumberToSend;
    private final KafkaService kafkaService;
    protected long messagesSent = 0;

    @Autowired
    TransactionsGenerator transactionsGenerator;

    public Producer(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public void run() {
        send();
    }

    private void send() {
        LOGGER.info("Sending " + totalTransactionsNumberToSend + " messages");
        for (; haveMessagesToSend(); messagesSent++) {
            sendMessage(transactionsGenerator.next());
        }
        LOGGER.info("All Messages Dispatched");
    }

    private boolean haveMessagesToSend() {
        if (!transactionsGenerator.hasNext()) return false;
        return totalTransactionsNumberToSend < 0 || messagesSent < totalTransactionsNumberToSend;
    }

    private void sendMessage(MicroTransaction transaction) {
        kafkaService.send(transaction);
    }

}