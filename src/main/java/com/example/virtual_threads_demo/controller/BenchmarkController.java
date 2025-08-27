package com.example.virtual_threads_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

// Add to StatsController or create new controller
@RestController
@RequestMapping("/benchmark")
class BenchmarkController {

    @GetMapping("/compare")
    public String compareApproaches(@RequestParam int requestCount) {
        StringBuilder result = new StringBuilder();

        // Test virtual threads
        long vtStart = System.currentTimeMillis();
        testVirtualThreads(requestCount);
        long vtDuration = System.currentTimeMillis() - vtStart;

        // Test platform threads
        long ptStart = System.currentTimeMillis();
        testPlatformThreads(requestCount);
        long ptDuration = System.currentTimeMillis() - ptStart;

        result.append("Virtual Threads: ").append(vtDuration).append("ms\n");
        result.append("Platform Threads: ").append(ptDuration).append("ms\n");
        result.append("Improvement: ").append((ptDuration - vtDuration) * 100.0 / ptDuration).append("%\n");

        return result.toString();
    }

    private void testVirtualThreads(int count) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<?>[] futures = new CompletableFuture[count];
            for (int i = 0; i < count; i++) {
                futures[i] = CompletableFuture.runAsync(this::simulateIoOperation, executor);
            }
            CompletableFuture.allOf(futures).join();
        }
    }

    private void testPlatformThreads(int count) {
        try (var executor = Executors.newFixedThreadPool(200)) {
            CompletableFuture<?>[] futures = new CompletableFuture[count];
            for (int i = 0; i < count; i++) {
                futures[i] = CompletableFuture.runAsync(this::simulateIoOperation, executor);
            }
            CompletableFuture.allOf(futures).join();
        }
    }

    private void simulateIoOperation() {
        try {
            Thread.sleep(100); // Simulate I/O wait
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
