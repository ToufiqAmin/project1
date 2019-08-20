package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;

public interface DaoOrder {
	
	public void saveOrder(ModelOrder order);
	
	
	
	public List<ModelOrder> getOrderData();
	public Optional<ModelOrder> getOrderById(Long orderId);
	public List<Map<String,Object>> report();
	//public List<ModelOrder> getAllOwner( String initial_Buyer,String ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed,Date mail_st,Date mail_ed);
	public List<ModelOrder> getAllOwner( String initial_Buyer,String ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed);
   
	public List<ModelOrder> getAllOwnerActive(Long initial_Buyer,Long ultimate_buyer,Long inquiry_Id,String mail_Id,Long user_inquery_no,String remarks,String user,Date inq_st,Date inq_ed,int active_status);
	
	public List<ModelOrder> getOrderData(String initialBuyer,String ultimate_buyer, String user, Date stDate, Date endDate);
    
    
    public List<ModelOrder> getAllOwnerBySearch(Long typeId, Long owner, Long ultimateOwner, Long inquiryId,
			Long user, Date startDate, Date endDate, int status);
    
    public List<ModelInquiryList> getOrderDeatailsData(Long ownerType,Long owner,Long ultimateOwner, Date startDate, Date endDate,Long user,int active);
   
    
    
    //created by sohel rana on 14/03/2019
    
    public void saveNewOrder(ModelOrder order);
    
    public List<ModelInquiryList> getNewOrderData(Long id);
    
    public List<ModelOrder> getAllInquiryData(Long typeId,Long owner,Long ultimateOwner,Long inquiryId,Long user,Date startDate,Date endDate,int status);
    
    
    public List<ModelOrder> newInquiry(Long id);
    
    //created by sohel rana on 18/04/2019
    
    public List<ModelOrder> checkInquiry(Long ownerId,Date orderDate);
    
    //created by sohel rana on 25/04/2019
    
    public List<ModelOrder> getAllVerifiedInquiryData(Long typeId, Long owner, Long ultimateOwner, Long inquiryId,
			Long user, Date startDate, Date endDate, int status, Date doneStartDate, Date doneEndDate,Long verifiedBy);
    
}
