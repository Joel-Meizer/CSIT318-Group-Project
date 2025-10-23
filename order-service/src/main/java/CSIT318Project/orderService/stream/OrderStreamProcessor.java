package CSIT318Project.orderService.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import CSIT318Project.events.OrderPlacedEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Configuration
public class OrderStreamProcessor {

    // In-memory sliding window to store recent orders for stream analysis
    private final List<OrderPlacedEvent> orderStream = new CopyOnWriteArrayList<>();

    /**
     * STREAM PROCESSING: Analyzes order stream with sliding window
     *
     * SQL-Like Query:
     * SELECT
     *   orderId,
     *   totalAmount,
     *   AVG(totalAmount) OVER (ORDER BY timestamp ROWS BETWEEN 99 PRECEDING AND CURRENT ROW) as stream_avg,
     *   COUNT(*) FILTER (WHERE totalAmount > 100) OVER (ORDER BY timestamp ROWS BETWEEN 99 PRECEDING AND CURRENT ROW) as high_value_count
     * FROM orders
     * WHERE totalAmount > 100 OR totalAmount > stream_avg * 2
     *
     * Stream Analysis Features:
     * 1. Sliding window (last 100 orders)
     * 2. Running average calculation
     * 3. High-value order count
     * 4. Anomaly detection (orders 2x above average)
     */
    @Bean
    public Consumer<OrderPlacedEvent> analyzeOrderStream() {
        return event -> {
            // Add current event to stream
            orderStream.add(event);

            // Maintain sliding window of 100 orders
            if (orderStream.size() > 100) {
                orderStream.remove(0);
            }

            // STREAM ANALYSIS 1: Calculate running average from stream
            double streamAverage = orderStream.stream()
                    .mapToDouble(OrderPlacedEvent::getTotalAmount)
                    .average()
                    .orElse(0.0);

            // STREAM ANALYSIS 2: Count high-value orders in current stream
            long highValueCount = orderStream.stream()
                    .filter(o -> o.getTotalAmount() > 100)
                    .count();

            // STREAM ANALYSIS 3: Calculate percentage of high-value orders
            double highValuePercentage = (highValueCount * 100.0) / orderStream.size();

            // STREAM ANALYSIS 4: Find maximum order in stream
            double streamMax = orderStream.stream()
                    .mapToDouble(OrderPlacedEvent::getTotalAmount)
                    .max()
                    .orElse(0.0);

            // DETECTION 1: High-value order (simple threshold)
            boolean isHighValue = event.getTotalAmount() > 100.0;

            // DETECTION 2: Anomaly detection (2x above stream average)
            boolean isAnomaly = event.getTotalAmount() > (streamAverage * 2);

            // Generate analysis report
            if (isHighValue || isAnomaly) {
                System.out.println("\n════════════════════════════════════════════════════");

                if (isAnomaly) {
                    System.out.println("🚨 ANOMALY DETECTED - ORDER SIGNIFICANTLY ABOVE AVERAGE!");
                } else {
                    System.out.println("🚨 HIGH-VALUE ORDER DETECTED!");
                }

                System.out.println("════════════════════════════════════════════════════");
                System.out.println("📊 CURRENT ORDER:");
                System.out.println("  Order ID:       " + event.getOrderId());
                System.out.println("  Customer ID:    " + event.getCustomerId());
                System.out.println("  Amount:         $" + String.format("%.2f", event.getTotalAmount()));
                System.out.println("  Timestamp:      " + event.getTimestamp());

                System.out.println("\n📈 STREAM ANALYTICS (Last " + orderStream.size() + " orders):");
                System.out.println("  Stream Average: $" + String.format("%.2f", streamAverage));
                System.out.println("  Stream Maximum: $" + String.format("%.2f", streamMax));
                System.out.println("  High-Value Count: " + highValueCount + " orders");
                System.out.println("  High-Value %:   " + String.format("%.1f", highValuePercentage) + "%");

                if (isAnomaly) {
                    double multiplier = event.getTotalAmount() / streamAverage;
                    System.out.println("\n⚠️  ANOMALY METRICS:");
                    System.out.println("  This order is " + String.format("%.1f", multiplier) + "x the stream average!");
                    System.out.println("  Possible fraud - requires manual review");
                }

                System.out.println("\n⚡ ACTIONS:");
                if (isAnomaly) {
                    System.out.println("  - FLAG FOR FRAUD REVIEW");
                    System.out.println("  - HOLD SHIPMENT PENDING VERIFICATION");
                    System.out.println("  - CONTACT CUSTOMER FOR CONFIRMATION");
                } else {
                    System.out.println("  - PRIORITY PROCESSING");
                    System.out.println("  - VIP CUSTOMER SERVICE");
                    System.out.println("  - EXPEDITED SHIPPING");
                }

                System.out.println("════════════════════════════════════════════════════\n");
            } else {
                // Standard order - compact log
                System.out.println("✓ Standard order: $" + String.format("%.2f", event.getTotalAmount()) +
                        " (Order " + event.getOrderId() +
                        ") | Stream Avg: $" + String.format("%.2f", streamAverage));
            }
        };
    }
}