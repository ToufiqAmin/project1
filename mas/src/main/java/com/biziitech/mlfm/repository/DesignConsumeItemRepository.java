package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelOrder;

public interface DesignConsumeItemRepository extends JpaRepository<ModelDesignConsumItem,Long>{
	
	
	//@Query("select a from mlfm_design_consum_item a where a.modelDesign.designId=:id")
	//@Query(value="select * from MLFM_DESIGN_CONSUM_ITEM a where a.design_id=:id", nativeQuery=true)
	@Query("select a from MLFM_DESIGN_CONSUM_ITEM a where a.modelDesign.designId=COALESCE(:id,a.modelDesign.designId)")
    public List<ModelDesignConsumItem> getDesignConsumItemByDesignId(@Param("id")Long id);
	


	
}
