package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.model.ModelWOMst;



public interface DaoProductionPlan {
	
	public void saveProductionPlan(ModelProductionPlanMst ProductionPlan);
	public List<ModelUser> getUserList();
	
	//public List<ModelInquiryList>getOrderList(int orderType,String initialBuyer,String ultimateBuyerName,Date inqStartDate, Date inqEndDate);

//	public List<ModelInquiryList>getOrderList(int orderType,String initialBuyer,String ultimateBuyerName,Long inqueryId,Long planId,Long designBy,Long designId,Long userId,Long userClusterId,Date inqStartDate, Date inqEndDate,Date designStartDate,Date designEndDate,Date planStartDate,Date planEndDate);
	public List<ModelInquiryList>getOrderList(String initialBuyer,String ultimateBuyerName,Long inqueryId,Date inqStartDate, Date inqEndDate);
	public List<ModelInquiryList>getOrderListPlanned(String initialBuyer,String ultimateBuyerName,Long inqueryId,Date inqStartDate, Date inqEndDate);
	
	
	public List<ModelInquiryList>getWOList(Long orderOwnerTypeId,Long initialBuyerId,Long ultimateBuyerId,Long wOMstId,Long itemId,
    		Date wOStartDate,Date wOEndDate);
	
	public List<ModelInquiryList>getWOListPlanned(Long orderOwnerTypeId,Long initialBuyerId,Long ultimateBuyerId,Long wOMstId,Long userId,Long itemId,
			Date planStartDate,Date planEndDate);
	
	public List<ModelProductionCustom> getProductionPlanMstById(Long productionPlanMstId);
	
	public List<ModelProductionCustom> getProductionPlanChdByMstId(Long productionPlanMstId);
	
	public List<ModelProductionCustom> getProductionPlanChdByChdId(Long productionPlanChdId);
	
	public List<ModelProductionCustom> getProductionPlanChdDateByMstId(Long productionPlanMstId);
	
	public List<ModelMachineScheduleData> getAvailableMachine(Long machineTypeId);
	
	
//	public List<ModelOrder>getAllOwner(String initial_Buyer,String ultimate_Buyer,String inquery_ID ,String plan_ID,String user,String design,Date inq_st,Date inq_ed,Date design_st,Date design_ed,Date plan_st,Date plan_ed);
	public List<ModelMachineScheduleData>getMachineSheduleList(Long machineTypeId, Long machineId,Long machineShiftId,Date startDate,Date endDate);
	public List<ModelMachineScheduleData> getMachineSheduleListUnchecked(Long machineTypeId, Long machineId,Long machineShiftId,Date startDate,Date endDate);

	public Optional<ModelProductionPlanMst> findProductionplanMstById(Long id);
	
	
	public List<ModelProductionCustom> getInUsedProductionPlanMst(Long wOMstId);
//  public List<ModelMachineScheduleData>getProductionPlanChdList();
	
	
	
}
