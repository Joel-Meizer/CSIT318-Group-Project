package com.library.accounts.service;

import com.library.accounts.events.*;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class UserEventPublisher {

    private final StreamBridge streamBridge;

    public UserEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishUserCreated(Long id, String email, String firstName, String lastName) {
        UserCreatedEvent event = new UserCreatedEvent(id, email, firstName, lastName);
        streamBridge.send("userCreated-out-0",
                MessageBuilder.withPayload(event).build());
    }

    public void publishMembershipCancelled(Long userId) {
        MembershipCancelledEvent event = new MembershipCancelledEvent(userId);
        streamBridge.send("membershipCancelled-out-0",
                MessageBuilder.withPayload(event).build());
    }
}
