package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.biziitech.mlfm.bg.model.ModelObjectType;

public interface ObjectTypeRepository extends JpaRepository <ModelObjectType,Long> {
	
	@Query("select a from BG_OBJECT_TYPE a where a.activeStatus=1 order by a.typeName" )
	public List<ModelObjectType> findObjectType();
	
	@Query("select a from BG_OBJECT_TYPE a  where a.typeName LIKE CONCAT('%',:typeName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelObjectType> findObjectTypeDetails(@Param("typeName")String typeName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);


}
