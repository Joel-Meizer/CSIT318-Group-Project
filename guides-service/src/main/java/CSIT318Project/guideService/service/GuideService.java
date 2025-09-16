package CSIT318Project.guideService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.model.GuideNotFoundException;
import CSIT318Project.guideService.repository.GuideRepository;

@Service
public class GuideService {
	private final GuideRepository guideRepository;

	public GuideService(GuideRepository learningGuideRepository) {
		this.guideRepository = learningGuideRepository;
	}

	public Guide getLearningGuide(UUID id) {
		return guideRepository.findById(id).orElseThrow(
				() -> new GuideNotFoundException("LearningGuide not found"));
	}

	public List<Guide> findAll() {
		return guideRepository.findAll();
	}

	public void saveLearningGuide(Guide guide) {
		guideRepository.save(guide);
	}
}
