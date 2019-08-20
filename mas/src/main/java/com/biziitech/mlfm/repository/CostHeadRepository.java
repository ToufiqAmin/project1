package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelCostHead;

public interface CostHeadRepository extends JpaRepository<ModelCostHead,Long> {
	
	@Query("select a from MLFM_COST_HEAD a where a.activeStatus=1")
	public List<ModelCostHead> findCostHead();
	
	@Query("select a from MLFM_COST_HEAD a where a.costHeadId=:id")
	public List<ModelCostHead> findCostHeadById(@Param("id") Long id);

}
