package com.executor.java_executor.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.executor.java_executor.dtos.requests.ExecuteCodeRequest;

@Service
public class ExecutorService {
    // 1. Create temp directory
    // 2. Create file path with userId and problemId
    // 3. Write code to file
    // 4. Run code using ProcessBuilder
    public CompletableFuture<String> run(ExecuteCodeRequest executeCodeRequest) throws Exception {
        String code = executeCodeRequest.getCode();
        String userId = executeCodeRequest.getUserId();
        String problemId = executeCodeRequest.getProblemId();
        // 1
        String dirPath = "temp" + File.separator + userId + "_" + problemId;
        Path tempDirPath = Paths.get(dirPath);

        try {
            Files.createDirectories(tempDirPath);
        } catch (Exception e) {
            throw new RuntimeException("Error creating temp directory: " + e.getMessage());
        }

        // 2
        String fileName = "Main.java";
        Path filePath = tempDirPath.resolve(fileName);
        File codeFile = new File(filePath.toString());

        // 3
        try (FileWriter writer = new FileWriter(codeFile)) {
            writer.write(code);
        } catch (Exception e) {
            throw new RuntimeException("Error writing code to file: " + e.getMessage());
        }

        // 4
        ProcessBuilder processBuilder = new ProcessBuilder("java", filePath.toString());
        processBuilder.directory(new File(System.getProperty("user.dir")));

        return CompletableFuture.supplyAsync(() -> {
            StringBuilder result = new StringBuilder();
            try {
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                
                // Get stdout and stderr
                result.append(new String(process.getInputStream().readAllBytes()));
                result.append(new String(process.getErrorStream().readAllBytes()));

                // Return result or error output
                if (exitCode != 0) {
                    throw new RuntimeException("Error executing code: " + result.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("Execution failed: " + e.getMessage());
            } finally {
                // Delete the file after execution
                try {
                    Files.deleteIfExists(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result.toString().trim();
        });
    }
}
