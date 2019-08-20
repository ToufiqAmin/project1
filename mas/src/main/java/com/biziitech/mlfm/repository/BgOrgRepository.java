package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelOrg;



public interface BgOrgRepository extends JpaRepository <ModelOrg,Long>{
	
	@Query("select a from BG_ORG a where a.activeStatus=1 order by orgName" )
	public List<ModelOrg> findOrg();
	
	
	@Query("select a from BG_ORG a where a.orgGroupId.orgGroupId=COALESCE(:orgGroupId,a.orgGroupId.orgGroupId) and a.activeStatus=1")
	public List<ModelOrg> findOrgByOrgGroup(@Param("orgGroupId") Long id);
	

}
