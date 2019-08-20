package com.biziitech.mlfm.dao;


import java.util.List;

import com.biziitech.mlfm.model.ModelOrderOwner;


public interface DaoOrderOwner {
	
	//modified by sohel rana on 17/03/2019
	
	public List<ModelOrderOwner> getAllOwner(Long countryId, String name,String shortCode, int  status,String contactPerson);// Specific Field search +all
	
	
	
	public List<ModelOrderOwner>getAllOwnerNotActive(String type, String name, String shortCode, String country, String number);//owner not active + Specific Field
	public List<ModelOrderOwner> getAllOwnerName();
	public List<ModelOrderOwner> getAllOwner();
	
	public void saveOwner(ModelOrderOwner  owner);
	public void updateOwner(ModelOrderOwner  owner);
	
	public long getOwnerId() ;//for generating Owner Id (Primary key);
	public ModelOrderOwner getOwner(Long id) ;//all field of a specific owner
	
	
	public List<ModelOrderOwner> getOwnerByType(Long ownerTypeId);
	public List<ModelOrderOwner> getOwnerByTypeInOrder(Long ownerTypeId);
	
	public List<ModelOrderOwner> getActiveOrderOwnerList();
	
	public List<ModelOrderOwner> getActiveOrderOwnerListByOwnerTypeId(Long ownerTypeId);
	
	public List<ModelOrderOwner> getUltimateBuyerList();
	
}
