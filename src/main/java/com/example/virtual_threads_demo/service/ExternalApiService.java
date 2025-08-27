package com.example.virtual_threads_demo.service;

// ExternalApiService.java

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    public CompletableFuture<String> fetchUserDataAsync(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Simulate API call latency
                Thread.sleep(100);
                String url = "https://jsonplaceholder.typicode.com/users/" + userId;
                return restTemplate.getForObject(url, String.class);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> fetchUserPostsAsync(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(150);
                String url = "https://jsonplaceholder.typicode.com/posts?userId=" + userId;
                return restTemplate.getForObject(url, String.class);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<String> fetchUserCommentsAsync(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
                String url = "https://jsonplaceholder.typicode.com/comments?postId=" + userId;
                return restTemplate.getForObject(url, String.class);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
