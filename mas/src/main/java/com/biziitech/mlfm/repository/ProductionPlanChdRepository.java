package com.biziitech.mlfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelProductionPlanChd;


public interface ProductionPlanChdRepository extends JpaRepository<ModelProductionPlanChd ,Long> {
	
	@Query("select a from MLFM_PRODUCTION_PLAN_CHD a  where a.modelProductionPlanMst.productionPlanMstId=COALESCE(:productionPlanMstId,a.modelProductionPlanMst.productionPlanMstId)")
	public List<ModelProductionPlanChd> findProductionPlanChdListByMstId(@Param("productionPlanMstId")Long productionPlanMstId);
    
	
	

}
