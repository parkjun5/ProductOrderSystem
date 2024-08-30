package kr.co.system.homework.order.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.system.homework.support.exception.SerializeFail;
import org.apache.kafka.common.serialization.Serializer;


public class OrderItemSerializer implements Serializer<OrderItem> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, OrderItem orderItem) {
        try {
            return objectMapper.writeValueAsBytes(orderItem);
        } catch (JsonProcessingException e) {
            throw new SerializeFail(e);
        }
    }

}
