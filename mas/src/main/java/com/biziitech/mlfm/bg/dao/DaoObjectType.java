package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelObjectType;

public interface DaoObjectType {

	public void saveObjectType(ModelObjectType objectType);
	public List<ModelObjectType> getObjectTypeList();
	public List<ModelObjectType> getObjectTypeListByCraiteria(String typeName, String shortCode, String remarks, int  status);
	
}
