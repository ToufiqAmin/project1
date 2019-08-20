package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelPIReceiveMst;

public interface PIReceiveMstRepository extends JpaRepository <ModelPIReceiveMst,Long> {
   
	
	@Query("select a from MLFM_PI_RECEIVE_MST a where a.modelPIMst.pIMstId=COALESCE(:id,a.modelPIMst.pIMstId)")
	public List<ModelPIReceiveMst> findPIReceiveMstById(@Param("id") Long id);	
}
