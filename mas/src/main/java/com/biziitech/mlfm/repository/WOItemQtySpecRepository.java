package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelWOItemQtySpec;

public interface WOItemQtySpecRepository  extends JpaRepository<ModelWOItemQtySpec,Long> {
	
	
	    @Query(value="select * from mlfm_wo_item_qty_spec a where a.ORDER_ITEM_QTY_ID=COALESCE(:inquiryItemQtyId, a.ORDER_ITEM_QTY_ID)", nativeQuery=true)
		public List<ModelWOItemQtySpec> findWOItemQtySpec(@Param("inquiryItemQtyId") Long inquiryItemQtyId);
	    
	    @Query("select a from MLFM_WO_ITEM_QTY_SPEC a where a.modelOrderItemQty.orderItemQtyId=:orderItemQtyId and a.modelSpecOrder.specId=:specId" )
       public List<ModelWOItemQtySpec> findWOItemQtySpecByOIQtyId(@Param("orderItemQtyId") Long orderItemQtyId,@Param("specId") Long specId);
}
