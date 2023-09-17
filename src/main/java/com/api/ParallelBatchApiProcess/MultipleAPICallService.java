package com.api.ParallelBatchApiProcess;

import reactor.core.publisher.Flux;

public interface MultipleAPICallService {
    Flux<Response> multipleApiCall();
}
