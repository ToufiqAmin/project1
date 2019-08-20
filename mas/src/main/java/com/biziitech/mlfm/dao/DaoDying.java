package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelDyingCustom;
import com.biziitech.mlfm.model.ModelDying;

public interface DaoDying {

	
	public void saveDying(ModelDying modelDying);
	public List<ModelDyingCustom> getDyingById(Long id);
	public List<ModelDyingCustom> getDyingDoneById(Long dyingId);
	public List<ModelDyingCustom>getPendingDyingOrderDetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelDyingCustom>getPendingDyingWODetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelDyingCustom>getCompletedDyingOrderDetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate, Long itemId);
	public List<ModelDyingCustom> getCompletedDyingWODetails(Long buyerId,Long pOId, Long dyingById, Date startDate, Date endDate,Long itemId);
	
	
	
}
