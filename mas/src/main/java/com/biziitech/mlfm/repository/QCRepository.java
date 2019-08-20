package com.biziitech.mlfm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.model.ModelQC;

public interface QCRepository extends JpaRepository<ModelQC, Long>{
	
	
@Query("select a from MLFM_QC a where a.modelProduction.productionId=:id and a.activeStatus=1")
public List<ModelQC> findQCById(@Param("id") Long id);

}
