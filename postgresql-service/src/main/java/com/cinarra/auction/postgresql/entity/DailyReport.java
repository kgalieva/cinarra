package com.cinarra.auction.postgresql.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions_by_day")
public class DailyReport extends AbstractEntity {
    public DailyReport() {
    }

    public DailyReport(String productId, LocalDate transactionDate, BigDecimal totalCost) {
        super(productId, transactionDate, totalCost);
    }
}