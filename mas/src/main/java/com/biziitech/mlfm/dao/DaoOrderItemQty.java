package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.model.ModelOrderItemQty;

public interface DaoOrderItemQty {
public void saveOrderItemQty(ModelOrderItemQty itemQty);
public List<ModelOrderItemQty> findItemsQty();
public List<ModelOrderItemQty> getOrderItemQtyById(Long id);
public Optional<ModelOrderItemQty> getOrderItemQty(Long itemQtyId);


public List<ModelInquiryList> getOrderItemQtyDetailsById(Long id);

public List<ModelInquiryList> getOrderItemQtyDetailsByOrderItemId(Long id);


/*crated by sohel rana on 27/03/2019
 * 
 */

public List<ModelOrderItemQty> getQuantityDataByOrderItemId(Long id);


/*created by sohel rana on 28/03/2019
 * 
 * 
 */

public List<ModelOrderItemQty>findOrderItemQtyForEdit(Long id);


//created by sohel rana on 08/04/2019

public List<ModelOrderItemQty> getAllInquiryMappedData(Long typeId,Long owner,Long ultimateOwner,Long inquiryId,Long user,Date startDate,Date endDate,int status);

}
