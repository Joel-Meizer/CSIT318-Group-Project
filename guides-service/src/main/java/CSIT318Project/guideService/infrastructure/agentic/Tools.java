package CSIT318Project.guideService.infrastructure.agentic;

import java.util.List;

import org.springframework.stereotype.Component;

import CSIT318Project.guideService.model.ExternalVideo;
import CSIT318Project.guideService.service.YoutubeService;
import dev.langchain4j.agent.tool.Tool;

@Component
public class Tools {
	private final YoutubeService youtubeService;

	public Tools(YoutubeService youtubeService) {
		this.youtubeService = youtubeService;
	}

	@Tool
	public List<ExternalVideo> searchVideos(String query) {
		return youtubeService.searchVideos(query);
	}
}
