package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelPIMstCustom;

public interface DaoPIReceive {

	public List<ModelPIMstCustom> getPISearchData(Long ownerType,Long owner,Long wOMstId,Date startDate,Date endDate,Long pITypeId);
	
	public List<ModelPIMstCustom> getPIReceiveDoneData(Long ownerType,Long owner,Long wOMstId,Date startDate,Date endDate,Long pITypeId,Date doneStartDate,Date doneEndDate);
}
