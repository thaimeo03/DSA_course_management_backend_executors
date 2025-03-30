package com.executor.java_executor.dtos.responses;

public class ExecuteCodeResponse {
    private String message;

    public ExecuteCodeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
