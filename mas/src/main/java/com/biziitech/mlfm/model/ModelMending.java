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

@Entity(name="MLFM_MENDING")
public class ModelMending {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="MENDING_ID")
	private Long mendingId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="MENDING_DATE")
	private Date mendingDate;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
	
	@Column(name="MENDED_QTY")
	private Double mendedQty;
		
	@Column(name="MENDED_BY")
	private Long mendedBy;
	
	@Column(name="REMARKS")
	private String mendedRemarks;
	
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
	@JoinColumn(name="MENDING_PLAN_ID")
	private ModelMendingPlan modelMendingPlan;
	
	@ManyToOne
	@JoinColumn(name="QC_ID")
	private ModelQC modelQC;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getMendingId() {
		return mendingId;
	}

	public void setMendingId(Long mendingId) {
		this.mendingId = mendingId;
	}

	public Date getMendingDate() {
		return mendingDate;
	}

	public void setMendingDate(Date mendingDate) {
		this.mendingDate = mendingDate;
	}	

	public Double getMendedQty() {
		return mendedQty;
	}

	public void setMendedQty(Double mendedQty) {
		this.mendedQty = mendedQty;
	}

	public Long getMendedBy() {
		return mendedBy;
	}

	public void setMendedBy(Long mendedBy) {
		this.mendedBy = mendedBy;
	}

	public String getMendedRemarks() {
		return mendedRemarks;
	}

	public void setMendedRemarks(String mendedRemarks) {
		this.mendedRemarks = mendedRemarks;
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

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	public ModelMendingPlan getModelMendingPlan() {
		return modelMendingPlan;
	}

	public void setModelMendingPlan(ModelMendingPlan modelMendingPlan) {
		this.modelMendingPlan = modelMendingPlan;
	}

	public ModelQC getModelQC() {
		return modelQC;
	}

	public void setModelQC(ModelQC modelQC) {
		this.modelQC = modelQC;
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
		return "ModelMending [mendingId=" + mendingId + ", mendingDate=" + mendingDate + ", modelProduction="
				+ modelProduction + ", mendedQty=" + mendedQty + ", mendedBy=" + mendedBy + ", mendedRemarks="
				+ mendedRemarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", updatedBy="
				+ updatedBy + ", entryTimeStamp=" + entryTimeStamp + ", updateTimeStamp=" + updateTimeStamp + ", flex1="
				+ flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
				+ ", modelMendingPlan=" + modelMendingPlan + ", modelQC=" + modelQC + ", modelUOM=" + modelUOM
				+ ", active=" + active + ", sActive=" + sActive + "]";
	}

	


	
	
	
	

}
