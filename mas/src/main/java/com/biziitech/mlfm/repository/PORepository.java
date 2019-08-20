package com.biziitech.mlfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.biziitech.mlfm.model.ModelPO;

public interface PORepository extends JpaRepository<ModelPO,Long> {
	
	@Query("select a from MLFM_PO a where a.pOId=:pOId")
	public ModelPO findPOById(@Param("pOId")Long pOId);
	
	
	@Query("select a from MLFM_PO a where a.modelDesign.designId=COALESCE(:designId,a.modelDesign.designId)")
	public ModelPO findPOByDesignId(@Param("designId")Long designId);
	//public Optional<ModelPO> findPOByDesignId(@Param("designId")Long designId);
	
}
