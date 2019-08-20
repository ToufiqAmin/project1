package com.biziitech.mlfm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.model.ModelOrderItemQty;

public interface OrderItemQtyRepository extends JpaRepository<ModelOrderItemQty,Long>{
	@Query("select a from MLFM_ORDER_ITEM_QTY a where a.modelOrderItem.itemOrderId=COALESCE(:ItemOrder_Id,a.modelOrderItem.itemOrderId)")
	public List<ModelOrderItemQty> findItemQtyDetails(@Param("ItemOrder_Id") Long orderId);	
	
	
	/*created by sohel rana on 28/03/2019
	 * 
	 */
	@Query("select a from MLFM_ORDER_ITEM_QTY a where a.orderItemQtyId=COALESCE(:id,a.orderItemQtyId)")
	public List<ModelOrderItemQty> findItemQtyDetailsForEdit(@Param("id") Long id);	
	
	
	//created by sohel rana on 04/04/2019
	
	/*
	
	@Modifying
	@Query("update MLFM_ORDER_ITEM_QTY a set a.modelDesign.designId = designId where a.orderItemQtyId = orderItemQtyId")
	int updateOrderItemQty(@Param("designId") Long designId, 
	@Param("orderItemQtyId") Long orderItemQtyId);
	
	*/
	//created by sohel rana on 08/04/2019
	
	@Query("select a from MLFM_ORDER_ITEM_QTY a where a.modelOrderItem.modelOrder.modelOrderOwnerType.orderOwnerTypeId=coalesce(:typeId,a.modelOrderItem.modelOrder.modelOrderOwnerType.orderOwnerTypeId) and a.modelOrderItem.modelOrder.modelOrderOwner.orderOwnerId=coalesce(:owner,a.modelOrderItem.modelOrder.modelOrderOwner.orderOwnerId) and a.modelOrderItem.modelOrder.modelOrderOwner.orderOwnerId=coalesce(:ultimateOwner,a.modelOrderItem.modelOrder.modelOrderOwner.orderOwnerId) and a.modelOrderItem.modelOrder.orderId=coalesce(:orderId,a.modelOrderItem.modelOrder.orderId) and a.modelOrderItem.modelOrder.enteredBy.userId=coalesce(:user,a.modelOrderItem.modelOrder.enteredBy.userId) and a.modelOrderItem.modelOrder.orderDate BETWEEN coalesce(:inq_st,a.modelOrderItem.modelOrder.orderDate) AND coalesce(:inq_ed,a.modelOrderItem.modelOrder.orderDate) and a.modelOrderItem.modelOrder.activeStatus=:status and a.modelDesign.designId is not null")
	public List<ModelOrderItemQty> findInquiryMappedDetails(@Param("typeId")Long typeId,@Param("owner") Long owner,@Param("ultimateOwner")Long ultimateOwner,@Param("orderId")Long orderId,@Param("user")Long user,@Param("inq_st")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_st,@Param("inq_ed")@DateTimeFormat(pattern="yyyy-MM-dd")Date inq_ed,@Param("status")int status);
	
	
}
