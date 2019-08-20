package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelPIMst;

public interface DaoDeliveryChallan {
    
	
	public void saveDeliveryChallan(ModelDeliveryChallan deliveryChallan);
	public List<ModelWashingCustom> getInquirySearchData(Long ownerType,Long owner,Long orderId);
	public List<ModelWashingCustom> getWorkOrderSearchData(Long ownerType,Long owner,Long workOrderId,Date startDate,Date endDate,Long pOId);
	public List<ModelWashingCustom> getWorkOrderSearchDataById(Long id);
	public List<ModelDeliverChallanCustom> getDeliverChallanDataById(Long id);
	
	public List<ModelDeliverChallanCustom> getDeliverChallanDoneDataById(Long id);
}
