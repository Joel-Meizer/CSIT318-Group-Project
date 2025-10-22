package com.library.accounts.events;

import com.library.accounts.model.UserAccount;
import com.library.accounts.repository.UserAccountRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderPlacedEventListener {

    private final UserAccountRepository userAccountRepository;

    public OrderPlacedEventListener(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @KafkaListener(topics = "orders", groupId = "accounts-service-group", containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void handleOrderPlaced(OrderPlacedEvent event) {
        try {
            System.out.println("📦 Received OrderPlacedEvent for customer ID: " + event.getCustomerId());

            // Convert customerId (String) to Long for UserAccount lookup
            Long userId = Long.parseLong(event.getCustomerId());

            userAccountRepository.findById(userId).ifPresent(user -> {
                user.addOrderedResource(event.getOrderId());
                userAccountRepository.save(user);
                System.out.println("✅ Updated user " + user.getEmail() +
                        " with new order: " + event.getOrderId() +
                        " (Total Amount: $" + event.getTotalAmount() + ")");
            });
        } catch (Exception e) {
            System.err.println("❌ Failed to handle OrderPlacedEvent: " + e.getMessage());
        }
    }
}
