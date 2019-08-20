package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelWOMst;


public interface DesignRepository extends JpaRepository<ModelDesign,Long>{
	@Query("select a from MLFM_DESIGN a where a.modelOrder.orderId=COALESCE(:Inquery_Id,a.modelOrder.orderId)")
	public List<ModelDesign> findDesignFromInquery(@Param("Inquery_Id") Long inqueryId);	
	
	
	@Query(value="select * from MLFM_DESIGN a WHERE a.ACTIVE_STATUS=1 ORDER BY a.design_name", nativeQuery=true)
	public List <ModelDesign> findDesignListActive();
	
	
	//created by sohel rana on 07/04/2019
	
	@Query("select a from MLFM_DESIGN a where a.designId=COALESCE(:designId,a.designId)")
	public List<ModelDesign> findDesign(@Param("designId") Long designId);
	
}
