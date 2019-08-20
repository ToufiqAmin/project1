package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelPOCustom;

import java.util.Date;
import com.biziitech.mlfm.model.ModelPO;
import com.biziitech.mlfm.model.ModelPOChd;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelProductionPlanMst;


public interface DaoPO {
	
	public List<ModelPOCustom>getWorkOrderDataUnchecked(Long wOMstId,Long orderOwnerTypeId,String ownerName,Long itemId,Date pPStratDate,Date pPEndDate);
	public List<ModelPOCustom>getWorkOrderDataChecked(Long wOMstId,Long orderOwnerTypeId,String ownerName,Long itemId,Date pOStratDate, Date pOEndDate);
	
	public void savePO(ModelPO modelPO);
	
	public ModelPO getPOList(Long pOId);
	
	
	
	public ModelPO getPOByDesignId(Long designId);
	
	
	//,Long itemId,Long designId
	public List<ModelPOCustom>getProductionPlanDataPONotDone(Long orderOwnerTypeId, Long orderOwnerId, Long wOMstId,Long itemId,Long designId, 
			Date planStartDate, Date planEndDate);
	
//	public List<ModelPOCustom>getProductionPlanDataPONotDone(Long orderOwnerTypeId, Long orderOwnerId, Long wOMstId,Long itemId,Long designId,String userDesignNo, 
//			Date planStartDate, Date planEndDate);
	
	public List<ModelPOCustom>getProductionPlanDataPODone(Long orderOwnerTypeId, Long orderOwnerId, Long wOMstId,Long itemId,Long designId, 
			Date pODoneFrom, Date pODoneTo);
	
	
	public List<ModelPOCustom>getProductionPlanChdData(Long pPlanMstId);
	
	public void savePOMst(ModelPOMst modelPOMst);
	
	
	public List<ModelPOCustom>getPOMstData(Long pOMstId);
	public ModelPOChd savePOChd(ModelPOChd modelPOChd);
	
	public List<ModelPOCustom>getPOChdData(Long pOChdId);

	
	public List<ModelPOCustom>getPOMstDoneData(Long pPChdId);
	public List<ModelPOCustom>getPOChdDoneData(Long pOMstId);
	
	
	public Optional<ModelPOMst> findPOMstById(Long id);
	
	
}
