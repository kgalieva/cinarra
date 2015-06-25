package com.cinarra.auction.generator.data;

import com.cinarra.auction.domain.MicroTransaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class CSVTransactionsGeneratorTest {

    @Test
    public void readsRecords() throws ParseException {
        CSVTransactionsGenerator csvTransactionsGenerator = new CSVTransactionsGenerator();
        assertTrue(csvTransactionsGenerator.hasNext());
        MicroTransaction transaction = csvTransactionsGenerator.next();
        assertNotNull(transaction);
        assertEquals("12345", transaction.getProductId());
        assertEquals("1", transaction.getTransactionId());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        assertEquals(df.parse("2015-06-14T10:15:30").getTime(), (long)transaction.getTransactionTime());
        assertEquals(new BigDecimal("123.11"), transaction.getWinPrice());
    }
}