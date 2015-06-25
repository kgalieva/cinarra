package com.cinarra.auction.generator.data;

import com.cinarra.auction.common.profiles.TestScenario;
import com.cinarra.auction.domain.MicroTransaction;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

@TestScenario
@Component
public class CSVTransactionsGenerator implements TransactionsGenerator {

    public static final String SEPARATOR = ",";
    private final BufferedReader source;
    private final Iterator<MicroTransaction> transactionsIterator;
    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public CSVTransactionsGenerator() {
        try {
            InputStream is = CSVTransactionsGenerator.class.getResourceAsStream("/transactions.csv");
            source = new BufferedReader(new InputStreamReader(is));
            transactionsIterator = readRecords();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    Iterator<MicroTransaction> readRecords() throws IOException {

        return source.lines()
                .map(line -> line.split(SEPARATOR))
                .map(this::createTransaction)
                .iterator();
    }

    public MicroTransaction createTransaction(String[] args) {
        try {
            MicroTransaction transaction = new MicroTransaction();
            transaction.setTransactionTime(df.parse(args[0]).getTime());
            transaction.setProductId(args[1]);
            transaction.setTransactionId(args[2]);
            transaction.setWinPrice(new BigDecimal(args[3]));
            return transaction;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MicroTransaction next() {
        return transactionsIterator.next();
    }

    @Override
    public boolean hasNext() {
        return transactionsIterator.hasNext();
    }
}
