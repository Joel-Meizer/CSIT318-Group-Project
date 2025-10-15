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

	@Bean
	public CommandLineRunner loadDatabase(ResourceRepository resourceRepository) throws Exception {
        return args -> {
            String[] genres = {
                    "Programming", "AI Research", "Software Development", "Cybersecurity", "Data Science", "Machine Learning",
                    "Cloud Computing", "Web Development", "Mobile Apps", "DevOps",
                    "Entrepreneurship", "Marketing", "Finance", "Accounting", "Economics", "Leadership", "Project Management",
                    "Physics", "Chemistry", "Biology", "Environmental Science", "Mechanical Engineering", "Electrical Engineering", "Civil Engineering",
                    "History", "Philosophy", "Literature", "Art History", "Music Theory", "Creative Writing", "Linguistics",
                    "Psychology", "Sociology", "Political Science", "Anthropology", "Education", "Law", "Ethics",
                    "Medicine", "Nutrition", "Public Health", "Fitness", "Mental Health", "Nursing", "Healthcare Administration",
                    "Productivity", "Mindfulness", "Career Development", "Communication Skills", "Time Management", "Personal Finance",
                    "English", "Spanish", "French", "Mandarin", "German", "Japanese", "Arabic"
            };
            String[] authors = {
                    "Alice Johnson", "Bob Lee", "Dr. Jane Smith", "Carlos Vega", "Dana White", "Emily Zhang",
                    "Frank Harris", "Grace Kim", "Henry Ford", "Isla Nguyen",
                    "Amina El-Sayed", "Rajiv Patel", "Yuki Tanaka", "Liam O'Connor", "Chloe Dubois", "Mateo Silva",
                    "Fatima Al-Mansouri", "Jasper Müller", "Anika Mehta", "Kwame Boateng", "Sofia Rossi", "Tariq Hassan",
                    "Nina Petrova", "Mohammed Idris", "Lucía Fernández", "Noah Cohen", "Zara Bakshi", "Ethan Park",
                    "Leila Haddad", "Sebastian Moreau", "Jun Ho Lee", "Camila Duarte", "Omar Rahman", "Maya Thompson",
                    "Viktor Ivanov", "Thandiwe Ndlovu", "Giulia Bianchi", "Alejandro Cruz", "Mei Lin Chen", "Tyler Brooks"
            };
            String[] urls = {"https://example.com/resource1", "https://example.com/resource2", "https://example.com/resource3", "https://example.com/resource4", "https://example.com/resource5"};

            List<EducationalResource> moreResources = new ArrayList<>();
            Random rand = new Random();

            for (int i = 1; i <= 100; i++) {
                EducationalResource resource = new EducationalResource();
                int type = rand.nextInt(3); // 0 = Book, 1 = Paper, 2 = Video

                switch (type) {
                    case 0 -> resource.setKnowledgeType(KnowledgeType.Book);
                    case 1 -> resource.setKnowledgeType(KnowledgeType.Paper);
                    default -> resource.setKnowledgeType(KnowledgeType.Video);
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
