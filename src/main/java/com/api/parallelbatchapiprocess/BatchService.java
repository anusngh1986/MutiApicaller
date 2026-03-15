package com.api.parallelbatchapiprocess;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BatchService {

    private final WebClientApiCaller webClientApiCaller;

    public BatchService(WebClientApiCaller webClientApiCaller) {
        this.webClientApiCaller = webClientApiCaller;
    }

    public Flux<Response> callBatch(Batch batch, int concurrency) {
        return Flux.fromIterable(batch.getApis())
                .flatMap(api -> webClientApiCaller.callApi(api), concurrency)
                .map(Response::new);
    }
}
