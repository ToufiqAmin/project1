package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelDyingPlanCustom;
import com.biziitech.mlfm.model.ModelDyingPlan;

public interface DaoDyingPlan {

	public void saveDyingPlan(ModelDyingPlan modelDyingPlan);
	public void deleteDyingPlan(Long dyingPlanId);
	 public List<ModelDyingPlanCustom>getPendingDyingPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelDyingPlanCustom>getCompletedDyingPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelDyingPlanCustom> getDyingPlanByPOId(Long pOId);
	 public List<ModelDyingPlanCustom> getDyingPlanByDyingPlanId(Long dyingPlanId);
	
	
	
}
