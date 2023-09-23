package com.api.ParallelBatchApi;

import reactor.core.publisher.Flux;

public interface MultipleAPICallService {
    Flux<Response> multipleApiCall();
}
