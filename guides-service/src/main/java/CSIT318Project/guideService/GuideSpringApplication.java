package CSIT318Project.guideService;

import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import CSIT318Project.guideService.model.ExternalVideo;
import CSIT318Project.guideService.model.Guide;
import CSIT318Project.guideService.model.RelatedSection;
import CSIT318Project.guideService.infrastructure.repository.GuideRepository;

@SpringBootApplication
public class GuideSpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(GuideSpringApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	CommandLineRunner loadDatabase(GuideRepository guideRepository) throws Exception {
		return args -> {
			Guide entry = new Guide();
			entry.setResourceId(UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef"));
			entry.setResearchGoal("Understand the basic structure of a Spring Boot service.");
			entry.setSummary("A simple guide covering the main components: controller, service, and repository.");
			entry.setRelatedSections(List.of(
					new RelatedSection("Introduction", "test"),
					new RelatedSection("Body", "summary")));
			entry.setExternalVideos(List.of(
					new ExternalVideo("Spring Boot Tutorial for Beginners",
							"https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")));

			Guide saved = guideRepository.save(entry);
			System.out.println("Preloaded " + saved);

			Guide entry2 = new Guide();
			entry2.setResourceId(UUID.fromString("12345678-90ab-cdef-1234-567890abcdef"));
			entry2.setResearchGoal("Learn how to create RESTful APIs with Spring Boot.");
			entry2.setSummary("This guide explains how to set up REST endpoints using Spring MVC.");
			entry2.setRelatedSections(List.of(
					new RelatedSection("Getting Started", "introduction"),
					new RelatedSection("Creating Endpoints", "implementation")));
			entry2.setExternalVideos(List.of(
					new ExternalVideo("Building RESTful APIs with Spring Boot",
							"https://www.youtube.com/watch?v=9SGDpanrc8U&list=RD9SGDpanrc8U&start_radio=1")));

			Guide saved2 = guideRepository.save(entry2);
			System.out.println("Preloaded " + saved2);
		};
	}

}
