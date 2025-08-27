package com.example.virtual_threads_demo.service;

// UserService.java

import com.example.virtual_threads_demo.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    private final ExternalApiService apiService;
    private final DatabaseService databaseService;

    public UserService(ExternalApiService apiService, DatabaseService databaseService) {
        this.apiService = apiService;
        this.databaseService = databaseService;
    }

    @Async
    public CompletableFuture<User> processUserData(int userId) {
        try {
            // Concurrent API calls using virtual threads
            var userDataFuture = apiService.fetchUserDataAsync(userId);
            var postsFuture = apiService.fetchUserPostsAsync(userId);
            var commentsFuture = apiService.fetchUserCommentsAsync(userId);

            // Wait for all completions
            CompletableFuture.allOf(userDataFuture, postsFuture, commentsFuture).join();

            // Process results (simplified)
            String userData = userDataFuture.get();
            String posts = postsFuture.get();
            String comments = commentsFuture.get();

            // Parse and create user object (simplified)
            User user = parseUserData(userData, posts, comments);

            // Save to database
            databaseService.saveUser(user);

            return CompletableFuture.completedFuture(user);

        } catch (Exception e) {
            throw new RuntimeException("Failed to process user data", e);
        }
    }

    private User parseUserData(String userData, String posts, String comments) {
        // Simplified parsing - in real application, use proper JSON parsing
        return new User("User " + System.currentTimeMillis(),
                "user@example.com", "Company");
    }
}
