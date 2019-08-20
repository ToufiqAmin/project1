package com.biziitech.mlfm.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelOrgType;


public interface bgOrgTypeRepository extends JpaRepository <ModelOrgType,Long>{
	

	@Query("select a from BG_ORG_TYPE a  where a.orgTypeName LIKE CONCAT('%',:orgTypeName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.orgTypeRemarks LIKE CONCAT('%',:orgTypeRemarks,'%') and a.activeStatus=:status")
	public List <ModelOrgType> findOrgTypeDetails(@Param("orgTypeName")String orgTypeName, @Param("shortCode")String shortCode, @Param("orgTypeRemarks")String orgTypeRemarks, @Param("status") int status);
	
	@Query("select a from BG_ORG_TYPE a where a.activeStatus=1")
	public List <ModelOrgType> findOrgType();

}
