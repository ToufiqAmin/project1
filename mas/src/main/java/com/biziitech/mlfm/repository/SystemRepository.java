package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.bg.model.ModelSystem;
import com.biziitech.mlfm.model.ModelUOM;

public interface SystemRepository extends JpaRepository <ModelSystem,Long> {

	
	@Query("select a from BG_SYSTEM a where a.activeStatus=1 order by a.systemName" )
	public List<ModelSystem> findSystem();
	
	@Query("select a from BG_SYSTEM a  where a.systemName LIKE CONCAT('%',:systemName,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelSystem> findSystemDetails(@Param("systemName")String systemName, @Param("remarks")String remarks, @Param("status") int status);

	
	
}
