package CSIT318Project.resourceService.service;

import CSIT318Project.resourceService.model.EducationalResource;
import CSIT318Project.resourceService.repository.ResourceRepository;
import CSIT318Project.resourceService.service.dto.ResourceDTO;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final ResourceEventPublisher eventPublisher;

	ResourceService(ResourceRepository resourceRepository, ResourceEventPublisher eventPublisher) {
		this.resourceRepository = resourceRepository;
		this.eventPublisher = eventPublisher;
	}

	public List<ResourceDTO> getAllResources() {
		return resourceRepository.findAll().stream()
				.map(resource -> {
					ResourceDTO resourceDto = new ResourceDTO(resource);
					return resourceDto;
				}).collect(Collectors.toList());
	}

	public ResourceDTO getResource(UUID resourceId) {
		EducationalResource resource = resourceRepository.findById(resourceId).orElseThrow(RuntimeException::new);
		ResourceDTO resourceDto = new ResourceDTO(resource);
		return resourceDto;
	}

	public ResourceDTO createResourceFromFile(MultipartFile file) throws IOException {
		EducationalResource resource = new EducationalResource();
		parseAndPopulateResource(file, resource);
		EducationalResource savedResource = resourceRepository.save(resource);
		return new ResourceDTO(savedResource);
	}

	public ResourceDTO updateResourceFromFile(UUID resourceId, MultipartFile file) throws IOException {
		EducationalResource resource = resourceRepository.findById(resourceId)
				.orElseThrow(() -> new RuntimeException("Resource not found with id: " + resourceId));

		parseAndPopulateResource(file, resource);
		EducationalResource updatedResource = resourceRepository.save(resource);

		// Publish event to Kafka
		eventPublisher.publishResourceContentUpdated(resourceId);

		return new ResourceDTO(updatedResource);
	}

	private void parseAndPopulateResource(MultipartFile file, EducationalResource resource) throws IOException {
		Properties metadata = new Properties();
		StringBuilder content = new StringBuilder();
		boolean inMetadata = true;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (inMetadata) {
					if (line.equals("---")) {
						inMetadata = false;
					} else {
						String[] parts = line.split(":", 2);
						if (parts.length == 2) {
							metadata.setProperty(parts[0].trim(), parts[1].trim());
						}
					}
				} else {
					content.append(line).append("\n");
				}
			}
		}

		String contentString = content.toString();
		if (contentString.startsWith("Title: ")) {
			contentString = contentString.substring(contentString.indexOf("\n") + 1);
		}

		resource.setTitle(metadata.getProperty("title"));
		resource.setAuthors(metadata.getProperty("authors", "").split(","));
		resource.setGenre(metadata.getProperty("genre"));
		resource.setDescription(metadata.getProperty("description"));
		try {
			resource.setPublicationDate(
					new java.text.SimpleDateFormat("yyyy-MM-dd").parse(metadata.getProperty("publicationDate")));
		} catch (java.text.ParseException e) {
			resource.setPublicationDate(null);
		}
		resource.setKnowledgeLevel(CSIT318Project.resourceService.Enums.KnowledgeLevel
				.valueOf(metadata.getProperty("knowledgeLevel", "BEGINNER").toUpperCase()));
		resource.setKnowledgeType(CSIT318Project.resourceService.Enums.KnowledgeType
				.valueOf(metadata.getProperty("knowledgeType", "Paper").toUpperCase()));
		resource.setUrl("https://example.com/default");
		resource.setContent(contentString);
	}
}
