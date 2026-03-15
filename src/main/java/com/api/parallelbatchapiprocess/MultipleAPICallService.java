package com.api.parallelbatchapiprocess;

import reactor.core.publisher.Flux;
import java.util.List;

public interface MultipleAPICallService {
    Flux<Response> multipleApiCall();
    Flux<Response> callApis(List<String> apis);
}
