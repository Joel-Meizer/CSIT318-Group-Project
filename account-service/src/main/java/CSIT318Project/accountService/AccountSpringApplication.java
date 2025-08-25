package CSIT318Project.accountService;

import CSIT318Project.accountService.repository.AccountRepository;
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
		};
	}

}
