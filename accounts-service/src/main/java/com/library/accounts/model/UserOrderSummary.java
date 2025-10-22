package com.library.accounts.model;

public class UserOrderSummary {
    private Long userId;
    private Long totalOrders;

    public UserOrderSummary() {}

    public UserOrderSummary(Long userId, Long totalOrders) {
        this.userId = userId;
        this.totalOrders = totalOrders;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    @Override
    public String toString() {
        return "UserOrderSummary{" +
                "userId=" + userId +
                ", totalOrders=" + totalOrders +
                '}';
    }
}
