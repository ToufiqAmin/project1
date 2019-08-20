package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelWOChd;

public interface WorkOrderChdRepository extends JpaRepository<ModelWOChd,Long> {
	
	//@Query("select a from MLFM_WO_CHD a where a.modelWOMst.woMstId=COALESCE(:workOrderId,a.modelWOMst.woMstId)")
	
	//@Query("select a from MLFM_WO_CHD a where a.modelWOMst.wOMstId=COALESCE(:workOrderId,a.modelWOMst.wOMstId)")
	//public List<ModelWOChd> findWOChdByWOMst(@Param("workOrderId") Long workOrderId);
	//@Query("select a from MLFM_WO_CHD a where a.woChdId=COALESCE(:workOrderChdId, a.woChdId)")
	//public List<ModelWOChd> findPIChdByWOChd(@Param("workOrderChdId") Long workOrderChdId);
	

}
