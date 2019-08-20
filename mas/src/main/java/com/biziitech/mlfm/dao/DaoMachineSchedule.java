package com.biziitech.mlfm.dao;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.custom.model.ModelMachineScheduleData;
import com.biziitech.mlfm.model.ModelMachineShedule;

public interface DaoMachineSchedule {
   
	void saveSchedule(ModelMachineShedule modelSchedule);
	
	public List<ModelMachineScheduleData> getMachineScheduleByMachineId(Long id,Long machineTypeId,Long machineShiftId,Date from,Date to);
	
	public List<ModelMachineScheduleData> checkScheduleData(Long id,Long machineTypeId,Long machineShiftId,Date scheduleDate);
	public List<ModelMachineScheduleData> activeInactiveData(Long id);
	
	public List<ModelMachineScheduleData> getScheduleAllData(Long shiftId,Long machineId,Date from,Date to,int status);
	
}
