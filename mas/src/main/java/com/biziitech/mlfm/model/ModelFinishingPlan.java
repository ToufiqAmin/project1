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

@Entity(name="MLFM_FINISHING_PLAN")
public class ModelFinishingPlan {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="FINISHING_PLAN_ID")
	private Long finishingPlanId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="FINISHING_PLAN_DATE")
	private Date finishingPlanDate;
	
	@ManyToOne
	@JoinColumn(name="PO_MST_ID")
	private ModelPOMst modelPOMst;
	
	@Column(name="FINISHING_PLAN_QTY")
	private Double finishingPlanQty;
	
	@Column(name="REMARKS")
	private String finishingPlanRemarks;
	
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
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getFinishingPlanId() {
		return finishingPlanId;
	}

	public Date getFinishingPlanDate() {
		return finishingPlanDate;
	}

	public ModelPOMst getModelPOMst() {
		return modelPOMst;
	}

	public Double getFinishingPlanQty() {
		return finishingPlanQty;
	}

	public String getFinishingPlanRemarks() {
		return finishingPlanRemarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}



	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	
	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
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

	public ModelUOM getModelUOM() {
		return modelUOM;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setFinishingPlanId(Long finishingPlanId) {
		this.finishingPlanId = finishingPlanId;
	}

	public void setFinishingPlanDate(Date finishingPlanDate) {
		this.finishingPlanDate = finishingPlanDate;
	}

	public void setModelPOMst(ModelPOMst modelPOMst) {
		this.modelPOMst = modelPOMst;
	}

	public void setFinishingPlanQty(Double finishingPlanQty) {
		this.finishingPlanQty = finishingPlanQty;
	}

	public void setFinishingPlanRemarks(String finishingPlanRemarks) {
		this.finishingPlanRemarks = finishingPlanRemarks;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	
	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
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

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
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
		return "ModelFinishingPlan [finishingPlanId=" + finishingPlanId + ", finishingPlanDate=" + finishingPlanDate
				+ ", modelPOMst=" + modelPOMst + ", finishingPlanQty=" + finishingPlanQty + ", finishingPlanRemarks="
				+ finishingPlanRemarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", updatedBy="
				+ updatedBy + ", entryTimeStamp=" + entryTimeStamp + ", updateTimeStamp=" + updateTimeStamp + ", flex1="
				+ flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
				+ ", modelUOM=" + modelUOM + ", active=" + active + ", sActive=" + sActive + "]";
	}

	

}
