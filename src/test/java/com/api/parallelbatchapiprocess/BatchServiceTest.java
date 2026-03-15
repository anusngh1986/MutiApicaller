package com.api.parallelbatchapiprocess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchServiceTest {

    @Mock
    private WebClientApiCaller webClientApiCaller;

    @InjectMocks
    private BatchService batchService;

    @Test
    void callBatch_processes_all_apis() {
        when(webClientApiCaller.callApi(anyString())).thenReturn(Mono.just("{\"result\":\"ok\"}"));

        Batch batch = new Batch(List.of("http://api1.com", "http://api2.com", "http://api3.com"));

        StepVerifier.create(batchService.callBatch(batch, 10))
                .expectNextCount(3)
                .verifyComplete();
    }
}
