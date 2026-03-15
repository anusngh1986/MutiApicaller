package com.api.parallelbatchapiprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class WebClientApiCaller {

    private static final Logger log = LoggerFactory.getLogger(WebClientApiCaller.class);

    private final WebClient webClient;
    private final ApiCallerProperties properties;

    public WebClientApiCaller(WebClient webClient, ApiCallerProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public Mono<String> callApi(String api) {
        return webClient.get()
                .uri(api)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(properties.getTimeoutSeconds()))
                .retryWhen(Retry.backoff(properties.getMaxRetries(), Duration.ofMillis(500))
                        .doBeforeRetry(signal -> log.warn("Retrying API call to {}, attempt {}", api, signal.totalRetries() + 1)))
                .onErrorResume(e -> {
                    log.error("Failed to call API: {}", api, e);
                    return Mono.just("ERROR: " + e.getMessage());
                });
    }
}
