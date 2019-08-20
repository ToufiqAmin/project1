package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelWOMst;

public interface DaoWorkOrder {
	
	public void saveWOMst(ModelWOMst modelWOMst);
	
	
	public List<ModelWOInquiryData>getInquiryDataListDoneWO(Long user,Long user_cluster,Long orderOwnerId,Long ultimateOwnerId,Date orderStratDate,Date orderEndDate);
	public List<ModelWOInquiryData>getInquiryDataListNotDoneWO(Long user,Long user_cluster,Long orderOwnerId,Long ultimateOwnerId,Date orderStratDate,Date orderEndDate);
	
	
	public List<ModelPIInquiryList>getWoDataList(Long ownerType,String owner,String ultimateOwner, Long user,Long cluster, Date startdate, Date enddate);
	
//	public List<ModelPIInquiryList>getWoMstList(Long id);
	
	
	public List<ModelPIInquiryList>getWoDataListWithPI(Long ownerType,String owner,String ultimateOwner, Long user,Long cluster, Date startdate, Date enddate);

	
	public Optional<ModelWOMst> findWOMstById(Long Id);
	
	public List<ModelWOMst>getInUsedWOMstList();
	
	
	public List<ModelWOChdListCustom> getWOChdList(Long inquiryItemQtyId,Long wOMstId);
	
	
	//public List<ModelOrderItemQtySpec> getOrderItemQtySpecList(Long inquiryItemQtyId);
	
	public List<ModelWOInquiryData> getWOMstListFindbyWOMstId(Long wOMstId);
	
	public List<ModelWOInquiryData> getWOMstListDoneData(Long orderItemQtyId);
	
	public List<ModelWOInquiryData> getWOChdListDoneData(Long wOMstId);
	
	public Optional<ModelWOMst> findWOMst(Long wOMstId);
	
}
