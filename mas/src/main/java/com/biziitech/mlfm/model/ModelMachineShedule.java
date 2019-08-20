package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name="MLFM_MACHINE_SCHEDULE")
public class ModelMachineShedule {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="MACHINE_SCHEDULE_ID")
	private Long machineScheduleId;
	
	@Column(name="MACHINE_SCHEDULE_NAME")
	private String machineScheduleName;
	
	@ManyToOne
	@JoinColumn(name="MACHINE_SHIFT_ID")
	private ModelMachineShift modelMachineShfit;
	
	@Column(name="SCHEDULE_DATE")
	private Date scheduleDate;
	
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private Long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	@ColumnDefault("0")
	private Long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimestamp;
	
	@Column(name="FLEX_FIELD1")
	private String flex1  ;
	
	@Column(name="FLEX_FIELD2")
	private String flex2  ;
	
	@Column(name="FLEX_FIELD3")
	private String flex3  ;
	
	@Column(name="FLEX_FIELD4")
	private String flex4  ;
	
	@Column(name="FLEX_FIELD5")
	private String flex5  ;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
    public ModelMachineShedule() {
    	
    }

	public Long getMachineScheduleId() {
		return machineScheduleId;
	}

	public String getMachineScheduleName() {
		return machineScheduleName;
	}

	public ModelMachineShift getModelMachineShfit() {
		return modelMachineShfit;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public Long getEnteredBy() {
		return enteredBy;
	}

	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public String getFlex1() {
		return flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public String getFlex4() {
		return flex4;
	}

	public String getFlex5() {
		return flex5;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setMachineScheduleId(Long machineScheduleId) {
		this.machineScheduleId = machineScheduleId;
	}

	public void setMachineScheduleName(String machineScheduleName) {
		this.machineScheduleName = machineScheduleName;
	}

	public void setModelMachineShfit(ModelMachineShift modelMachineShfit) {
		this.modelMachineShfit = modelMachineShfit;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setEnteredBy(Long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public void setFlex4(String flex4) {
		this.flex4 = flex4;
	}

	public void setFlex5(String flex5) {
		this.flex5 = flex5;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	@Override
	public String toString() {
		return "ModelMachineShedule [machineScheduleId=" + machineScheduleId + ", machineScheduleName="
				+ machineScheduleName + ", modelMachineShfit=" + modelMachineShfit + ", scheduleDate=" + scheduleDate
				+ ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy
				+ ", entryTimestamp=" + entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestamp="
				+ updateTimestamp + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	
}
