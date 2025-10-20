package CSIT318Project.guideService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.service.GuideAgent;
import CSIT318Project.guideService.service.GuideService;
import CSIT318Project.guideService.service.YoutubeService;

@RestController
public class GuideController {
	private final GuideService guideService;

	public GuideController(GuideService guideService, GuideAgent guideAgent, YoutubeService youtubeService) {
		this.guideService = guideService;
	}

	@GetMapping("/guide")
	public Guide getGuide(@RequestParam UUID resourceId, @RequestParam Long userId) {
		return guideService.getGuide(resourceId, userId);
	}

	@GetMapping("/guides")
	public List<Guide> getAllGuides() {
		return guideService.findAll();
	}

	@GetMapping("/guide/{id}")
	public Guide getGuide(@PathVariable UUID id) {
		return guideService.getGuide(id);
	}
}
