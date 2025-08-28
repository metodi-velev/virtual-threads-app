package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.model.GeoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoRepository extends JpaRepository<GeoEntity, Long> {}

