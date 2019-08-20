package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelTermsCon;
import com.biziitech.mlfm.model.ModelTermsConType;

public interface TermsconTypeRepository extends JpaRepository <ModelTermsConType,Long> {
   
	
	@Query("select a from MLFM_TERMSCON_TYPE a where a.activeStatus=1" )
	public List<ModelTermsConType> findTermsConType();
	
	
	@Query("select a from MLFM_TERMSCON_TYPE a  where a.typeName LIKE CONCAT('%',:typeName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelTermsConType> findTypeDetails(@Param("typeName")String typeName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);

}
