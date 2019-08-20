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
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_WASHING")
public class ModelWashing {
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="WASHING_ID")
	private Long washingId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="WASHING_DATE")
	private Date washingDate;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
	
	@Column(name="WASHED_QTY")
	private Double washedQty;
		
	@Column(name="WASHED_BY")
	private Long washedBy;
	
	@Column(name="REMARKS")
	private String washedRemarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	
	
	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimeStamp;
	
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
	
	@ManyToOne
	@JoinColumn(name="WASHING_PLAN_ID")
	private ModelWashingPlan modelWashingPlan;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getWashingId() {
		return washingId;
	}

	public void setWashingId(Long washingId) {
		this.washingId = washingId;
	}

	public Date getWashingDate() {
		return washingDate;
	}

	public void setWashingDate(Date washingDate) {
		this.washingDate = washingDate;
	}

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	public Double getWashedQty() {
		return washedQty;
	}

	public void setWashedQty(Double washedQty) {
		this.washedQty = washedQty;
	}

	public Long getWashedBy() {
		return washedBy;
	}

	public void setWashedBy(Long washedBy) {
		this.washedBy = washedBy;
	}

	public String getWashedRemarks() {
		return washedRemarks;
	}

	public void setWashedRemarks(String washedRemarks) {
		this.washedRemarks = washedRemarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	

	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	

	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
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

	public ModelWashingPlan getModelWashingPlan() {
		return modelWashingPlan;
	}

	public void setModelWashingPlan(ModelWashingPlan modelWashingPlan) {
		this.modelWashingPlan = modelWashingPlan;
	}

	public ModelUOM getModelUOM() {
		return modelUOM;
	}

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
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
		return "ModelWashing [washingId=" + washingId + ", washingDate=" + washingDate + ", modelProduction="
				+ modelProduction + ", washedQty=" + washedQty + ", washedBy=" + washedBy + ", washedRemarks="
				+ washedRemarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", updatedBy="
				+ updatedBy + ", entryTimeStamp=" + entryTimeStamp + ", updateTimeStamp=" + updateTimeStamp + ", flex1="
				+ flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
				+ ", modelWashingPlan=" + modelWashingPlan + ", modelUOM=" + modelUOM + ", active=" + active
				+ ", sActive=" + sActive + "]";
	}

	



}
