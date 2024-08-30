package kr.co.system.stock.support.config;

import kr.co.system.stock.order_item.domain.OrderItem;
import kr.co.system.stock.order_item.domain.OrderItemDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-server.address}")
    private String bootstrapServerAddress;
    @Value("${kafka.bootstrap-server.port}")
    private String bootstrapServerPort;

    @Bean
    public KafkaConsumer<Long, OrderItem> kafkaConsumer() {
        Properties props = new Properties();
        // bootstrap.servers, key.serializer, value.serializer,
        String fullAddress = bootstrapServerAddress + ":" + bootstrapServerPort;
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, fullAddress);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, OrderItemDeserializer.class.getName());
        // optional
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group_01");
        return new KafkaConsumer<>(props);
    }

}
