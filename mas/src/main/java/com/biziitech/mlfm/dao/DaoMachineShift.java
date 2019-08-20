package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelShift;

public interface DaoMachineShift {
	
	
	public List<ModelShift> getShiftList();
	public List<ModelMachine> getMachineList();
	
	public List<ModelMachineShift> getMachineShiftList();
	

	public List<ModelMachineShift> getMachineShiftByName(Long machineId);
	
	//public List<ModelMachineShift> getMachineShiftByName(Long machineId);
	
	
	//saj - machine_shift_list search query - Start
	public List<ModelMachineShift> getMachineShiftListByCraiteria(String shiftName, String remarks, int  status);
	//saj - machine_shift_list search query - end



	public void saveMachineShift(ModelMachineShift machineShift);


	

}
