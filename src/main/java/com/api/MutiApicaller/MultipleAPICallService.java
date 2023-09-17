package com.api.MutiApicaller;

import reactor.core.publisher.Flux;

public interface MultipleAPICallService {
    Flux<Response> multipleApiCall();
}
