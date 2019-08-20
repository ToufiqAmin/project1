package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.model.ModelOrderItem;

public interface DaoOrderItem {
	public void saveOrderItem(ModelOrderItem item);
	public List<ModelOrderItem> getOrderItem();
	public List<ModelOrderItem> getOrderItemById(Long id);
	public Optional<ModelOrderItem> getItemById(Long id);
	
	public List<ModelInquiryList> getOrderItemDetailsById(Long id);
	
	public List<ModelOrderItem>getOrderItemDetails(Long id);
	
	//created by sohel rana on 22/04/2019
	
	public List<ModelOrderItem>getAuditDetails(Long id);
	
	public List<ModelOrderItem>checkOrderItem(Long orderId,Long itemId);
	
	
}
