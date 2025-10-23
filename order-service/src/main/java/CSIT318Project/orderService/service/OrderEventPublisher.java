package CSIT318Project.orderService.service;

import CSIT318Project.events.OrderPlacedEvent;
import CSIT318Project.orderService.model.OrderCompletedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderPlaced(OrderPlacedEvent event) {
        try {
            kafkaTemplate.send("orders", event.getOrderId(), event);
            System.out.println("✅ Event published: Order " + event.getOrderId());
        } catch (Exception e) {
            System.err.println("❌ Failed to publish event: " + e.getMessage());
        }
    }

    public void publishOrderCompleted(OrderCompletedEvent event) {
        try {
            kafkaTemplate.send("order-completed", event.getUserId(), event);
            System.out.println("✅ OrderCompletedEvent published: User " + event.getUserId());
        } catch (Exception e) {
            System.err.println("❌ Failed to publish OrderCompletedEvent: " + e.getMessage());
        }
    }
}