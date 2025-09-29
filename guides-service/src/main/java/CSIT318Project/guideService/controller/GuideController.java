package CSIT318Project.guideService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.service.GuideService;

@RestController
public class GuideController {
	private final GuideService guideService;

	public GuideController(GuideService guideService) {
		this.guideService = guideService;
	}

	@GetMapping("/guide")
	public Guide getGuideByResourceAndGoal(@RequestParam UUID resourceId, @RequestParam String researchGoal) {
		return guideService.getGuide(resourceId, researchGoal);
	}

	@GetMapping("/guides")
	public List<Guide> getAllGuides() {
		return guideService.findAll();
	}

	@GetMapping("/guide/{id}")
	public Guide getGuide(@PathVariable UUID id) {
		return guideService.getGuide(id);
	}

	@PostMapping("/guide")
	public void createOrUpdateGuide(@RequestBody Guide guide) {
		guideService.saveGuide(guide);
	}
}
