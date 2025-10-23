package com.library.accounts.service;

import CSIT318Project.events.OrderPlacedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

@Configuration
public class OrderAnalysisService {


    private final ConcurrentHashMap<String, DoubleAdder> totalSpent = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, LongAdder> orderCount = new ConcurrentHashMap<>();


    @Bean
    public Consumer<OrderPlacedEvent> analyzeUserOrders() {
        return event -> {
            String userId = event.getCustomerId();
            double total = event.getTotalAmount();

            System.out.println("[LIVE STREAM] Received event for user " + event.getCustomerId() +
                    " | total: $" + event.getTotalAmount() + " at " + java.time.LocalTime.now());

            totalSpent.computeIfAbsent(userId, k -> new DoubleAdder()).add(total);
            orderCount.computeIfAbsent(userId, k -> new LongAdder()).increment();

            System.out.println("📊 [Accounts-Service] Updated analytics for user " + userId);
            System.out.println("   → Total orders: " + orderCount.get(userId).sum());
            System.out.println("   → Total spent: $" + totalSpent.get(userId).sum());
        };
    }

    public double getTotalSpent(String userId) {
        return totalSpent.getOrDefault(userId, new DoubleAdder()).sum();
    }

    public long getOrderCount(String userId) {
        return orderCount.getOrDefault(userId, new LongAdder()).sum();
    }
}
