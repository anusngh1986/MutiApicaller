package com.api.ParallelBatchApi;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static java.util.stream.Collectors.toList;

@Service
public class MultipleAPICallServiceImpl implements MultipleAPICallService {

    private final WebClient webClient;
    private final BatchService batchService;
    private final int batchSize = 1000;
    private final int numThreads = 3;

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

        // Split the APIs into batches
        List<Batch> batches = new ArrayList<>();
        for (int i = 0; i < apis.size(); i += batchSize) {
            batches.add(new Batch(apis.subList(i, Math.min(i + batchSize, apis.size()))));
        }

        // Create a thread pool with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Submit tasks to the executor service and collect the futures
        List<Future<Flux<Response>>> futures = new ArrayList<>(batches.size());
        for (Batch batch : batches) {
            futures.add(executorService.submit(() -> batchService.callBatch(batch)));
        }

        // Shutdown the executor service after all tasks are completed
        executorService.shutdown();

        // Merge the responses from the batches into a single Flux
        return Flux.merge(futures.stream().map(Future::get).collect(toList()));
    }
}
