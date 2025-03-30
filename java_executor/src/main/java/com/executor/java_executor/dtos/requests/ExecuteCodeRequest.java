package com.executor.java_executor.dtos.requests;

public class ExecuteCodeRequest {
    private String code;
    private String userId;
    private String problemId;

    public String getCode() {
        return code;
    }

    public String getUserId() {
        return userId;
    }

    public String getProblemId() {
        return problemId;
    }
}
