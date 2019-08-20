package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelProductionPlanMst;

public interface DaoDesign {

	void saveDesign(ModelDesign design);
	public Optional<ModelDesign> getDesignById(Long id);
	public List<ModelDesign> getDesignFromInquery(Long id);
	public List<ModelDesign> getDesignListActive();
	
	
	// sas
	public List<ModelDesignCustom> getDesignListNotDone(Long orderOwnerTypeId,Long orderOwnerId,Long ultimateBuyerId,Long orderId,Long itemId,Long userId,Date orderStartDate,Date orderEndDate,String userInqueryNo,String remarks,int active);
	public List<ModelDesignCustom> getDesignListDone(Long orderOwnerTypeId,Long orderOwnerId,Long ultimateBuyerId,Long orderId,Long itemId,Long userId,Date designStartDate,Date designEndDate,String userInqueryNo,String remarks,int active);
	public List<ModelDesignCustom> designSaveModal(ModelDesign modelDesign);
	public List<ModelDesignCustom> getDesignListByDesignId(Long designId);
	
	public Optional<ModelDesign> findDesignById(Long designId);
	
	
	//created by sohel rana on 04/04/2019
	
	public List<ModelDesign> getDesignListForInquiryMs(Long inquiryId);
	
	//created by sohel rana on 07/04/2019 
	
	public List<ModelDesign> getSpecNameFromDesign(Long designId);
	
	
	
}
