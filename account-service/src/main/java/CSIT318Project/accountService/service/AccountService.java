package CSIT318Project.accountService.service;

import CSIT318Project.accountService.repository.AccountRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    AccountService(AccountRepository accountRepository, RestTemplate restTemplate,
                ApplicationEventPublisher applicationEventPublisher) {
        this.accountRepository = accountRepository;
        this.restTemplate = restTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
