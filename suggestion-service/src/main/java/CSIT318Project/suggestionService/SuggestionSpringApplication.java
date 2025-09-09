package CSIT318Project.suggestionservice;

import CSIT318Project.bookService.model.Book;
import CSIT318Project.bookService.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
	public CommandLineRunner loadDatabase(SuggestionRepository suggestionRepository) throws Exception {
		return args -> {};
	}

}
