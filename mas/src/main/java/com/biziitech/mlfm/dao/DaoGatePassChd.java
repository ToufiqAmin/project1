package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.model.ModelGatePassChd;

public interface DaoGatePassChd {
   
	public void saveGatePassChd(ModelGatePassChd gatePassChd);
	public List<ModelWashingCustom> getDataByChdId(Long id);
	public List<ModelWashingCustom> getDataByChdByProductionId(Long id);
}
