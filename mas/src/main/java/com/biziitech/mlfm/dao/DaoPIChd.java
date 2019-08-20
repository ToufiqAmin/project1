package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;

public interface DaoPIChd {
	
	public List<ModelPIChdOrder> getPIChdDataList(Long workOrderChdId);
	public void savePIChd(ModelPIChd modelPIChd);
	
	// edited by KTA
	
	public List<ModelPIChdOrder> getPIChdDataById(Long pIChdId);
	
	//end edited by KTA
	
	public List<ModelPIChdOrder> getPIChdDataByWOChId(Long id);

}
