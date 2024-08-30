package kr.co.system.stock.order_item.application;

import kr.co.system.stock.order_item.OrderItemDoubleChecker;
import kr.co.system.stock.order_item.domain.OrderItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class OrderItemConsumer {

    @Value("${kafka.order.topic.name}")
    private String topicName;

    private final OrderItemDoubleChecker orderItemDoubleChecker;
    private final KafkaConsumer<Long, OrderItem> kafkaConsumer;

    public final Logger logger = LoggerFactory.getLogger(getClass());

    public OrderItemConsumer(OrderItemDoubleChecker orderItemDoubleChecker,
                             KafkaConsumer<Long, OrderItem> kafkaConsumer
    ) {
        this.orderItemDoubleChecker = orderItemDoubleChecker;
        this.kafkaConsumer = kafkaConsumer;
    }

    public void receiveOrderItem() {
        kafkaConsumer.subscribe(List.of(topicName));
        while (true) {
            ConsumerRecords<Long, OrderItem> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Long, OrderItem> record : records) {
                logger.info("Key: {}, Value: {}", record.key(), record.value());
                logger.info("Partition {}, Offset: {}", record.partition(), record.offset());
                orderItemDoubleChecker.startCheck(record.value());
            }
        }
    }

}
