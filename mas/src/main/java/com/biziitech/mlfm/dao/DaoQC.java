package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;


import com.biziitech.mlfm.custom.model.ModelQCCustom;
import com.biziitech.mlfm.model.ModelQC;

public interface DaoQC {
	
	 public void saveQC(ModelQC modelQC);
	 public void deleteQC(Long qCId);
	 public List<ModelQCCustom> getQCById(Long id);
	 public List<ModelQCCustom> getQCDoneById(Long id);
	 public List<ModelQCCustom>getPendingQCOrderDetails(Long buyerId,Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId);
	 public List<ModelQCCustom>getPendingQCWODetails(Long buyerId,Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId);
	 public List<ModelQCCustom>getCompletedQCOrderDetails(Long buyerId,Long inquiryId, Long qCById, Date startDate, Date endDate, Long itemId);
	 public List<ModelQCCustom>getCompletedQCWODetails(Long buyerId,Long inquiryId, Long qCById, Date startDate, Date endDate,Long itemId);
	 

	 
}
