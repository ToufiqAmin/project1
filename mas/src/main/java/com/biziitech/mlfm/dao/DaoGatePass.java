package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.model.ModelFinishing;
import com.biziitech.mlfm.model.ModelGatePassMst;

public interface DaoGatePass {
	
	public List<ModelWashingCustom> getInquirySearchData(Long onwerType,Long owner, Long orderId,Date startDate,Date endDate);
	public List<ModelWashingCustom> getWorkOrderSearchData(Long ownerType,Long owner,Long orderId,Date startDate,Date endDate);
	public void saveGatePassMst(ModelGatePassMst gatePassMst);
	public List<ModelGatePassCustom> getIGatePassMstData(Long id);
	public List<ModelWashingCustom> getInquiryDataById(Long id);
	public List<ModelGatePassCustom> getAllData();
	
	public List<ModelWashingCustom> getGetPassDoneSearchData(Long onwerType,Long owner, Long orderId);
}
