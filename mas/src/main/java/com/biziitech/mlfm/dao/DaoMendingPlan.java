package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelMendingPlanCustom;
import com.biziitech.mlfm.model.ModelMendingPlan;

public interface DaoMendingPlan {
	
	public void saveMendingPlan(ModelMendingPlan modelMendingPlan);
	public void deleteMendingPlan(Long mendingPlanId);
	public List<ModelMendingPlanCustom> getPendingMendingPlanDetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	public List<ModelMendingPlanCustom> getCompletedMendingPlanDetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	public List<ModelMendingPlanCustom> getMendingPlanById(Long mendingPlanId);
	public List<ModelMendingPlanCustom> getMendingPlanBy(Long pOId, Long qCId);
	public List<ModelMendingPlanCustom> getQCPlanByPOId(Long pOId);
	public List<ModelMendingPlanCustom> getQCByPOId(Long pOId);

}
