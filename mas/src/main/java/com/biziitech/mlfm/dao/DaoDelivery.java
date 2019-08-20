package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.model.ModelDelivery;
import com.biziitech.mlfm.model.ModelDeliveryChallan;

public interface DaoDelivery {
   
	public List<ModelDeliverChallanCustom> getSearchData(Long ownerType,Long owner,Long pOId,String challanNo,Date startDate,Date endDate);
	public void saveDelivery(ModelDelivery delivery);
}
