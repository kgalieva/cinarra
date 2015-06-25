package com.cinarra.auction.domain;

import java.math.BigDecimal;

public class MicroTransaction {
    private String transactionId;
    private String productId;
    private Long transactionTime;
    private BigDecimal winPrice;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public BigDecimal getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(BigDecimal winPrice) {
        this.winPrice = winPrice;
    }

    @Override
    public String toString() {
        return "MicroTransaction{" +
                "transactionId='" + transactionId + '\'' +
                ", productId='" + productId + '\'' +
                ", transactionTime=" + transactionTime +
                ", winPrice=" + winPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MicroTransaction that = (MicroTransaction) o;

        if (!productId.equals(that.productId)) return false;
        if (!transactionId.equals(that.transactionId)) return false;
        if (!transactionTime.equals(that.transactionTime)) return false;
        if (!winPrice.equals(that.winPrice)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionId.hashCode();
        result = 31 * result + productId.hashCode();
        result = 31 * result + transactionTime.hashCode();
        result = 31 * result + winPrice.hashCode();
        return result;
    }
}
