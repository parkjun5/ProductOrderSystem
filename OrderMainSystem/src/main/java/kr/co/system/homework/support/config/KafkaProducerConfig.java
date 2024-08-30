package kr.co.system.homework.support.config;

import kr.co.system.homework.order.domain.OrderItem;
import kr.co.system.homework.order.domain.OrderItemSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-server.address}")
    private String bootstrapServerAddress;
    @Value("${kafka.bootstrap-server.port}")
    private String bootstrapServerPort;

    @Bean
    public KafkaProducer<Long, OrderItem> kafkaProducer() {
        Properties props = new Properties();
        // bootstrap.servers, key.serializer, value.serializer,
        String fullAddress = bootstrapServerAddress + ":" + bootstrapServerPort;
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, fullAddress);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, OrderItemSerializer.class.getName());
        // optional
        return new KafkaProducer<>(props);
    }

}
