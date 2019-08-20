package com.biziitech.mlfm.custom.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelMachineScheduleData {
	
	private Long machineSheduleId;
	private String machineSheduleName;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date scheduleDate;
	
	private String machineName;
	private String machineShiftName;
	
	private Long machineTypeId;
	private Long machineId;
	private Long machineShiftId;
	private String uomName;
	
	private int activeStatus;
	
	private String shiftName;
	
	
	private String startTime;
	private String endTime;
	
	
	
	
	//getter and setter 
	//creator:sajib 
	//date:1/6/2019
	
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getMachineSheduleId() {
		return machineSheduleId;
	}
	public void setMachineSheduleId(Long machineSheduleId) {
		this.machineSheduleId = machineSheduleId;
	}
	public String getMachineSheduleName() {
		return machineSheduleName;
	}
	public void setMachineSheduleName(String machineSheduleName) {
		this.machineSheduleName = machineSheduleName;
	}

	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getMachineShiftName() {
		return machineShiftName;
	}
	public void setMachineShiftName(String machineShiftName) {
		this.machineShiftName = machineShiftName;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public Long getMachineShiftId() {
		return machineShiftId;
	}
	public void setMachineShiftId(Long machineShiftId) {
		this.machineShiftId = machineShiftId;
	}
	public Long getMachineTypeId() {
		return machineTypeId;
	}
	public void setMachineTypeId(Long machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

}
