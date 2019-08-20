package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.model.ModelProductionPlanChd;


public interface DaoProductionPlanChd {
	
	public void saveProductionPlanChd(ModelProductionPlanChd modelProductionPlanChd);
	
	
	public List<ModelProductionPlanChd> findProductionPlanChdListByMstId(Long productionPlanMstId);

}
