package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelObjectGroup;
import com.biziitech.mlfm.model.ModelMachineType;

public interface DaoObjectGroup {

	public void saveObjectGroup(ModelObjectGroup objectGroup);
	public List<ModelObjectGroup> getObjectGroupList();
	public List<ModelObjectGroup> getObjectGroupListByCraiteria(String groupName, String shortCode, String remarks, int  status);
}
