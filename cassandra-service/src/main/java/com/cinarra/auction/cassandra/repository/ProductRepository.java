package com.cinarra.auction.cassandra.repository;

import com.cinarra.auction.cassandra.entity.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface ProductRepository extends CassandraRepository<Product> {
    @Query("select * from products where transaction_date = ?0")
    List<Product> getProductsByDate(long transactionDate);
}
