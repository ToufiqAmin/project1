package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelOrderItemQtySpec;

public interface OrderItemQtySpecRepository extends JpaRepository <ModelOrderItemQtySpec,Long>{
	@Query("select a from MLFM_ORDER_ITEM_QTY_SPEC a where a.modelOrderItemQty.orderItemQtyId=COALESCE(:qty_Id,a.modelOrderItemQty.orderItemQtyId)")
	public List<ModelOrderItemQtySpec> findItemSpecDetails(@Param("qty_Id") Long qtyId);
	
	@Query("select a from MLFM_ORDER_ITEM_QTY_SPEC a where a.activeStatus=1 and a.modelOrderItemQty.orderItemQtyId=COALESCE(:qty_Id,a.modelOrderItemQty.orderItemQtyId)")
	public List<ModelOrderItemQtySpec> findItemSpecDetailsActive(@Param("qty_Id") Long qtyId);
	
	
	
	/*created by sohel rana on 25/03/2019 
	 * 
	 */
	
	
	@Query("select a from MLFM_ORDER_ITEM_QTY_SPEC a where a.orderItemQtySpecId=COALESCE(:specification_Id,a.orderItemQtySpecId)")
	public List<ModelOrderItemQtySpec> findSpecification(@Param("specification_Id") Long id);
	
	
}
