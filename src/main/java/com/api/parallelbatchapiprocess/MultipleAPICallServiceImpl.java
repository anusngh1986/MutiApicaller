package com.api.parallelbatchapiprocess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MultipleAPICallServiceImpl implements MultipleAPICallService {

    private static final Logger log = LoggerFactory.getLogger(MultipleAPICallServiceImpl.class);

    private final BatchService batchService;
    private final ApiCallerProperties properties;

    public MultipleAPICallServiceImpl(BatchService batchService, ApiCallerProperties properties) {
        this.batchService = batchService;
        this.properties = properties;
    }

    @Override
    public Flux<Response> multipleApiCall() {
        List<String> apis = properties.getDefaultApis();
        return callApis(apis);
    }

    @Override
    public Flux<Response> callApis(List<String> apis) {
        log.info("Calling {} APIs with batch size {} and concurrency {}",
                apis.size(), properties.getBatchSize(), properties.getConcurrency());

        return Flux.fromIterable(apis)
                .buffer(properties.getBatchSize())
                .flatMap(batchApis -> {
                    Batch batch = new Batch(batchApis);
                    return batchService.callBatch(batch, properties.getConcurrency());
                });
    }
}
