package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelFinishingPlanCustom;
import com.biziitech.mlfm.model.ModelFinishingPlan;

public interface DaoFinishingPlan {

	public void saveFinishingPlan(ModelFinishingPlan modelFinishingPlan);
	public void deleteFinishingPlan(Long finishingPlanId);
	 public List<ModelFinishingPlanCustom>getPendingFinisingPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelFinishingPlanCustom>getCompletedFinishingPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelFinishingPlanCustom> getFinishingPlanByPOId(Long pOId);
	 public List<ModelFinishingPlanCustom> getFinishingPlanByFinishingPlanId(Long finishingPlanId);
	
	
	
}
