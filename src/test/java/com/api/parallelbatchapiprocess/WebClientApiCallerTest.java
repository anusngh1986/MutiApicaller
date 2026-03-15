package com.api.parallelbatchapiprocess;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class WebClientApiCallerTest {

    private MockWebServer mockWebServer;
    private WebClientApiCaller webClientApiCaller;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.create(mockWebServer.url("/").toString());
        ApiCallerProperties properties = new ApiCallerProperties();
        properties.setTimeoutSeconds(5);
        properties.setMaxRetries(1);
        webClientApiCaller = new WebClientApiCaller(webClient, properties);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void callApi_success() {
        mockWebServer.enqueue(new MockResponse().setBody("{\"ip\":\"1.2.3.4\"}").setResponseCode(200));
        String url = mockWebServer.url("/test").toString();

        StepVerifier.create(webClientApiCaller.callApi(url))
                .assertNext(response -> assertTrue(response.contains("1.2.3.4")))
                .verifyComplete();
    }

    @Test
    void callApi_error_returns_fallback() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        mockWebServer.enqueue(new MockResponse().setResponseCode(500)); // retry
        String url = mockWebServer.url("/fail").toString();

        StepVerifier.create(webClientApiCaller.callApi(url))
                .assertNext(response -> assertTrue(response.startsWith("ERROR:")))
                .verifyComplete();
    }
}
