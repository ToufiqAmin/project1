package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelQCPlanCustom;
import com.biziitech.mlfm.model.ModelQCPlan;

public interface DaoQCPlan {
	
	public void saveQCPlan(ModelQCPlan modelQCPlan);
	public void deleteQCPlan(Long qCPlanId);
	 public List<ModelQCPlanCustom>getPendingQCPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelQCPlanCustom>getCompletedQCPlanPODetails(Long pOId,Long buyerId,Date startDate,Date endDate);
	 public List<ModelQCPlanCustom> getQCPlanByPOId(Long pOId);
	 public List<ModelQCPlanCustom> getQCPlanByQCPlanId(Long qCPlanId);

	

}
