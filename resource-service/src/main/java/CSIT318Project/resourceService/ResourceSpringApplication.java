package CSIT318Project.resourceService;

import CSIT318Project.resourceService.Enums.KnowledgeLevel;
import CSIT318Project.resourceService.Enums.KnowledgeType;
import CSIT318Project.resourceService.model.EducationalResource;
import CSIT318Project.resourceService.repository.ResourceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class ResourceSpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(ResourceSpringApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
