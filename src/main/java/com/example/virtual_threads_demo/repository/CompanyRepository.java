package com.example.virtual_threads_demo.repository;

import com.example.virtual_threads_demo.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {}

