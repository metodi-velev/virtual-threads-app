package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {}

