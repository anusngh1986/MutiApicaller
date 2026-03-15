package com.api.parallelbatchapiprocess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest(MultipleAPICallController.class)
class MultipleAPICallControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MultipleAPICallService multipleAPICallService;

    @Test
    void getEndpoint_returns_responses() {
        when(multipleAPICallService.multipleApiCall())
                .thenReturn(Flux.just(new Response("{\"ip\":\"1.2.3.4\"}")));

        webTestClient.get().uri("/api/multiple-api-call")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Response.class)
                .hasSize(1);
    }
}
