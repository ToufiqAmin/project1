package com.biziitech.mlfm.dao;


import java.util.List;

import com.biziitech.mlfm.bg.model.ModelObjectGroup;

import com.biziitech.mlfm.model.ModelUserObject;

public interface DaoUserObject {
	
	public List<ModelUserObject> getUserObject(Long userId);
	
	public void saveUserObject(ModelUserObject modelUserObject);
	
	public List<ModelUserObject> getUserObjectById(Long userObjectId);
	
	public List<ModelUserObject> getUserObjectByObjectGroup(Long userId, String objectGroup);
	
	public List<ModelObjectGroup> getObjectGroup();
	
	public List<ModelUserObject> getUserObjectByObjectGroupId(Long objectGroupId);
	
	
	public List<ModelUserObject> checkUserObject(Long userId,Long objectId);

}
