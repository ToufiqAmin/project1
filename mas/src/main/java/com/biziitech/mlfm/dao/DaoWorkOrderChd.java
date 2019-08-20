package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.model.ModelWOMst;

public interface DaoWorkOrderChd {
	 
	public List<ModelPIChdOrder> getWOChdDataList(Long workOrderId);
	public List<ModelPIChdOrder> getDoneWOChdDataList(Long workOrderId);
	public void saveWOChd(ModelWOChd modelWOChd);
	public List<ModelWOInquiryData> getWOChdData(Long wOChdId);
	
	public Optional<ModelWOChd> findWOChdById(Long wOChdId);

}
