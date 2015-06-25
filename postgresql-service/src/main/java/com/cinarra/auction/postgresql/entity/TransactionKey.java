package com.cinarra.auction.postgresql.entity;

import com.cinarra.auction.postgresql.utils.LocalDateConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class TransactionKey implements Serializable {

    public static final String DELIMITER = "_";

    private static final long serialVersionUID = 1L;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "transaction_date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate transactionDate;


    public TransactionKey() {
    }

    public TransactionKey(String productId, LocalDate transactionDate) {
        this.productId = productId;
        this.transactionDate = transactionDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return productId + DELIMITER + transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionKey that = (TransactionKey) o;

        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        return result;
    }
}
