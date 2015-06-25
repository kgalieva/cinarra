package com.cinarra.auction.cassandra.service;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.cassandra.repository.ProductRepository;
import com.cinarra.auction.cassandra.repository.ProductTransactionRepository;
import com.cinarra.auction.domain.MicroTransaction;
import com.google.common.util.concurrent.ListenableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.cinarra.auction.common.utils.Utils.getDay;

@Service
public class CassandraServiceImpl implements CassandraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTransactionRepository productTransactionRepository;

    @Override
    public List<Product> getProducts(LocalDate day) {
        return productRepository.getProductsByDate(day.toEpochDay());
    }

    @Override
    public List<ProductTransaction> getTransactions(String productId, LocalDate day) {
        return productTransactionRepository.getTransactionsByProductAndDate(productId, day.toEpochDay());
    }

    @Override
    public void addTransaction(MicroTransaction transaction) {
        long day = getDay(transaction.getTransactionTime());
        Product product = new Product(day, transaction.getProductId());
        productRepository.save(product);
        ProductTransaction productTransaction = new ProductTransaction(transaction.getProductId(),
                day, transaction.getTransactionId(), transaction.getWinPrice());
        productTransactionRepository.save(productTransaction);
    }
}
