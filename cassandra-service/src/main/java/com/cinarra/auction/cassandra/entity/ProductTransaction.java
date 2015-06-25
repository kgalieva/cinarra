package com.cinarra.auction.cassandra.entity;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.math.BigDecimal;

@Table("product_transactions")
public class ProductTransaction {
    @PrimaryKeyColumn(name = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String productId;

    @PrimaryKeyColumn(name = "transaction_date", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Long transactionDate;

    @PrimaryKeyColumn(name = "transaction_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String transactionId;

    @Column("win_price")
    private BigDecimal winPrice;

    public ProductTransaction() {
    }

    public ProductTransaction(String productId, Long transactionDate, String transactionId, BigDecimal winPrice) {
        this.productId = productId;
        this.transactionDate = transactionDate;
        this.transactionId = transactionId;
        this.winPrice = winPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(BigDecimal winPrice) {
        this.winPrice = winPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTransaction that = (ProductTransaction) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;
        if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
            return false;
        if (winPrice != null ? !winPrice.equals(that.winPrice) : that.winPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (winPrice != null ? winPrice.hashCode() : 0);
        return result;
    }
}
