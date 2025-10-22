package com.library.accounts.controller;

import com.library.accounts.stream.InteractiveQueryServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class OrderAnalyticsController {

    private final InteractiveQueryServiceImpl interactiveQueryService;

    public OrderAnalyticsController(InteractiveQueryServiceImpl interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    @GetMapping("/api/orders/analytics")
    public List<String> getOrderAnalytics() {
        return interactiveQueryService.getRecentUserOrders();
    }
}
