package CSIT318Project.resourceService;

import CSIT318Project.resourceService.Enums.KnowledgeLevel;
import CSIT318Project.resourceService.model.EducationalResource;
import CSIT318Project.resourceService.model.Inheritors.Book;
import CSIT318Project.resourceService.model.Inheritors.Paper;
import CSIT318Project.resourceService.model.Inheritors.Video;
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

	@Bean
	public CommandLineRunner loadDatabase(ResourceRepository resourceRepository) throws Exception {
        return args -> {
            String[] genres = {"Programming", "AI Research", "Software Development", "Cybersecurity", "Data Science", "Machine Learning", "Cloud Computing", "Web Development", "Mobile Apps", "DevOps"};
            String[] authors = {"Alice Johnson", "Bob Lee", "Dr. Jane Smith", "Carlos Vega", "Dana White", "Emily Zhang", "Frank Harris", "Grace Kim", "Henry Ford", "Isla Nguyen"};
            String[] urls = {"https://example.com/resource1", "https://example.com/resource2", "https://example.com/resource3", "https://example.com/resource4", "https://example.com/resource5"};

            List<EducationalResource> moreResources = new ArrayList<>();
            Random rand = new Random();

            for (int i = 1; i <= 100; i++) {
                EducationalResource resource;
                int type = rand.nextInt(3); // 0 = Book, 1 = Paper, 2 = Video

                switch (type) {
                    case 0 -> resource = new Book();
                    case 1 -> resource = new Paper();
                    default -> resource = new Video();
                }

                resource.setTitle("Resource Title " + i);
                resource.setDescription("Description for resource " + i);
                resource.setAuthors(new String[]{
                        authors[rand.nextInt(authors.length)],
                        rand.nextBoolean() ? authors[rand.nextInt(authors.length)] : null
                });
                resource.setPublicationDate(new Date(120 + rand.nextInt(5), rand.nextInt(12), 1 + rand.nextInt(28)));
                resource.setGenre(genres[rand.nextInt(genres.length)]);
                resource.setUrl(urls[rand.nextInt(urls.length)]);
                resource.setKnowledgeLevel(KnowledgeLevel.values()[rand.nextInt(KnowledgeLevel.values().length)]);

                resourceRepository.save(resource);
                moreResources.add(resource);
            }
        };
	}
}
