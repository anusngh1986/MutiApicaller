package com.api.ParallelBatchApiProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
@Component
// Batch service will execute batches
public class BatchService {

    @Autowired
    WebClientApiCaller WebClientApiCaller;

    //This code will call 256 API in parallel,flatmap call 256 API in parallel
    public Flux<Response> callBatch(Batch batch) {
        return Flux.fromIterable(batch.getApis())
                .flatMap(api -> WebClientApiCaller.callApi(api))
                .map(response -> new Response(response));
    }
}
