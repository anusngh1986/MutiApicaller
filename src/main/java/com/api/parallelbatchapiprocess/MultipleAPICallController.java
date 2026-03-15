package com.api.parallelbatchapiprocess;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MultipleAPICallController {

    private final MultipleAPICallService multipleAPICallService;

    public MultipleAPICallController(MultipleAPICallService multipleAPICallService) {
        this.multipleAPICallService = multipleAPICallService;
    }

    @GetMapping("/multiple-api-call")
    public Flux<Response> multipleApiCallDefault() {
        return multipleAPICallService.multipleApiCall();
    }

    @PostMapping("/multiple-api-call")
    public Flux<Response> multipleApiCall(@RequestBody List<String> apis) {
        return multipleAPICallService.callApis(apis);
    }
}
