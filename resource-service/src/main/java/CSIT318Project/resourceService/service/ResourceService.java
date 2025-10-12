package CSIT318Project.resourceService.service;

import CSIT318Project.resourceService.model.EducationalResource;
import CSIT318Project.resourceService.repository.ResourceRepository;
import CSIT318Project.resourceService.service.dto.ResourceDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResourceService {


    private final ResourceRepository resourceRepository;

    ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
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
}
