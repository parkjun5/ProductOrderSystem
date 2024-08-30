package kr.co.system.homework.order.application;

import kr.co.system.homework.order.domain.OrderItem;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderItemProducer {

    @Value("${kafka.order.topic.name}")
    private String topicName;

    private final KafkaProducer<Long, OrderItem> kafkaProducer;

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public OrderItemProducer(KafkaProducer<Long, OrderItem> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendOrderMessage(OrderItem orderItem) {
        ProducerRecord<Long, OrderItem> producerRecord = new ProducerRecord<>(topicName,
                orderItem.getId(),
                orderItem
        );
        kafkaProducer.send(producerRecord, callBackLog(orderItem));
    }

    private Callback callBackLog(OrderItem orderItem) {
        return (metadata, e) -> {
            if (e == null) {
                logger.info("async message: {} partition : {} offset : {}",
                        orderItem.getId(),
                        metadata.partition(),
                        metadata.offset()
                );
            } else {
                logger.error("exception error from broker {}", e.getMessage());
            }
        };
    }

}
