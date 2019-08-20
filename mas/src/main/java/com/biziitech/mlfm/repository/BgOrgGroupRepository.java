package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelOrgGroup;



public interface BgOrgGroupRepository extends JpaRepository <ModelOrgGroup,Long>{
	
	@Query("select a from BG_ORG_GROUP a where a.groupName LIKE CONCAT('%',:groupName,'%') and a.groupCode LIKE CONCAT('%',:groupCode,'%') and a.orgGroupRemarks LIKE CONCAT('%',:orgGroupRemarks,'%') and a.activeStatus=:status")
	public List <ModelOrgGroup> findOrgGroupDetails(@Param("groupName")String groupName, @Param("groupCode")String groupCode,@Param("orgGroupRemarks")String orgGroupRemarks,@Param("status")int status);
	
	@Query("select a from BG_ORG_GROUP a where a.activeStatus=1" )
	public List<ModelOrgGroup> findOrgGroup();
	

}
