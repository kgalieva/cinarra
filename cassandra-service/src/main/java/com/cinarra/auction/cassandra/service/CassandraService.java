package com.cinarra.auction.cassandra.service;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.domain.MicroTransaction;
import com.google.common.util.concurrent.ListenableFuture;

import java.time.LocalDate;
import java.util.List;

public interface CassandraService {
    List<Product> getProducts(LocalDate day);

    List<ProductTransaction> getTransactions(String productId, LocalDate day);

    void addTransaction(MicroTransaction transaction);
}
