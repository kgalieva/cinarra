package com.cinarra.auction.aggregator;

import com.cinarra.auction.cassandra.entity.Product;
import com.cinarra.auction.cassandra.entity.ProductTransaction;
import com.cinarra.auction.cassandra.service.CassandraService;
import com.cinarra.auction.postgresql.service.PostgresqlService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CassandraToPostgresAggregatorTest {

    @InjectMocks
    CassandraToPostgresAggregator aggregator;

    @Mock
    CassandraService cassandraService;

    @Mock
    PostgresqlService postgresqlService;

    LocalDate todayDate = LocalDate.now();
    long today = todayDate.toEpochDay();



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(cassandraService.getProducts(todayDate)).thenReturn(Arrays.asList(
                new Product(today, "123"),
                new Product(today, "456")
        ));

        when(cassandraService.getTransactions("123", todayDate)).thenReturn(Arrays.asList(
                new ProductTransaction("123", today, "1", new BigDecimal("56.21")),
                new ProductTransaction("123", today, "2", new BigDecimal("76.33")),
                new ProductTransaction("123", today, "3", new BigDecimal("94.82"))
        ));

        when(cassandraService.getTransactions("456", todayDate)).thenReturn(Arrays.asList(
                new ProductTransaction("456", today, "4", new BigDecimal("98.44")),
                new ProductTransaction("456", today, "5", new BigDecimal("80.80")),
                new ProductTransaction("456", today, "6", new BigDecimal("11.11"))
        ));
    }

    @Test
    public void testAggregate() {
        aggregator.aggregate();
        verify(postgresqlService).updateSum("123", todayDate, new BigDecimal("227.36"));
        verify(postgresqlService).updateSum("456", todayDate, new BigDecimal("190.35"));
    }
}