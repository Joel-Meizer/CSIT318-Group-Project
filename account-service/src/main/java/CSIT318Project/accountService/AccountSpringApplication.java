package CSIT318Project.accountService;

import CSIT318Project.bookService.model.Book;
import CSIT318Project.bookService.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AccountSpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(AccountSpringApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner loadDatabase(AccountRepository accountRepository) throws Exception {
		return args -> {
			Book entry = new Book();
			entry.setIsbn("0-684-84328-5");
			entry.setTitle("Introduction to Software Engineering");
			entry.addAvailableLibrary(2500L);
			entry.addAvailableLibrary(2522L);
			accountRepository.save(entry);
			System.out.println(accountRepository.findById("0-684-84328-5").orElseThrow());

			Book entry1 = new Book();
			entry1.setIsbn("93-86954-21-4");
			entry1.setTitle("Domain Drive Design");
			entry1.addAvailableLibrary(2522L);
			accountRepository.save(entry1);
			System.out.println(accountRepository.findById("93-86954-21-4").orElseThrow());
		};
	}

}
