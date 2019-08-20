package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelObject;
import com.biziitech.mlfm.bg.model.ModelObjectGroup;

public interface DaoObject {

	public void saveObject(ModelObject object);
	public List<ModelObject> getObjectFromModule(Long id);
	public List<ModelObject> getAllObjectList();
}
