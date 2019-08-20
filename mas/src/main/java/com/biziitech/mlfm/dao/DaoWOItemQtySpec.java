package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelWOItemQtySpec;


public interface DaoWOItemQtySpec {
	
	public void saveWOItemQtySpec(ModelOrderItemQtySpec modelOrderItemQtySpec);
	public List<ModelWOItemQtySpec> getWOItemQtySpecList(Long inquiryItemQtyId);
	//public void saveWOItemQtySpecNew(Long wOSpecNew,String wOValueNew,Long wOUOMNew,String wORemarksSpecNew);
	public void saveWOItemQtySpecNew(ModelWOItemQtySpec modelWOItemQtySpec);
    public int chkAlreadySavedWOItemQtySpec(Long orderItemQtyId,Long specId);
    
    public List<ModelWOChdListCustom> getWOItemQtySpecListCustom(Long orderItemQtyId);
}
