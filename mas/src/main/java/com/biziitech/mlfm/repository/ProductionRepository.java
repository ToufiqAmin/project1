package com.biziitech.mlfm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biziitech.mlfm.model.ModelProduction;

public interface ProductionRepository extends JpaRepository <ModelProduction,Long>{
}
