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



@Entity(name="MLFM_QC")
public class ModelQC {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="QC_ID")
	private Long qCId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="QC_DATE")
	private Date qCDate;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
	
	@Column(name="QC_QTY")
	private Double qCQty;
		
	@Column(name="QC_BY")
	private Long qCBy;
	
	@Column(name="REMARKS")
	private String qCRemarks;
	
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
	@JoinColumn(name="QC_PLAN_ID")
	private ModelQCPlan modelQCPlan;
	
	
	@Column(name="bed_no")
	private Double bedNo;
	
	
	@Column(name="mending_required_qty	")
	private Double mendingRequiredQty	;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
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

	public Long getqCId() {
		return qCId;
	}

	public void setqCId(Long qCId) {
		this.qCId = qCId;
	}

	public Date getqCDate() {
		return qCDate;
	}

	public void setqCDate(Date qCDate) {
		this.qCDate = qCDate;
	}

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	

	public Double getqCQty() {
		return qCQty;
	}

	public void setqCQty(Double qCQty) {
		this.qCQty = qCQty;
	}


	

	public String getqCRemarks() {
		return qCRemarks;
	}

	public void setqCRemarks(String qCRemarks) {
		this.qCRemarks = qCRemarks;
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

	public Long getqCBy() {
		return qCBy;
	}

	public void setqCBy(Long qCBy) {
		this.qCBy = qCBy;
	}

	public Double getBedNo() {
		return bedNo;
	}

	public void setBedNo(Double bedNo) {
		this.bedNo = bedNo;
	}

	public Double getMendingRequiredQty() {
		return mendingRequiredQty;
	}

	public void setMendingRequiredQty(Double mendingRequiredQty) {
		this.mendingRequiredQty = mendingRequiredQty;
	}

	public ModelQCPlan getModelQCPlan() {
		return modelQCPlan;
	}

	public void setModelQCPlan(ModelQCPlan modelQCPlan) {
		this.modelQCPlan = modelQCPlan;
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
		return "ModelQC [qCId=" + qCId + ", qCDate=" + qCDate + ", modelProduction=" + modelProduction + ", qCQty="
				+ qCQty + ", qCBy=" + qCBy + ", qCRemarks=" + qCRemarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimeStamp=" + entryTimeStamp
				+ ", updateTimeStamp=" + updateTimeStamp + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3
				+ ", flex4=" + flex4 + ", flex5=" + flex5 + ", modelQCPlan=" + modelQCPlan + ", bedNo=" + bedNo
				+ ", mendingRequiredQty=" + mendingRequiredQty + ", modelUOM=" + modelUOM + ", active=" + active
				+ ", sActive=" + sActive + "]";
	}

	


	

	
	

}
