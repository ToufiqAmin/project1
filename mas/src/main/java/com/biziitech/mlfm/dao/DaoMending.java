package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelMendingCustom;
import com.biziitech.mlfm.model.ModelMending;

public interface DaoMending {
	
	public void saveMending(ModelMending modelMending);
	public List<ModelMendingCustom> getMendingByMendingPlanId(Long mendingPlanId);
	public List<ModelMendingCustom> getMendedById(Long id, Date startDate, Date endDate);
	public List<ModelMendingCustom>getPendingMendingOrderDetails(Long buyerId,Long inquiryId, Long mendedById, Date startDate, Date endDate, Long itemId);
	public List<ModelMendingCustom>getPendingMendingWODetails(Long buyerId,Long inquiryId, Long mendedById, Date startDate, Date endDate, Long itemId);
	public List<ModelMendingCustom>getCompletedMendingOrderDetails(Long buyerId,Long inquiryId, Long mendedById, Date startDate, Date endDate, Long itemId);
	public List<ModelMendingCustom>getCompletedMendingWODetails(Long buyerId,Long inquiryId, Long mendedById, Date startDate, Date endDate,Long itemId);
	

}
