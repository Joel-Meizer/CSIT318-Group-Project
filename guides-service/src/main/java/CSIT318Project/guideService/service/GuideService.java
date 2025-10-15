package CSIT318Project.guideService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.model.GuideNotFoundException;
import CSIT318Project.guideService.infrastructure.repository.GuideRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GuideService {
	private final GuideRepository guideRepository;

	public GuideService(GuideRepository guideRepository) {
		this.guideRepository = guideRepository;
	}

	public Guide getGuide(UUID resourceId, String researchGoal) {
		return guideRepository.findByResourceIdAndResearchGoal(resourceId, researchGoal).orElseThrow(
				() -> new GuideNotFoundException("Guide not found"));
	}

	public Guide getGuide(UUID id) {
		return guideRepository.findById(id).orElseThrow(
				() -> new GuideNotFoundException("Guide not found"));
	}

	public List<Guide> findAll() {
		return guideRepository.findAll();
	}

	public void saveGuide(Guide guide) {
		guideRepository.save(guide);
	}

	@Transactional
	public void deleteByResourceId(UUID resourceId) {
		guideRepository.deleteByResourceId(resourceId);
	}
}
