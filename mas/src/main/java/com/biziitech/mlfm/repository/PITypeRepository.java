package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelPIType;
import com.biziitech.mlfm.model.ModelUOM;

public interface PITypeRepository extends JpaRepository <ModelPIType,Long>{
	
	@Query("select a from MLFM_PI_TYPE a where a.activeStatus=1")
	public List<ModelPIType> findPITypes();
	
	@Query("select a from MLFM_PI_TYPE a  where a.typeName LIKE CONCAT('%',:typeName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelPIType> findTypeDetails(@Param("typeName")String typeName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);


}
