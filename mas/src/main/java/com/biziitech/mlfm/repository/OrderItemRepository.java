package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;

public interface OrderItemRepository extends JpaRepository<ModelOrderItem,Long> {
	@Query("select a from MLFM_ORDER_ITEM a where a.modelOrder.orderId=COALESCE(:order_Id,a.modelOrder.orderId)")
	public List<ModelOrderItem> findItemDetails(@Param("order_Id") Long orderId);	
	
	
	/*created by sohel rana on 27/03/2019
	  
	 */
	
	@Query("select a from MLFM_ORDER_ITEM a where a.itemOrderId=COALESCE(:id,a.itemOrderId)")
	public List<ModelOrderItem> findOrderItem(@Param("id") Long id);
	
	
	//created by sohel rana on 22/04/2019
	
	@Query("select a from MLFM_ORDER_ITEM a where a.modelOrder.orderId=COALESCE(:order_Id,a.modelOrder.orderId) and a.listModelItem.itemId=COALESCE(:item_Id,a.listModelItem.itemId) ")
	public List<ModelOrderItem> checkOrderItem(@Param("order_Id") Long orderId,@Param("item_Id") Long itemId);	
	
	
}
