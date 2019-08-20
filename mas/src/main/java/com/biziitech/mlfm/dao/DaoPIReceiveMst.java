package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.model.ModelPIReceiveMst;

public interface DaoPIReceiveMst {
   
	public void savePIReceiveMst(ModelPIReceiveMst modelPIReceiveMst);
	
	public List<ModelPIReceiveCustom> getPIReceiveMstById(Long id);
	
	public List<ModelPIReceiveCustom> getPIReceiveMstByPIMstId(Long id);
	
	public List<ModelPIReceiveCustom> getPIChdById(Long id);
	
}
