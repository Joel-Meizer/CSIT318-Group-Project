package CSIT318Project.suggestionService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HttpWebClient {
    private final WebClient webClient;

    public HttpWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

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