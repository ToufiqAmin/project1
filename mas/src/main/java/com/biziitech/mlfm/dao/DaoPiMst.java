package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelPIMst;

public interface DaoPiMst {
	
	public void savePIMst(ModelPIMst modelPIMst);
	
	    public List<ModelPIMstCustom>getPIMstDetails();
   
		public List<ModelPIMstCustom> getPIMstById(Long id);
}
