package CSIT318Project.orderService.controller;

import CSIT318Project.orderService.model.Order;
import CSIT318Project.orderService.model.OrderItem;
import CSIT318Project.orderService.model.OrderStatus;
import CSIT318Project.orderService.model.EducationalResource;
import CSIT318Project.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    //  Get Order History as Educational Resources
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<EducationalResource>> getOrderHistory(@PathVariable Long userId) {
        List<Order> userOrders = orderService.getOrdersByUserId(userId);
        List<EducationalResource> resources = new ArrayList<>();

        for (Order order : userOrders) {
            if (order.getStatus() == OrderStatus.DELIVERED ||
                    order.getStatus() == OrderStatus.SHIPPED) {
                for (OrderItem item : order.getItems()) {
                    EducationalResource resource = new EducationalResource(
                            item.getProductId(),
                            item.getProductName(),
                            "Unknown Author",
                            "Book",
                            item.getPrice()
                    );
                    resources.add(resource);
                }
            }
        }

        return ResponseEntity.ok(resources);
    }

    //  Complete Order
    @PutMapping("/{id}/complete")
    public ResponseEntity<Order> completeOrder(@PathVariable Long id) {
        Order completedOrder = orderService.completeOrder(id);
        return ResponseEntity.ok(completedOrder);
    }
}