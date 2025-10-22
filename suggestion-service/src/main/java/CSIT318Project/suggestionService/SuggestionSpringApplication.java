package CSIT318Project.suggestionService;

import CSIT318Project.suggestionService.Enums.KnowledgeLevel;
import CSIT318Project.suggestionService.Enums.KnowledgeType;
import CSIT318Project.suggestionService.model.EducationalResource;
import CSIT318Project.suggestionService.model.Suggestion;
import CSIT318Project.suggestionService.repository.EducationalResourceRepository;
import CSIT318Project.suggestionService.repository.SuggestionRepository;
import CSIT318Project.suggestionService.service.HttpWebClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SuggestionSpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SuggestionSpringApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

    @Bean
    public CommandLineRunner loadDatabase(SuggestionRepository suggestionRepository, EducationalResourceRepository educationalResourceRepository, HttpWebClient webClient) {
        return args -> {
            String response = webClient.GetRESTAsync("http://localhost:8081/resources/withoutContent");

            if (response != null && !response.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                List<EducationalResource> resourceList = new ArrayList<>();

                JsonNode root = mapper.readTree(response);
                for (JsonNode node : root) {
                    String type = node.get("knowledgeType").asText();
                    EducationalResource resource = mapper.treeToValue(node, EducationalResource.class);

                    switch (type.toLowerCase()) {
                        case "paper":
                            resource.setKnowledgeType(KnowledgeType.Paper);
                            break;
                        case "book":
                            resource.setKnowledgeType(KnowledgeType.Book);
                            break;
                        case "video":
                            resource.setKnowledgeType(KnowledgeType.Video);
                            break;
                        default:
                            continue; // skip unknown types
                    }

                    resourceList.add(resource);
                }

                List<EducationalResource> savedResources = educationalResourceRepository.saveAll(resourceList);

                Random rand = new Random();
                for (int i = 1; i <= 20; i++) {
                    Suggestion suggestion = new Suggestion();
                    suggestion.setSummary("Suggestion " + i);
                    suggestion.setSuggestedResources(List.of(
                            savedResources.get(rand.nextInt(savedResources.size())),
                            savedResources.get(rand.nextInt(savedResources.size()))
                    ));
                    suggestionRepository.save(suggestion);
                }
            }
        };
    }
}
