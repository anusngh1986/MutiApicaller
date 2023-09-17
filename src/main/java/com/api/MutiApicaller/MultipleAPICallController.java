package com.api.MutiApicaller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MultipleAPICallController {

    private final MultipleAPICallService multipleAPICallService;

    public MultipleAPICallController(MultipleAPICallService multipleAPICallService) {
        this.multipleAPICallService = multipleAPICallService;
    }

    @GetMapping("/multiple-api-call")
    public Flux<Response> multipleApiCall() {
        return multipleAPICallService.multipleApiCall();
    }
}