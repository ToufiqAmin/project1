package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.model.ModelWashing;

public interface DaoWashing {
	
	
	public void saveWashing(ModelWashing modelWashing);
	public List<ModelWashingCustom> getWashingById(Long id);
	public List<ModelWashingCustom> getWashedById(Long id, Date startDate, Date endDate);
	public List<ModelWashingCustom>getPendingWashingOrderDetails(Long buyerId,Long inquiryId, Long washedById, Date startDate, Date endDate, Long itemId);
	public List<ModelWashingCustom>getPendingWashingWODetails(Long buyerId,Long inquiryId, Long washedById, Date startDate, Date endDate, Long itemId);
	public List<ModelWashingCustom>getCompletedWashingOrderDetails(Long buyerId,Long inquiryId, Long washedById, Date startDate, Date endDate, Long itemId);
	public List<ModelWashingCustom>getCompletedWashingWODetails(Long buyerId,Long inquiryId, Long washedById, Date startDate, Date endDate,Long itemId);

	

	

}
