package CSIT318Project.events;

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
