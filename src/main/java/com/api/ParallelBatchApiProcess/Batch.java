package com.api.ParallelBatchApiProcess;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
// Batch class will hold list of API's
public class Batch {

    private List<String> apis;

    public Batch(List<String> apis) {
        this.apis = apis;
    }

    public List<String> getApis() {
        return apis;
    }

    public void setApis(List<String> apis) {
        this.apis = apis;
    }
}
