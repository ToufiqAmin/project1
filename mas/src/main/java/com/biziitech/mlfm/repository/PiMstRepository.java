package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelPIMst;

public interface PiMstRepository extends JpaRepository <ModelPIMst,Long> {
	
	    @Query("select a from MLFM_PI_MST a where a.pIMstId=COALESCE(:id,a.pIMstId)")
		public List<ModelPIMst> findPIMstById(@Param("id") Long id);

}
