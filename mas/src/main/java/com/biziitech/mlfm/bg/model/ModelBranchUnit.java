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

@Entity(name="BG_BRANCH_UNIT")
public class ModelBranchUnit {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="BRANCH_UNIT_ID")
	private Long branchUnitId;
	
	@Column(name="UNIT_NAME")
	private String unitName;
	
	@Column(name="SHORT_CODE")
	private String shortCode;
	
	@Column(name="UNIT_LABEL")
	private int unitLabel;
	
	@ManyToOne
	@JoinColumn(name="BRANCH_ID")
	private ModelBranch branchId;

	@Column(name="PARENT_BRANCH_UNIT_ID")
	private Long parentUnitId;
	
	@Column(name="LEVEL_ID")
	private int levelId;
	
	@Column(name="REMARKS")
	private String branchUnitRemarks;
	
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

	public Long getBranchUnitId() {
		return branchUnitId;
	}

	public void setBranchUnitId(Long branchUnitId) {
		this.branchUnitId = branchUnitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public int getUnitLabel() {
		return unitLabel;
	}

	public void setUnitLabel(int unitLabel) {
		this.unitLabel = unitLabel;
	}
	
	
	
	

	public ModelBranch getBranchId() {
		return branchId;
	}

	public void setBranchId(ModelBranch branchId) {
		this.branchId = branchId;
	}

	public Long getParentUnitId() {
		return parentUnitId;
	}

	public void setParentUnitId(Long parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getBranchUnitRemarks() {
		return branchUnitRemarks;
	}

	public void setBranchUnitRemarks(String branchUnitRemarks) {
		this.branchUnitRemarks = branchUnitRemarks;
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
