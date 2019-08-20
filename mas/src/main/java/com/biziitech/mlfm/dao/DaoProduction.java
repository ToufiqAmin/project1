package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelWOMst;

public interface DaoProduction {
	
	public List<ModelInquiryList>getOrderListPlanned(String buyerId,String ultimateBuyerId,Long inqueryId,
			Date inqFromDate, Date inqToDate);
	
	public List<ModelInquiryList>getOrderListNotPlanned(String buyerId,String ultimateBuyerId,Long inqueryId,
			Date inqFromDate, Date inqToDate);
	
	public List<ModelProductionCustom>getSearchListPending(Long orderOwnerId, Long wOId,
			Date pOStartDate, Date pOEndDate);
	
	public List<ModelProductionCustom>getSearchListDoneProduction(Long orderOwnerId, Long wOId,
			Date pOStartDate, Date pOEndDate);
	
	public List<ModelProductionCustom>getPlanDetails(Long orderItemQtyId);
	
	public void saveProduction(ModelProduction modelProduction);
	
	public List<ModelProductionCustom> getProductionById(Long productionId);
	
	public Optional<ModelProduction> findProductiontById(Long id);
	
	public List<ModelProductionCustom> getProductionByIdForproductionEdit(Long productionId);
	
	public List<ModelProductionCustom> getProductionByPOMstId(Long pOMstId);
	

}
