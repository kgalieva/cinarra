package com.cinarra.auction.postgresql.utils;

import com.cinarra.auction.postgresql.entity.TransactionKey;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static com.cinarra.auction.postgresql.entity.TransactionKey.DELIMITER;

public class TransactionKeyConverter implements Converter<String, TransactionKey> {

    public TransactionKey convert(String id) {
        int delimeter = id.indexOf(DELIMITER);
        LocalDate transactionDate = LocalDate.parse(id.substring(delimeter + DELIMITER.length()));
        String productId = id.substring(0, delimeter);
        return new TransactionKey(productId, transactionDate);
    }
}