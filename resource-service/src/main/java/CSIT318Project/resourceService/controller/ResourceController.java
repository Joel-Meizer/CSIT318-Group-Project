package CSIT318Project.resourceService.controller;

import CSIT318Project.resourceService.service.ResourceService;
import CSIT318Project.resourceService.service.dto.ResourceDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ResourceController {
    private final ResourceService resourceService;

    ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/resources")
    List<ResourceDTO> allBooks() {
        return resourceService.getAllResources();
    }

    @GetMapping("/resources/{resourceId}")
    ResourceDTO findBook(@PathVariable UUID resourceId) {
        return resourceService.getResource(resourceId);
    }
}