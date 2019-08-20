package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelPackingCustom;
import com.biziitech.mlfm.model.ModelPacking;

public interface DaoPacking {

	public void savePacking(ModelPacking modelPacking);
	public List<ModelPackingCustom> getPackingById(Long id);
	public List<ModelPackingCustom> getPackingDateById(Long id, Date startDate, Date endDate);
	public List<ModelPackingCustom>getPendingPackingOrderDetails(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom>getPendingPackingWODetails(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom>getCompletedPackingOrderDetails(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom> getCompletedPackingWODetails(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate,Long itemId);
	 
	 public List<ModelPackingCustom>getPendingPackingOrderDetailsByPackingDate(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom>getPendingPackingWODetailsByPackingDate(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom>getCompletedPackingOrderDetailsByPackingDate(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate, Long itemId);
	 public List<ModelPackingCustom> getCompletedPackingWODetailsByPackingDate(Long buyerTypeId,Long buyerId,Long inquiryId, Long packedById, Date startDate, Date endDate,Long itemId);
	
	
	
	
}
