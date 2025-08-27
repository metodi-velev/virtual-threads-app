package com.example.virtual_threads_demo.service;

// DatabaseService.java

import com.example.virtual_threads_demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Service
public class DatabaseService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveUser(User user) {
        try {
            // Simulate database operation latency
            Thread.sleep(50);
            entityManager.persist(user);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
}