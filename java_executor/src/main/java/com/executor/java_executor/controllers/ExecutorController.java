package com.executor.java_executor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.executor.java_executor.dtos.requests.ExecuteCodeRequest;
import com.executor.java_executor.dtos.responses.ExecuteCodeResponse;
import com.executor.java_executor.services.ExecutorService;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/executor")
public class ExecutorController {
    @Autowired
    private ExecutorService executorService;
    
    @PostMapping("/run")
    public ResponseEntity<ExecuteCodeResponse> run(@RequestBody ExecuteCodeRequest executeCodeRequest){
        try {
            CompletableFuture<String> data = this.executorService.run(executeCodeRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new ExecuteCodeResponse(data.get()));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ExecuteCodeResponse("Error during code execution: " + e.getMessage()));
        }
    }
    
}
