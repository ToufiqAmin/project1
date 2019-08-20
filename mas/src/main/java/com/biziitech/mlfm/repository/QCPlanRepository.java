package com.biziitech.mlfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.biziitech.mlfm.model.ModelQCPlan;



public interface QCPlanRepository extends JpaRepository<ModelQCPlan, Long>{
	

	
	
	@Query("select a from MLFM_QC_PLAN a where a.qCPlanId=:Id")
	public Optional<ModelQCPlan> findQCPlanById(@Param("Id")Long id);
		
	
	
	
	
	
	
}
