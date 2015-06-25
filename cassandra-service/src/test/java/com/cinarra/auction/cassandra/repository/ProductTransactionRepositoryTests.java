package com.cinarra.auction.cassandra.repository;

import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.cassandra.repository.ProductTransactionRepository;
import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.CassandraServiceConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraServiceConfiguration.class)
@ActiveProfiles(Embedded.profile)
public class ProductTransactionRepositoryTests {

    @Autowired
    ProductTransactionRepository repository;

    ProductTransaction productTransaction;

    @Before
    public void setUp() {
        productTransaction = new ProductTransaction();
        productTransaction.setProductId("432");
        productTransaction.setTransactionId("876");
        productTransaction.setTransactionDate(System.currentTimeMillis());
        productTransaction.setWinPrice(new BigDecimal(987));
    }

    @Test
    public void testFindSavedProductById() {
        productTransaction = repository.save(productTransaction);
        Iterable<ProductTransaction> transactions = repository.findAll();
        assertNotNull(transactions);
        assertThat(transactions, hasItem(productTransaction));
    }

    @Test
    public void testGetProductIdsByDay() {
        productTransaction = repository.save(productTransaction);
        List<ProductTransaction> transactions = repository.getTransactionsByProductAndDate(productTransaction.getProductId(), productTransaction.getTransactionDate());
        assertNotNull(transactions);
        assertThat(transactions, hasItem(productTransaction));
    }
}