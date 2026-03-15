package com.api.parallelbatchapiprocess;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "api.caller")
public class ApiCallerProperties {
    private int batchSize = 1000;
    private int concurrency = 256;
    private int timeoutSeconds = 5;
    private int maxRetries = 3;
    private List<String> defaultApis;

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public List<String> getDefaultApis() {
        return defaultApis;
    }

    public void setDefaultApis(List<String> defaultApis) {
        this.defaultApis = defaultApis;
    }
}
