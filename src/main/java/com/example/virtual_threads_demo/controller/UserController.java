package com.example.virtual_threads_demo.controller;

// UserController.java

import com.example.virtual_threads_demo.model.User;
import com.example.virtual_threads_demo.service.DatabaseService;
import com.example.virtual_threads_demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DatabaseService databaseService;

    public UserController(UserService userService, DatabaseService databaseService) {
        this.userService = userService;
        this.databaseService = databaseService;
    }

    @PostMapping("/process/{userId}")
    public CompletableFuture<String> processUser(@PathVariable int userId) {
        return userService.processUserData(userId)
                .thenApply(user -> "Processed user: " + user.getName());
    }

    @PostMapping("/process-batch")
    public CompletableFuture<String> processUsersBatch(@RequestParam int count) {
        long startTime = System.currentTimeMillis();

        CompletableFuture<?>[] futures = new CompletableFuture[count];
        for (int i = 1; i <= count; i++) {
            futures[i-1] = userService.processUserData(i);
        }

        return CompletableFuture.allOf(futures)
                .thenApply(v -> {
                    long duration = System.currentTimeMillis() - startTime;
                    return String.format("Processed %d users in %d ms", count, duration);
                });
    }

    @GetMapping
    public List<User> getAllUsers() {
        return databaseService.getAllUsers();
    }
}
