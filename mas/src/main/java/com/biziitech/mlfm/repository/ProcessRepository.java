package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelProcess;

public interface ProcessRepository extends JpaRepository <ModelProcess,Long> {

	
}
