package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.biziitech.mlfm.model.ModelSpec;

public interface SpecRepository extends JpaRepository<ModelSpec,Long> {
	
	
	@Query("select a from MLFM_SPEC a where a.activeStatus=1 order by a.specName")
	public List <ModelSpec> findAllActive();
	
}

