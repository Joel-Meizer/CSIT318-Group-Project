package CSIT318Project.guideService.service;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import CSIT318Project.guideService.model.event.ResourceContentUpdatedEvent;

@Configuration
public class GuideEventHandler {
	private final GuideService guideService;

	public GuideEventHandler(GuideService guideService) {
		this.guideService = guideService;
	}

	@Bean
	public Consumer<ResourceContentUpdatedEvent> receiveResourceContentUpdated() {
		return event -> {
			guideService.deleteByResourceId(event.getResourceId());
		};
	}
}
