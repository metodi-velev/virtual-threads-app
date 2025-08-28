package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {}
