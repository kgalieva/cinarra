package com.cinarra.auction.postgresql.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PostgresqlService {

    void updateSum(String productId, LocalDate day, BigDecimal sum);

}
