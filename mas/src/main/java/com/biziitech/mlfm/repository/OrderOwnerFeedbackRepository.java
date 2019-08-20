package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;

public interface OrderOwnerFeedbackRepository extends JpaRepository<ModelOrderOwnerFeedback,Long>{

	@Query("select a from MLFM_ORDER_OWNER_FEEDBACK a where a.activeStatus=1 and a.modelOrderItemQty.orderItemQtyId=:id")
	public List<ModelOrderOwnerFeedback> findFeddbackById(@Param("id") Long id);
	
	
	@Query("select a from MLFM_ORDER_OWNER_FEEDBACK a where a.activeStatus=1 and a.orderOwnerFeedbackId=:id")
	public List<ModelOrderOwnerFeedback> getFeddbackById(@Param("id") Long id);
	
}
