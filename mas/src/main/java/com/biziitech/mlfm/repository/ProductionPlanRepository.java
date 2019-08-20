package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.model.ModelProductionPlanMst;



public interface ProductionPlanRepository extends JpaRepository<ModelProductionPlanMst,Long>{
	
	@Query("select a from BG_USER a where a.activeStatus=1 order by a.userName" )
	public List<ModelUser> findUser();
	

//	public List<ModelOrder> findProductionInquiryDetails(@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_ID") Long inquery_ID,@RequestParam("plan_ID") Long plan_ID,@RequestParam("user")String user,@RequestParam("design")String design,@RequestParam("inquery_start_date")String inq_start,@RequestParam("inquery_end_date")String inq_end,@RequestParam("design_start_date")String design_start,@RequestParam("design_end_date")String design_end,@RequestParam("plan_start_date")String plan_start,@RequestParam("plan_end_date")String plan_end );	

    @Query(value=" select * from (  select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,a.order_owner_id,f.owner_name from mlfm_order a inner join mlfm_order_item b on a.order_id=b.order_id inner join mlfm_order_item_qty c on b.item_order_id=c.order_item_id inner join mlfm_item d on b.item_id=d.item_id left outer join mlfm_uom e on c.uom_id=e.uom_id inner join mlfm_order_owner f on a.order_owner_id=f.order_owner_id  and a.active_status=1 and b.active_status=1 and c.active_status=1 and not exists (SELECT 1 FROM mlfm_production_plan_mst p where p.ORDER_ITEM_QTY_ID=c.order_item_qty_id) ) a " +
    " where a.owner_name like concat('%',:initial_Buyer,'%')", nativeQuery=true)
	public List<ModelInquiryList> findInquiryList(@Param("initial_Buyer") String initial_Buyer);	
	
	//public List<ModelOrder> findWOList(@RequestParam("order_type") String order_type,@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_start_date")String inq_start,@RequestParam("inquery_end_date")String inq_end);	
   // @Query(value="select a.MACHINE_SCHEDULE_ID,a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name from mlfm_machine_schedule a inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID where a.ACTIVE_STATUS=1  a where a.entered_by =coalesce(:user,a.entered_by) and a.user_cluster_id=coalesce(:user_cluster,a.user_cluster_id ", nativeQuery=true )
   
    
    
    // using NamedJdbcTamplate
//    @Query(value="select a.MACHINE_SCHEDULE_ID,a.machine_schedule_name,a.schedule_date,c.MACHINE_NAME,b.machine_shift_name from mlfm_machine_schedule a inner join mlfm_machine_shift b on a.machine_shift_id=b.machine_shift_id inner join mlfm_machine c on c.MACHINE_ID=b.MACHINE_ID where a.ACTIVE_STATUS=1  ", nativeQuery=true )
//    public List<ModelMachineScheduleData> findMachineSheduleList(@Param("machine") Long machine,@Param("machineShiftName") Long machineShiftName,@Param("inquery_start_date") Date inquery_start_date,@Param("inquery_end_date") Date inquery_end_date);
}



