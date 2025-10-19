package CSIT318Project.orderService.stream;

import CSIT318Project.orderService.model.OrderPlacedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderStreamProcessor {

    /**
     * STREAM PROCESSING USE CASE: High-Value Order Detection
     *
     * SQL Query:
     * SELECT orderId, customerId, totalAmount, timestamp
     * FROM orders
     * WHERE totalAmount > 100
     *
     * Description:
     * Monitors order stream in real-time and detects orders exceeding $100.
     * Triggers priority processing, fraud detection, and VIP service alerts.
     *
     * Business Value:
     * - Priority fulfillment for expensive orders
     * - Fraud detection on high-value transactions
     * - VIP customer service triggers
     * - Revenue impact monitoring
     */
    @Bean
    public Consumer<OrderPlacedEvent> detectHighValueOrders() {
        return event -> {
            if (event.getTotalAmount() > 100.0) {
                System.out.println("\n════════════════════════════════════════════════════");
                System.out.println("🚨 HIGH-VALUE ORDER DETECTED!");
                System.out.println("════════════════════════════════════════════════════");
                System.out.println("  Order ID:       " + event.getOrderId());
                System.out.println("  Customer ID:    " + event.getCustomerId());
                System.out.println("  Amount:         $" + String.format("%.2f", event.getTotalAmount()));
                System.out.println("  Timestamp:      " + event.getTimestamp());
                System.out.println("  Action:         ⚡ PRIORITY PROCESSING REQUIRED");
                System.out.println("════════════════════════════════════════════════════\n");
            } else {
                System.out.println("✓ Standard order: $" + String.format("%.2f", event.getTotalAmount()) +
                        " (Order " + event.getOrderId() + ")");
            }
        };
    }
}