package com.biziitech.mlfm.dao;


import java.util.List;

import com.biziitech.mlfm.model.ModelMachineType;

public interface DaoMachineType {

	public void saveMachine(ModelMachineType modelmachine);
	public List<ModelMachineType> getMachineTypeList();
	public List<ModelMachineType> getMachineName();
	public List<ModelMachineType> getMachineTypeListByCraiteria(String machineTypeName, String shortCode, String remarks, int  status);
}
