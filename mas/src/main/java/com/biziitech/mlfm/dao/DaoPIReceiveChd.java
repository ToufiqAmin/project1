package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.model.ModelPIReceiveChd;

public interface DaoPIReceiveChd {

	
	public void savePIReceiveChd(ModelPIReceiveChd modelPIReceiveChd);
	
	public List<ModelPIReceiveCustom> getPIReceiveChdByPIMstId(Long id);
	
} 
