package com.cinarra.auction.cassandra.repository;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.repository.ProductRepository;
import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.CassandraServiceConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraServiceConfiguration.class)
@ActiveProfiles(Embedded.profile)
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repository;

    Product product;

    @Before
    public void setUp() {
        product = new Product();
        product.setProductId("123");
        product.setTransactionDate(System.currentTimeMillis());
    }

    @Test
    public void testFindSavedProductById() {
        product = repository.save(product);
        Iterable<Product> products = repository.findAll();
        assertNotNull(products);
        assertThat(products, hasItem(product));
    }

    @Test
    public void testGetProductIdsByDay() {
        product = repository.save(product);
        List<Product> products = repository.getProductsByDate(product.getTransactionDate());
        assertNotNull(products);
        assertTrue(products.size() == 1);
        assertEquals(product, products.get(0));
    }
}