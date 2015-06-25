package com.cinarra.auction.cassandra.service;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.common.utils.Utils;
import com.cinarra.auction.config.CassandraServiceConfiguration;
import com.cinarra.auction.domain.MicroTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraServiceConfiguration.class)
@ActiveProfiles(Embedded.profile)
public class CassandraServiceTest {

    @Autowired
    CassandraService cassandraService;

    @Test
    public void testAddTransaction() throws ExecutionException, InterruptedException {
        MicroTransaction tr = new MicroTransaction();
        tr.setProductId("123");
        tr.setTransactionId("456");
        tr.setTransactionTime(System.currentTimeMillis());
        tr.setWinPrice(new BigDecimal(789.11));
        cassandraService.addTransaction(tr);

        List<Product> products = cassandraService.getProducts(LocalDate.now());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        Product product = products.get(0);
        assertEquals(tr.getProductId(), product.getProductId());

        List<ProductTransaction> transactions = cassandraService.getTransactions(tr.getProductId(), LocalDate.now());
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());
        ProductTransaction transaction = transactions.get(0);
        assertNotNull(transaction);
        assertEquals(tr.getProductId(), transaction.getProductId());
        assertEquals(tr.getWinPrice(), transaction.getWinPrice());
        assertEquals(tr.getTransactionId(), transaction.getTransactionId());
    }
}
