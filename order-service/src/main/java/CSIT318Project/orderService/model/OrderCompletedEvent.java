package CSIT318Project.orderService.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderCompletedEvent {

    private String userId;
    private String orderId;
    private List<UUID> resourceIds;
    private LocalDateTime timestamp;

    public OrderCompletedEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public OrderCompletedEvent(String userId, String orderId, List<UUID> resourceIds) {
        this.userId = userId;
        this.orderId = orderId;
        this.resourceIds = resourceIds;
        this.timestamp = LocalDateTime.now();
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public List<UUID> getResourceIds() { return resourceIds; }
    public void setResourceIds(List<UUID> resourceIds) { this.resourceIds = resourceIds; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}