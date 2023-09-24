package com.api.ParallelBatchApi;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MultipleAPICallServiceImpl implements MultipleAPICallService {

    private final WebClient webClient;
    private final BatchService batchService;

    public MultipleAPICallServiceImpl(WebClient webClient, BatchService batchService) {
        this.webClient = webClient;
        this.batchService = batchService;
    }

    @SneakyThrows
    @Override
    public Flux<Response> multipleApiCall() {
        // Create a list of APIs to call
        List<String> apis = new ArrayList<>();
        apis.add("https://official-joke-api.appspot.com/random_joke");
        apis.add("https://official-joke-api.appspot.com/random_joke");
        apis.add("https://official-joke-api.appspot.com/random_joke");


        // Split the APIs into batches of 1000
        List<Batch> batches = IntStream.range(0, apis.size())
                .boxed()
                .collect(Collectors.groupingBy(i -> i / 1000))
                .values()
                .stream()
                .map(indices -> new Batch(indices.stream().map(apis::get).collect(Collectors.toList())))
                .collect(Collectors.toList());

        // Create an executor service to call the batches in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Create a list to store the responses from the batches
        List<Future<Flux<Response>>> futures = new ArrayList<>();

        // Iterate over the batches and call them in parallel
        for (Batch batch : batches) {
            futures.add(executorService.submit(() -> batchService.callBatch(batch)));
        }

        // Shut down the executor service
        executorService.shutdown();

        // Create a list to store the merged responses
        List<Flux<Response>> mergedResponses = new ArrayList<>();

        // Wait for all the batches to finish calling and collect the responses
        for (Future<Flux<Response>> future : futures) {
            mergedResponses.add(future.get());
        }

        // Merge the responses from the batches into a single Flux
        return Flux.merge(mergedResponses);
    }
}
