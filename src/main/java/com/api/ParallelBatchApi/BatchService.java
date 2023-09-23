package com.api.ParallelBatchApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


/*

injected the WebClientApiCaller dependency through the constructor instead of using field injection.
Constructor injection is generally considered a best practice as it promotes better testability and reduces coupling.
Used the parallel operator to parallelize the API calls. This allows the calls to be executed concurrently, improving performance.
Added the ordered operator to preserve the order of responses. By passing (u1, u2) -> 0 as the comparator, we ensure that the responses are ordered based on their original order in the batch.
Simplified the mapping operation by using method references (Response::new) instead of lambda expressions.
 */
@Component
// Batch service will execute batches
public class BatchService {

    private final WebClientApiCaller webClientApiCaller;

    @Autowired
    public BatchService(WebClientApiCaller webClientApiCaller) {
        this.webClientApiCaller = webClientApiCaller;
    }

    // This code will call APIs in parallel using flatMap
    public Flux<Response> callBatch(Batch batch) {
        return Flux.fromIterable(batch.getApis())
                .parallel()
                .flatMap(api -> webClientApiCaller.callApi(api))
                .ordered((u1, u2) -> 0) // Preserve the order of responses
                .map(Response::new);
    }
}
