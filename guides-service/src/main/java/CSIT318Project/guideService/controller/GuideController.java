package CSIT318Project.guideService.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.service.GuideAgent;
import CSIT318Project.guideService.service.GuideService;

@RestController
public class GuideController {
	private final GuideService guideService;
	private final GuideAgent guideAgent;

	public GuideController(GuideService guideService, GuideAgent guideAgent) {
		this.guideService = guideService;
		this.guideAgent = guideAgent;
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

	@PostMapping("/guideAgent")
	public String chat(@RequestParam String researchGoal, @RequestParam MultipartFile file) throws IOException {
		String bookContent = new String(file.getBytes(), StandardCharsets.UTF_8);
		return guideAgent.generateGuide(researchGoal, bookContent).content();
	}
}
