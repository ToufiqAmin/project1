package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelTermsCon;
import com.biziitech.mlfm.model.ModelTermsConType;

public interface TermsConRepository extends JpaRepository <ModelTermsCon,Long> {

	@Query("select a from MLFM_TERMSCON a where a.modelTermsConType.shortCode='p'" )
	public List<ModelTermsCon> findPITermsCon();
	
	@Query("select a from MLFM_TERMSCON a  where a.termsConName LIKE CONCAT('%',:typeName,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelTermsCon> findTermsconDetails(@Param("typeName")String typeName, @Param("remarks")String remarks, @Param("status") int status);

	
}
