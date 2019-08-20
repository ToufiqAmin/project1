
package com.biziitech.mlfm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.model.ModelWOMst;



public interface WorkOrderRepository extends JpaRepository<ModelWOMst,Long> {
	
//	@Query("select a from MLFM_ORDER_ITEM_QTY a  where a.machineShiftName LIKE CONCAT('%',:machineShiftName,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
//	public List <ModelOrderItemQty> findInquiryData(@Param("machineShiftName")String shiftName, @Param("remarks")String remarks, @Param("status") int status);
//  
	 
	   
	
	/*
	 * created by:
	 * Date:
	 * 
	 * Purpose:
	 * 
	 * The following query will return inquiry-item-qty records for work orders which have not included at workorder tables yet -> 
	 * (mlfm_wo_mst and mlfm_wo_chd)
	 * The query will be selected when wo checkbox will be unchecked and status will be blank.
	 * 
	 *  mlfm_order a
	 *  mlfm_order_item b
	 *  mlfm_order_item_Qty c
	 *  mlfm_item d
	 *  mlfm_uom e
	 *  mlfm_order_owner f
	 * 
	 * 
	 */
//
//	@Query(value=" select * from (  select  c.order_item_qty_id,a.order_date,d.item_name,c.item_qty,e.uom_id,e.uom_name,b.item_order_id,b.item_id,"
//			+ " a.entered_by, h.user_cluster_id from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id inner join "
//			+ " mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id "
//			+ " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id"
//			+"  inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id "
//			+ "  and a.active_status=1 and b.active_status=1 and c.active_status=1 and not exists (SELECT 1 FROM mlfm_wo_mst p inner join "
//			+ " mlfm_wo_chd q on p.wo_mst_id="
//			+ " q.wo_mst_id where p.active_status=1 and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)) a " 
//		    + "  where a.entered_by =coalesce(:user,a.entered_by) and a.user_cluster_id=coalesce(:user_cluster,a.user_cluster_id) ", nativeQuery=true)
//			
//	
	
	 
//	@Query("select * from MLFM_WO_MST a  where a.ACTIVE_STATUS=1")
	
	@Query(value="select * from MLFM_WO_MST a WHERE NOT EXISTS( SELECT 1 FROM mlfm_wo_chd b WHERE a.WO_MST_ID=b.WO_MST_ID AND b.ACTIVE_STATUS=1) AND a.ACTIVE_STATUS=1", nativeQuery=true)
	public List <ModelWOMst> findInUsedWOMstList();
  
	
	
	/*
	 * created by:
	 * Date:
	 * 
	 * Purpose:
	 * 
	 * The following query will return inquiry-item-qty records for work orders which have not included at workorder tables yet -> 
	 * (mlfm_wo_mst and mlfm_wo_chd)
	 * The query will be selected when wo checkbox will be unchecked and status will be blank.
	 * 
	 *  mlfm_order a
	 *  mlfm_order_item b
	 *  mlfm_order_item_Qty c
	 *  mlfm_item d
	 *  mlfm_uom e
	 *  mlfm_order_owner f
	 * 
	 * 
	 */
//
//	@Query(value=" select * from (  select  c.order_item_qty_id,a.order_date,d.item_name,c.item_qty,e.uom_id,e.uom_name,b.item_order_id,b.item_id,"
//			+ " a.entered_by, h.user_cluster_id from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id inner join "
//			+ " mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id "
//			+ " left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id"
//			+"  inner join bg_user g on a.entered_by=g.user_id left outer join mlfm_user_cluster h on g.user_id=h.user_id "
//			+ "  and a.active_status=1 and b.active_status=1 and c.active_status=1 and not exists (SELECT 1 FROM mlfm_wo_mst p inner join "
//			+ " mlfm_wo_chd q on p.wo_mst_id="
//			+ " q.wo_mst_id where p.active_status=1 and q.active_status=1 and q.order_item_qty_id=c.order_item_qty_id)) a " 
//		    + "  where a.entered_by =coalesce(:user,a.entered_by) and a.user_cluster_id=coalesce(:user_cluster,a.user_cluster_id) ", nativeQuery=true)
//			
//	
	
	 
}
