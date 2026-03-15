package com.api.parallelbatchapiprocess;

public class Response {

    private String response;
    private boolean success;

    public Response() {
    }

    public Response(String response) {
        this.response = response;
        this.success = !response.startsWith("ERROR:");
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
