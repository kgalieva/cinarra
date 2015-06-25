package com.cinarra.auction.cassandra.repository;

import com.cinarra.auction.cassandra.entity.ProductTransaction;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface ProductTransactionRepository extends CassandraRepository<ProductTransaction> {
    @Query("select * from product_transactions where product_id = ?0 and transaction_date = ?1")
    List<ProductTransaction> getTransactionsByProductAndDate(String productId, long transactionDate);
}
