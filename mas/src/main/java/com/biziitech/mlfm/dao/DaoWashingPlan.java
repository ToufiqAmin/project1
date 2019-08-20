package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;


import com.biziitech.mlfm.custom.model.ModelWashingPlanCustom;
import com.biziitech.mlfm.model.ModelWashingPlan;

public interface DaoWashingPlan {
	
	public void saveWashingPlan(ModelWashingPlan modelWashingPlan);
	public void deleteWashingPlan(Long washingPlanId);
	public List<ModelWashingPlanCustom> getPendingWashingPlanDetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	public List<ModelWashingPlanCustom> getCompletedWashingPlanDetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	public List<ModelWashingPlanCustom> getWashingPlanById(Long washingPlanId);
	public List<ModelWashingPlanCustom> getWashingPlanBy(Long pOId);
	

}
