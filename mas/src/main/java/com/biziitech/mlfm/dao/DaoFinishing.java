package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelFinishingCustom;
import com.biziitech.mlfm.model.ModelFinishing;

public interface DaoFinishing {
	
	public void saveFinishing(ModelFinishing modelFinishing);
	public List<ModelFinishingCustom> getFinishingById(Long id);
	public List<ModelFinishingCustom> getFinishingDoneById(Long finishingId);
	public List<ModelFinishingCustom>getPendingFinishingOrderDetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelFinishingCustom>getPendingFinishingWODetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelFinishingCustom>getCompletedFinishingOrderDetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelFinishingCustom> getCompletedFinishingWODetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate,Long itemId);

}
