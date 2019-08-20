package com.biziitech.mlfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelModule;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelOrderItem;

public interface ModuleRepository extends JpaRepository <ModelModule,Long> {
	
	@Query("select a from BG_MODULE a where a.activeStatus=1 order by a.moduleName" )
	public List<ModelModule> findModule();
	
	@Query("select a from BG_MODULE a where a.modelSystem.systemId=COALESCE(:System_Id,a.modelSystem.systemId)")
	public List<ModelModule> findModuleFromSystem(@Param("System_Id") Long systemId);
	
	
	@Query("select a from BG_MODULE a where a.moduleId=COALESCE(:moduleId,a.moduleId)")
	public Optional<ModelModule> findModule(@Param("moduleId") Long moduleId);
	
	
}
