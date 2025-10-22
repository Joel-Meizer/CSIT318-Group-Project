package CSIT318Project.guideService.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import CSIT318Project.guideService.model.dto.ResearchGoalDTO;
import CSIT318Project.guideService.model.dto.ResourceDTO;

import org.springframework.web.client.RestTemplate;

import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.model.GuideNotFoundException;
import CSIT318Project.guideService.infrastructure.repository.GuideRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GuideService {
	private final GuideRepository guideRepository;
	private final RestTemplate restTemplate;
	private final GuideAgent guideAgent;

	public GuideService(GuideRepository guideRepository, RestTemplate restTemplate, GuideAgent guideAgent) {
		this.guideRepository = guideRepository;
		this.restTemplate = restTemplate;
		this.guideAgent = guideAgent;
	}

	public Guide getGuide(UUID resourceId, Long userId) {
		// Fetch research goal from accounts-service
		String researchGoalUrl = "http://localhost:8080/api/users/" + userId + "/research-goal";
		ResearchGoalDTO researchGoalDTO = restTemplate.getForObject(researchGoalUrl, ResearchGoalDTO.class);
		String researchGoal = researchGoalDTO.getResearchGoal();

		// Check if guide exists in the repository
		return guideRepository.findByResourceIdAndResearchGoal(resourceId, researchGoal).orElseGet(() -> {
			// Fetch resource content from resource-service
			String resourceUrl = "http://localhost:8081/resources/" + resourceId;
			ResourceDTO resourceDTO = restTemplate.getForObject(resourceUrl, ResourceDTO.class);
			String bookContent = resourceDTO.getContent();

			// Generate the guide
			                        Guide guide = null;
			                        while (true) {
			                            try {
			                                guide = guideAgent.generateGuide(researchGoal, bookContent).content();
			                                break; // Success, exit the loop
			                            } catch (dev.langchain4j.service.output.OutputParsingException e) {
			                                System.err.println("Failed to parse the response from the GuideAgent, retrying...");
			                            }
			                        }			
						// Set the resourceId and researchGoal on the guide
						guide.setResourceId(resourceId);
						guide.setResearchGoal(researchGoal);
			// Save the new guide to the repository
			guideRepository.save(guide);

			return guide;
		});
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
