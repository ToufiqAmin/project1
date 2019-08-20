package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.biziitech.mlfm.model.ModelFabricType;



public interface FabricTypeRepository extends JpaRepository<ModelFabricType, Long> {
	
	@Query("select a from MLFM_FABRIC_TYPE a where a.activeStatus=1 order by a.fabricTypeName" )
	public List<ModelFabricType> findFabricType();

}
