package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelOrderOwner;

public interface DaoMachine {
	
	public void saveMachineData(ModelMachine modelmachine);
	
	public List<ModelMachine> getMachineList();
	public List<ModelMachine> getMachineListByCraiteria(String machineName, String capacityPerShiftPerDay, String capacityUom, String remarks, int  status);
	
	public List<ModelMachine> getMachineByType(Long machineTypeId);

}