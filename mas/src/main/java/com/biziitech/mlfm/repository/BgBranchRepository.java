package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelBranch;


public interface BgBranchRepository extends JpaRepository<ModelBranch, Long>{
	
	@Query("select a from BG_BRANCH a where a.activeStatus=1" )
	public List<ModelBranch> findBranch();
	
	@Query("select a from BG_BRANCH a where a.orgId.orgId=COALESCE(:orgId,a.orgId.orgId) and a.activeStatus=1")
	public List<ModelBranch> findBranchByOrg(@Param("orgId") Long id);
	
	
	

}
