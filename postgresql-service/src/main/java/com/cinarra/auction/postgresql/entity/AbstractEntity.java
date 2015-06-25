package com.cinarra.auction.postgresql.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.LocalDate;

@MappedSuperclass
public class AbstractEntity {

    @EmbeddedId
    private TransactionKey pk;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    public AbstractEntity() {
    }

    public AbstractEntity(String productId, LocalDate transactionDate, BigDecimal totalCost) {
        this.pk = new TransactionKey(productId, transactionDate);
        this.totalCost = totalCost;
    }

    public TransactionKey getPk() {
        return pk;
    }

    public void setPk(TransactionKey pk) {
        this.pk = pk;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}