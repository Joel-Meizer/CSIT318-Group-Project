package CSIT318Project.orderService.service;

import CSIT318Project.orderService.model.Order;
import CSIT318Project.orderService.model.OrderStatus;
import CSIT318Project.orderService.model.OrderPlacedEvent;
import CSIT318Project.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

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
}