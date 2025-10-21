package CSIT318Project.orderService.service;

import CSIT318Project.orderService.model.Order;
import CSIT318Project.orderService.model.OrderItem;
import CSIT318Project.orderService.model.OrderStatus;
import CSIT318Project.orderService.model.OrderPlacedEvent;
import CSIT318Project.orderService.model.OrderCompletedEvent;
import CSIT318Project.orderService.repository.OrderRepository;
import CSIT318Project.orderService.stream.OrderStreamProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderStreamProcessor streamProcessor;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.DRAFT);
        order.setTotal(order.calculateTotal());
        Order savedOrder = orderRepository.save(order);

        // Publish event
        try {
            OrderPlacedEvent event = new OrderPlacedEvent(
                    String.valueOf(savedOrder.getId()),
                    String.valueOf(savedOrder.getUserId()),
                    savedOrder.getTotal()
            );
            orderEventPublisher.publishOrderPlaced(event);

            // DEBUG LINE
            System.out.println("🔍 Testing stream processor with amount: $" + savedOrder.getTotal());

            // TRIGGER STREAM PROCESSOR
            streamProcessor.analyzeOrderStream().accept(event);

        } catch (Exception e) {
            System.err.println("⚠️ Event publishing failed: " + e.getMessage());
        }

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order completeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.DELIVERED);
        Order completedOrder = orderRepository.save(order);

        try {
            List<Long> resourceIds = completedOrder.getItems().stream()
                    .map(OrderItem::getProductId)
                    .collect(Collectors.toList());

            OrderCompletedEvent event = new OrderCompletedEvent(
                    String.valueOf(completedOrder.getUserId()),
                    String.valueOf(completedOrder.getId()),
                    resourceIds
            );
            orderEventPublisher.publishOrderCompleted(event);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish order completed event: " + e.getMessage());
        }

        return completedOrder;
    }
}