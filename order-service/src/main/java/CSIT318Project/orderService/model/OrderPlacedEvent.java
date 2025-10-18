package CSIT318Project.orderService.model;

import java.time.LocalDateTime;

public class OrderPlacedEvent {

    private String orderId;
    private String customerId;
    private double totalAmount;
    private LocalDateTime timestamp;

    // Default constructor
    public OrderPlacedEvent() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor
    public OrderPlacedEvent(String orderId, String customerId, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}