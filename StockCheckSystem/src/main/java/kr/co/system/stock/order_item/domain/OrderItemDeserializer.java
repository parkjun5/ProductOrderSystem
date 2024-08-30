package kr.co.system.stock.order_item.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;


public class OrderItemDeserializer implements Deserializer<OrderItem> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderItem deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, OrderItem.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
