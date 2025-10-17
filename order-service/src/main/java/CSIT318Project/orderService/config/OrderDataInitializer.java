package CSIT318Project.orderService.config;

import CSIT318Project.orderService.model.Order;
import CSIT318Project.orderService.model.OrderItem;
import CSIT318Project.orderService.model.OrderStatus;
import CSIT318Project.orderService.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderDataInitializer {

    @Bean
    public CommandLineRunner loadOrderData(OrderRepository orderRepository) {
        return args -> {
            System.out.println("🔄 Loading Order test data...");

            // Order 1 - User 1 (ACC001) buys 2 books
            Order order1 = new Order(1L);  // userId = 1 (Alice Johnson from account-service)
            order1.setStatus(OrderStatus.DELIVERED);

            OrderItem item1 = new OrderItem(101L, "Clean Code", 1, 49.99);
            OrderItem item2 = new OrderItem(102L, "Design Patterns", 1, 54.99);

            order1.addItem(item1);
            order1.addItem(item2);
            order1.setTotal(order1.calculateTotal());
            orderRepository.save(order1);

            // Order 2 - User 2 (ACC002) buys 1 book
            Order order2 = new Order(2L);  // userId = 2 (Bob Smith)
            order2.setStatus(OrderStatus.SHIPPED);

            OrderItem item3 = new OrderItem(103L, "Spring in Action", 1, 44.99);

            order2.addItem(item3);
            order2.setTotal(order2.calculateTotal());
            orderRepository.save(order2);

            // Order 3 - User 3 (ACC003) buys 2 books
            Order order3 = new Order(3L);  // userId = 3 (Carol White)
            order3.setStatus(OrderStatus.CONFIRMED);

            OrderItem item4 = new OrderItem(104L, "Microservices Patterns", 1, 59.99);
            OrderItem item5 = new OrderItem(105L, "Domain-Driven Design", 1, 64.99);

            order3.addItem(item4);
            order3.addItem(item5);
            order3.setTotal(order3.calculateTotal());
            orderRepository.save(order3);

            // Order 4 - User 4 (ACC004) buys 1 book - PENDING
            Order order4 = new Order(4L);  // userId = 4 (David Brown)
            order4.setStatus(OrderStatus.PENDING);

            OrderItem item6 = new OrderItem(101L, "Clean Code", 1, 49.99);

            order4.addItem(item6);
            order4.setTotal(order4.calculateTotal());
            orderRepository.save(order4);

            // Order 5 - User 1 - High value order (for stream processing demo)
            Order order5 = new Order(1L);
            order5.setStatus(OrderStatus.DRAFT);

            OrderItem item7 = new OrderItem(106L, "Java Programming Course", 1, 299.99);
            OrderItem item8 = new OrderItem(107L, "Advanced Spring Boot", 1, 199.99);

            order5.addItem(item7);
            order5.addItem(item8);
            order5.setTotal(order5.calculateTotal());
            orderRepository.save(order5);

            System.out.println("✅ Order test data loaded successfully!");
            System.out.println("📦 Total orders created: " + orderRepository.count());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("ORDER DATA SUMMARY:");
            System.out.println("ORD001 - User 1 (ACC001) - $104.98 - DELIVERED");
            System.out.println("ORD002 - User 2 (ACC002) - $44.99 - SHIPPED");
            System.out.println("ORD003 - User 3 (ACC003) - $124.98 - CONFIRMED");
            System.out.println("ORD004 - User 4 (ACC004) - $49.99 - PENDING");
            System.out.println("ORD005 - User 1 (ACC001) - $499.98 - DRAFT (High Value!)");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        };
    }
}
