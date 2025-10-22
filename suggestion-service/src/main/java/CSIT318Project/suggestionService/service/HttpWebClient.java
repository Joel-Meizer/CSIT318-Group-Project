package CSIT318Project.suggestionService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HttpWebClient {
    private final WebClient webClient;

    public HttpWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // found how to create a webClient object https://www.geeksforgeeks.org/springboot/spring-boot-webclient-with-example/
    public String GetRESTAsync(String fullUrl) {
        return webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public <T> T GetRESTAsync(String fullUrl, Class<T> responseType) {
        return webClient.get()
                .uri(fullUrl)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}