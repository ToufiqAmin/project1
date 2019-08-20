package com.biziitech.mlfm.bg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="BG_BRANCH_UNIT_LOCATION")
public class ModelBranchUnitLoc {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="BRANCH_UNIT_LOC_ID")
	private Long branchUnitLocId;
	
	@Column(name="LOCATION_NAME")
	private String locationName;
	
	@Column(name="SHORT_CODE")
	private String shortCode;
	
	@Column(name="ROOM_ID")
	private Long roomId;
	
	@ManyToOne
	@JoinColumn(name="BRANCH_UNIT_ID")
	private ModelBranchUnit branchUnitId;
	
	@Column(name="REMARKS")
	private String branchUnitLocRemarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	
	@Column(name="UPDATED_BY")
	private long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP")
	private Date updateTimeStamp;
	
	@Column(name="FLEX_FIELD1")
	private String flexField1;
	
	@Column(name="FLEX_FIELD2")
	private String flexField2;
	
	@Column(name="FLEX_FIELD3")
	private String flexField3;
	
	@Column(name="FLEX_FIELD4")
	private String flexField4;
	
	@Column(name="FLEX_FIELD5")
	private String flexField5;
	
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getBranchUnitLocId() {
		return branchUnitLocId;
	}

	public void setBranchUnitLocId(Long branchUnitLocId) {
		this.branchUnitLocId = branchUnitLocId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public ModelBranchUnit getBranchUnitId() {
		return branchUnitId;
	}

	public void setBranchUnitId(ModelBranchUnit branchUnitId) {
		this.branchUnitId = branchUnitId;
	}

	public String getBranchUnitLocRemarks() {
		return branchUnitLocRemarks;
	}

	public void setBranchUnitLocRemarks(String branchUnitLocRemarks) {
		this.branchUnitLocRemarks = branchUnitLocRemarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public long getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public String getFlexField1() {
		return flexField1;
	}

	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}

	public String getFlexField2() {
		return flexField2;
	}

	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}

	public String getFlexField3() {
		return flexField3;
	}

	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}

	public String getFlexField4() {
		return flexField4;
	}

	public void setFlexField4(String flexField4) {
		this.flexField4 = flexField4;
	}

	public String getFlexField5() {
		return flexField5;
	}

	public void setFlexField5(String flexField5) {
		this.flexField5 = flexField5;
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
	
	
	
	
	

}
