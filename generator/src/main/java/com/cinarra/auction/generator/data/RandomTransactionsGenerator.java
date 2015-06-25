package com.cinarra.auction.generator.data;

import com.cinarra.auction.common.profiles.Load;
import com.cinarra.auction.domain.MicroTransaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Load
@Component
public class RandomTransactionsGenerator implements TransactionsGenerator {

    public MicroTransaction next() {
        Random random = new Random();
        MicroTransaction transaction = new MicroTransaction();
        transaction.setTransactionTime(System.currentTimeMillis());
        transaction.setProductId(String.valueOf(random.nextInt(1000)));
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setWinPrice(BigDecimal.valueOf(random.nextDouble() * 1000));
        return transaction;
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
