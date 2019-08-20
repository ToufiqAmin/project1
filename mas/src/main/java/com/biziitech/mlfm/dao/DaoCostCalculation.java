package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelWOInquiryData;

public interface DaoCostCalculation {

	public List<ModelWOInquiryData>getSearchData(Long ownerType,String owner,String ultimateOwner,Long inquiryId,Long designId,Long userDefinedNo,String designName,Long designBy,Long itemName,Date stratDate, Date endDate);
	
	public List<ModelWOInquiryData>getSearchDataWithCalculate(Long ownerType,String owner,String ultimateOwner,Long inquiryId,Long designId,Long userDefinedNo,String designName,Long designBy,Long itemName,Date stratDate, Date endDate);
	
	public List<ModelWOInquiryData>getDataByDesignId(Long id);
	
	public List<ModelWOInquiryData>getConsumCalculateDataByDesignId(Long id);
}
