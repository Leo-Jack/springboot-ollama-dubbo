package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaResponse {
    
    private String model;
    private String response;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("done_reason")
    private String doneReason;
    private boolean done;
    private java.util.List<Integer> context;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDoneReason() {
        return doneReason;
    }

    public void setDoneReason(String doneReason) {
        this.doneReason = doneReason;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public java.util.List<Integer> getContext() {
        return context;
    }

    public void setContext(java.util.List<Integer> context) {
        this.context = context;
    }
}
