package com.debezium.uygulama.deserializer;

import com.debezium.uygulama.model.ProductMessageCDC;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class ProductMessageDeserializer implements Deserializer<ProductMessageCDC> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ProductMessageCDC deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes , ProductMessageCDC.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductMessageCDC deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
