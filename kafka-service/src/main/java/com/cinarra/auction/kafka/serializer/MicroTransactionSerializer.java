package com.cinarra.auction.kafka.serializer;

import com.cinarra.auction.domain.MicroTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MicroTransactionSerializer implements Encoder<MicroTransaction>, Decoder<MicroTransaction> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MicroTransactionSerializer.class);

    public MicroTransactionSerializer(VerifiableProperties verifiableProperties) {
            /* This constructor must be present for successful compile. */
    }

    @Override
    public byte[] toBytes(MicroTransaction microTransaction) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(microTransaction);
        } catch (Exception e) {
            LOGGER.error("Unable to serialize transaction with id: {}", microTransaction.getTransactionId(), e);
        }
        return "".getBytes();
    }

    @Override
    public MicroTransaction fromBytes(byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, MicroTransaction.class);
        } catch (Exception e) {
            LOGGER.error("Unable to deserialize transaction: {}", Arrays.toString(bytes), e);
        }
        return null;
    }
}
