package com.api.ParallelBatchApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/multiple-api-call")
public class MultipleAPICallController {

    private final MultipleAPICallService multipleAPICallService;

    public MultipleAPICallController(MultipleAPICallService multipleAPICallService) {
        this.multipleAPICallService = multipleAPICallService;
    }

    @GetMapping
    public Flux<Response> multipleApiCall() {
        return multipleAPICallService.multipleApiCall();
    }
}