package com.cinarra.auction.aggregator;

import java.time.LocalDate;

public interface Aggregator {
    void aggregate(LocalDate day);
}
