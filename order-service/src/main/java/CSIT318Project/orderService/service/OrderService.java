package CSIT318Project.orderService.service;

import CSIT318Project.orderService.client.ResourceServiceClient;
import CSIT318Project.orderService.model.EducationalResource;
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
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Autowired
    private OrderStreamProcessor streamProcessor;

    @Autowired
    private ResourceServiceClient resourceServiceClient;

    public Order createOrder(Order order) {
        // FETCH REAL DATA FROM RESOURCE SERVICE
        System.out.println("📦 Processing order with " + order.getItems().size() + " items");

        for (OrderItem item : order.getItems()) {
            // If productName or price not provided, fetch from Resource Service
            if (item.getProductName() == null || item.getPrice() == null) {
                System.out.println("🔍 Fetching data for product: " + item.getProductId());

                EducationalResource resource = resourceServiceClient.getResourceById(item.getProductId());

                if (resource != null) {
                    item.setProductName(resource.getTitle());
                    item.setPrice(resource.getPrice());
                    System.out.println("✅ Populated: " + resource.getTitle() + " - $" + resource.getPrice());
                } else {
                    throw new RuntimeException("❌ Resource not found: " + item.getProductId());
                }
            }

            // Set the bidirectional relationship
            item.setOrder(order);
        }

        // Set initial state and calculate total
        order.setStatus(OrderStatus.DRAFT);
        order.setTotal(order.calculateTotal());
        Order savedOrder = orderRepository.save(order);

        System.out.println("🔍 Testing stream processor with amount: $" + savedOrder.getTotal());

        // Publish event and trigger stream processing
        try {
            OrderPlacedEvent event = new OrderPlacedEvent(
                    String.valueOf(savedOrder.getId()),
                    String.valueOf(savedOrder.getUserId()),
                    savedOrder.getTotal()
            );
            orderEventPublisher.publishOrderPlaced(event);
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
                    .map(item -> item.getProductId().getMostSignificantBits()) // Convert UUID to Long for compatibility
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

    public List<EducationalResource> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED ||
                        order.getStatus() == OrderStatus.SHIPPED)
                .flatMap(order -> order.getItems().stream())
                .map(item -> new EducationalResource(
                        item.getProductId(),
                        item.getProductName(),
                        "", // author - not stored in OrderItem
                        "", // category - not stored in OrderItem
                        item.getPrice()
                ))
                .distinct()
                .collect(Collectors.toList());
    }
}