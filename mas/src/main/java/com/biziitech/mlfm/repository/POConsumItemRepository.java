package com.biziitech.mlfm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelPO;
import com.biziitech.mlfm.model.ModelPOConsumItem;

public interface POConsumItemRepository extends JpaRepository <ModelPOConsumItem,Long> {
	
	@Query("select p from MLFM_PO_CONSUM_ITEM p where p.modelPOMst.pOMstId=COALESCE(:pOMstId,p.modelPOMst.pOMstId)")
	public List<ModelPOConsumItem> findPOConsumItemDetailsByPOMstId(@Param("pOMstId")Long pOMstId);
	
	
	

}
