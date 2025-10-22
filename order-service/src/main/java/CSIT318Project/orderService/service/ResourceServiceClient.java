package CSIT318Project.orderService.client;

import CSIT318Project.orderService.model.EducationalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ResourceServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String RESOURCE_SERVICE_URL = "http://localhost:8081";

    public EducationalResource getResourceById(UUID resourceId) {
        try {
            String url = RESOURCE_SERVICE_URL + "/resources/" + resourceId;
            System.out.println("🔍 Fetching resource from: " + url);

            EducationalResource resource = restTemplate.getForObject(url, EducationalResource.class);

            if (resource != null) {
                System.out.println("✅ Successfully fetched: " + resource.getTitle() + " - $" + resource.getPrice());
            }

            return resource;
        } catch (Exception e) {
            System.err.println("❌ Failed to fetch resource from Resource Service: " + e.getMessage());
            return null;
        }
    }
}