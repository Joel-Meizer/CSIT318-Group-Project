package CSIT318Project.resourceService.service;

import CSIT318Project.events.ResourceContentUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceEventPublisher {
    private final StreamBridge streamBridge;

    public ResourceEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishResourceContentUpdated(UUID resourceId) {
        ResourceContentUpdatedEvent event = new ResourceContentUpdatedEvent(resourceId);
        streamBridge.send("sendResourceContentUpdated-out-0", event);
    }
}
