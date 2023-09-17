package com.demo.MutiApicaller;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientApiCaller {

    private final WebClient webClient;

    public WebClientApiCaller(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> callApi(String api) {
        return webClient.get().uri(api).retrieve().bodyToMono(String.class);
    }
}