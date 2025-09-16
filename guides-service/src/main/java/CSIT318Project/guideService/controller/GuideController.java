package CSIT318Project.guideService.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import csci318.model.LearningGuide;
import com.example.csci318.service.LearningGuideService;

@RestController
public class LearningGuideController {
	private final GuideService learningGuideService;

	public LearningGuideController(GuideService learningGuideService) {
		this.learningGuideService = learningGuideService;
	}

	@GetMapping("/learning-guides")
	public List<Guide> getAllLearningGuides() {
		return learningGuideService.findAll();
	}

	@GetMapping("/learning-guides/{id}")
	public Guide getLearningGuide(@PathVariable UUID id) {
		return learningGuideService.getLearningGuide(id);
	}

	@PutMapping("/learning-guides")
	public void createOrUpdateLearningGuide(@RequestBody Guide learningGuide) {
		learningGuideService.saveLearningGuide(learningGuide);
	}
}
