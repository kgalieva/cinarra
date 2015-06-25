package com.cinarra.auction.generator.data;

import com.cinarra.auction.domain.MicroTransaction;

public interface TransactionsGenerator {
    MicroTransaction next();
    boolean hasNext();
}
