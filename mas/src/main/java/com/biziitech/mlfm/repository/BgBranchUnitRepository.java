package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelBranchUnit;


public interface BgBranchUnitRepository extends JpaRepository<ModelBranchUnit, Long>{
	
	@Query("select a from BG_BRANCH_UNIT a where a.activeStatus=1" )
	public List<ModelBranchUnit> findAllBranchUnit();

	@Query("select a from BG_BRANCH_UNIT a  where a.branchId.branchId=:id")
	public List <ModelBranchUnit> findBranchDetails(@Param("id")Long id);
	
//	@Query("select a from BG_BRANCH_UNIT a  where a.branchId LIKE CONCAT('%',:branchId,'%') and a.orgId.orgId=COALESCE(:orgId,a.orgId.orgId) and a.orgId.orgGroupId.orgGroupId=COALESCE(:orgGroupId,a.orgId.orgGroupId.orgGroupId) and a.activeStatus=1")
//	public List <ModelBranchUnit> findBranchDetails(@Param("branchId")Long branchId,@Param("orgId")Long orgId,@Param("orgGroupId")Long orgGroupId);
}
