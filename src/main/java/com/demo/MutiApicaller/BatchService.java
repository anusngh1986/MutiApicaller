package com.demo.MutiApicaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
// Batch service will execute batches
public class BatchService {

@Autowired
WebClientApiCaller WebClientApiCaller;
    public Flux<Response> callBatch(Batch batch) {
        return Flux.fromIterable(batch.getApis())
                .flatMap(api -> WebClientApiCaller.callApi(api,Response.class))
                .map(response -> new Response(response));
    }
}
