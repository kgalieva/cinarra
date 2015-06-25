package com.cinarra.auction.aggregator;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.cassandra.service.CassandraService;
import com.cinarra.auction.postgresql.service.PostgresqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class CassandraToPostgresAggregator implements Aggregator{

    private static final Logger LOGGER = LoggerFactory.getLogger(CassandraToPostgresAggregator.class);

    @Autowired
    CassandraService cassandraService;

    @Autowired
    PostgresqlService postgresqlService;

    public void aggregate() {
        aggregate(LocalDate.now());
    }

    public void aggregate(LocalDate day) {
        List<Product> products = cassandraService.getProducts(day);
        for (Product product: products) {
            List<ProductTransaction> transactions = cassandraService.getTransactions(product.getProductId(), day);
            BigDecimal sum = BigDecimal.ZERO;
            for(ProductTransaction transaction: transactions) {
                sum = sum.add(transaction.getWinPrice());
            }
            LOGGER.debug("started update");
            postgresqlService.updateSum(product.getProductId(), day, sum);
            LOGGER.debug("finished update");
        }
    }
}
