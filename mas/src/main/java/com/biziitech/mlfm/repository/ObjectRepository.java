package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelObject;

public interface ObjectRepository extends JpaRepository <ModelObject,Long> {
	
	@Query("select a from BG_OBJECT a where a.modelModule.moduleId=COALESCE(:Module_Id,a.modelModule.moduleId)")
	public List<ModelObject> findModuleFromSystem(@Param("Module_Id") Long moduleId);
	
	
	@Query("select a from BG_OBJECT a where a.activeStatus=1 ORDER BY a.objectName")
	public List<ModelObject> findObject();

}
