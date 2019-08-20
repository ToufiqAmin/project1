package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_MACHINE_SHIFT")
public class ModelMachineShift {
	
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="MACHINE_SHIFT_ID")
	private Long machineShiftId;
	
	@Column(name="MACHINE_SHIFT_NAME")
	private String machineShiftName;
	

	
	@ManyToOne
	@JoinColumn(name="SHIFT_ID")
	private ModelShift modelShift;

	
	@ManyToOne
	@JoinColumn(name="MACHINE_ID")
	private ModelMachine modelMachine;
	
	
//	@Column(name="SHIFT_ID")
//	private long shiftId;
	
	
	
	@Column(name="MACHINE_CAPACITY")
	private long machineCapacity;
	
	/*
	@Column(name="CAPACITY_UOM")
	private String capacityUom;
	*/
	
	
	@Column(name="CAPACITY_UOM")
	private Long capacityUom;
	
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
//	@Column(name="ENTERED_BY")
//	private Long enteredBy;
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
//	@Column(name="UPDATED_BY")
//	private Long updatedBy;
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
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

	
	
	
	
	
	public ModelShift getModelShift() {
		return modelShift;
	}

	public void setModelShift(ModelShift modelShift) {
		this.modelShift = modelShift;
	}
	
	public ModelMachine getModelMachine() {
		return modelMachine;
	}

	public void setModelMachine(ModelMachine modelMachine) {
		this.modelMachine = modelMachine;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	public Long getMachineShiftId() {
		return machineShiftId;
	}

	public void setMachineShiftId(Long machineShiftId) {
		this.machineShiftId = machineShiftId;
	}

	public String getMachineShiftName() {
		return machineShiftName;
	}

	public void setMachineShiftName(String machineShiftName) {
		this.machineShiftName = machineShiftName;
	}

	
//
//	public long getShiftId() {
//		return shiftId;
//	}
//
//	public void setShiftId(long shiftId) {
//		this.shiftId = shiftId;
//	}

	public long getMachineCapacity() {
		return machineCapacity;
	}

	public void setMachineCapacity(long machineCapacity) {
		this.machineCapacity = machineCapacity;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	

	public Long getCapacityUom() {
		return capacityUom;
	}

	public void setCapacityUom(Long capacityUom) {
		this.capacityUom = capacityUom;
	}

	

	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	

	

	public String getFlex1() {
		return flex1;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public String getFlex4() {
		return flex4;
	}

	public void setFlex4(String flex4) {
		this.flex4 = flex4;
	}

	public String getFlex5() {
		return flex5;
	}

	public void setFlex5(String flex5) {
		this.flex5 = flex5;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}



	public ModelUser getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(ModelUser enteredBy) {
		this.enteredBy = enteredBy;
	}

	public ModelUser getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(ModelUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ModelMachineShift [machineShiftId=" + machineShiftId + ", machineShiftName=" + machineShiftName
				+ ", modelShift=" + modelShift + ", modelMachine=" + modelMachine + ", machineCapacity="
				+ machineCapacity + ", capacityUom=" + capacityUom + ", remarks=" + remarks + ", activeStatus="
				+ activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp=" + entryTimestamp + ", updatedBy="
				+ updatedBy + ", updateTimestamp=" + updateTimestamp + ", flex1=" + flex1 + ", flex2=" + flex2
				+ ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive="
				+ sActive + "]";
	}

//	public boolean isActive() {
//		return active;
//	}
//
//	public void setActive(boolean active) {
//		this.active = active;
//	}
//
//	public String getsActive() {
//		return sActive;
//	}
//
//	public void setsActive(String sActive) {
//		this.sActive = sActive;
//	}
//
//	@Transient
//	private boolean active;
//	
//	@Transient
//	private String sActive;

}
