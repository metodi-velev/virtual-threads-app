package com.example.virtual_threads_demo.controller;

// ApiController.java

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@RestController
class ApiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/api/sequential")
    public String sequentialCalls(@RequestParam int count) {
        long start = System.currentTimeMillis();

        for (int i = 1; i <= count; i++) {
            String response = restTemplate.getForObject(
                    "https://jsonplaceholder.typicode.com/users/" + i, String.class);
        }

        long duration = System.currentTimeMillis() - start;
        return "Sequential calls: " + count + " in " + duration + "ms";
    }

    @GetMapping("/api/virtual-threads")
    public String virtualThreadCalls(@RequestParam int count) {
        long start = System.currentTimeMillis();

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture<?>[] futures = new CompletableFuture[count];

            for (int i = 1; i <= count; i++) {
                final int userId = i;
                futures[i-1] = CompletableFuture.runAsync(() -> {
                    restTemplate.getForObject(
                            "https://jsonplaceholder.typicode.com/users/" + userId, String.class);
                }, executor);
            }

            CompletableFuture.allOf(futures).join();
        }

        long duration = System.currentTimeMillis() - start;
        return "Virtual threads calls: " + count + " in " + duration + "ms";
    }
}
