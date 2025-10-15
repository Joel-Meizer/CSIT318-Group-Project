package CSIT318Project.guideService.model.event;

import java.util.UUID;

public class ResourceContentUpdatedEvent {
	private UUID resourceId;

	public ResourceContentUpdatedEvent() {
	}

	public ResourceContentUpdatedEvent(UUID resourceId) {
		this.resourceId = resourceId;
	}

	public UUID getResourceId() {
		return resourceId;
	}
}
