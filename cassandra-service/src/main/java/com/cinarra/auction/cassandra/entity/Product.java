package com.cinarra.auction.cassandra.entity;


import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table("products")
public class Product {
    @PrimaryKeyColumn(name = "transaction_date", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long transactionDate;

    @PrimaryKeyColumn(name = "product_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String productId;

    public Product() {
    }

    public Product(Long transactionDate, String productId) {
        this.transactionDate = transactionDate;
        this.productId = productId;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != null ? !productId.equals(product.productId) : product.productId != null) return false;
        if (transactionDate != null ? !transactionDate.equals(product.transactionDate) : product.transactionDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transactionDate != null ? transactionDate.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
