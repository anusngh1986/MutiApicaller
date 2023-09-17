package com.api.MutiApicaller;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientApiCaller {

    private final WebClient webClient;

    public WebClientApiCaller(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> callApi(String api) {
        return webClient.get().uri(api).retrieve().bodyToMono(String.class);
    }
}