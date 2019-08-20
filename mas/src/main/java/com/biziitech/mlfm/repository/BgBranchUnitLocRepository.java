package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelBranchUnitLoc;

public interface BgBranchUnitLocRepository extends JpaRepository<ModelBranchUnitLoc, Long>{
	
	
	@Query("select a from BG_BRANCH_UNIT_LOCATION a where a.activeStatus=1" )
	public List<ModelBranchUnitLoc> findAllBranchUnitLoc();
	
	@Query("select a from BG_BRANCH_UNIT_LOCATION a where a.branchUnitId.branchUnitId=COALESCE(:branchUnitId,a.branchUnitId.branchUnitId) and a.activeStatus=1 " )
	public List<ModelBranchUnitLoc> findBranchUnitLocByBranchUnitId(@Param("branchUnitId")Long id);
	

}
