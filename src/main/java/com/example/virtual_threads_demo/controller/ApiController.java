package com.example.virtual_threads_demo.controller;

// ApiController.java

import com.example.virtual_threads_demo.dto.UserDto;
import com.example.virtual_threads_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Transactional
@RestController
class ApiController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserService userService;

    @GetMapping("/api/sequential")
    public ResponseEntity<Map<String, Object>> sequentialCalls(@RequestParam int count) {
        long start = System.currentTimeMillis();

        List<UserDto> resultsList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            UserDto response = restTemplate.getForObject(
                    "https://jsonplaceholder.typicode.com/users/" + i, UserDto.class);
            resultsList.add(response);
        }

        long duration = System.currentTimeMillis() - start;

        String message = "Sequential calls: " + count + " in " + duration + "ms";
        System.out.println(message);

        userService.save(resultsList);

        // Create response with timing info and results
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("performance", Map.of(
                "message", message,
                "count", count,
                "durationMs", duration,
                "threadType", "sequential"
        ));
        response.put("users", resultsList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/virtual-threads")
    public ResponseEntity<Map<String, Object>> virtualThreadCalls(@RequestParam int count) {
        long start = System.currentTimeMillis();

        List<UserDto> results;
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            @SuppressWarnings("unchecked")
            CompletableFuture<UserDto>[] futures = new CompletableFuture[count];

            for (int i = 1; i <= count; i++) {
                final int userId = i;
                futures[i - 1] = CompletableFuture.supplyAsync(() ->
                        restTemplate.getForObject(
                                "https://jsonplaceholder.typicode.com/users/" + userId,
                                UserDto.class
                        ), executor);
            }

            results = Arrays.stream(futures)
                    .map(CompletableFuture::join)
                    .toList();
        }

        long duration = System.currentTimeMillis() - start;
        String message = "Virtual threads fetched " + count + " users in " + duration + " ms";
        System.out.println(message);

        userService.save(results);

        // Create response with timing info and results
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("performance", Map.of(
                "message", message,
                "count", count,
                "durationMs", duration,
                "threadType", "virtual"
        ));
        response.put("users", results);

        return ResponseEntity.ok(response);
    }
}
