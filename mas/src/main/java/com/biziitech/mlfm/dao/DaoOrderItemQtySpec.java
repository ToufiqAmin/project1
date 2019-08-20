package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;

public interface DaoOrderItemQtySpec {
	public void saveSpecification(String item,String specValue,String remarks);
	public List<ModelOrderItemQtySpec>findAll();
	public Optional<ModelOrderItemQtySpec> getItemQtySpecById(Long spec_Id);
	
	public List<ModelOrderItemQtySpec> getOrderItemQtySpecList(Long inquiryItemQtyId);
	public List<ModelOrderItemQtySpec> getOrderItemQtySpecActiveList(Long inquiryItemQtyId);
	
	
	/*created by sohel rana on 25/03/2019
	 * 
	 */
	
	public void saveNewSpecification(ModelOrderItemQtySpec modelOrderItemQtySpec);
	
	
	/*crated by Sohel Rana on 27/03/2019
	 * 
	 */
	
	public List<ModelOrderItemQtySpec>findAllSpecByOrderItemQtyId(Long id);
	
	/*created by sohel rana on 31/03/2019
	 * 
	 */
	
	public List<ModelOrderItemQtySpec>findAllSpecification(Long specificationId);
	
	
	//created by sohel rana on 23/04/2019
	
	public List<ModelWOChdListCustom>findSpecificationById(Long specificationId);
	
	
}
