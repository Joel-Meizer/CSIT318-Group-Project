package com.library.accounts.controller;

import com.library.accounts.service.OrderAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class OrderAnalyticsController {

    private final OrderAnalysisService analysisService;

    public OrderAnalyticsController(OrderAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/{userId}/summary")
    public String getUserSummary(@PathVariable String userId) {
        double totalSpent = analysisService.getTotalSpent(userId);
        long orderCount = analysisService.getOrderCount(userId);

        return String.format("📈 User %s summary: %d orders, total spent $%.2f",
                userId, orderCount, totalSpent);
    }
}
