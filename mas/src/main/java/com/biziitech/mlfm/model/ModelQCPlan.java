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

@Entity(name="MLFM_QC_PLAN")
public class ModelQCPlan {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="QC_PLAN_ID")
	private Long qCPlanId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="QC_PLAN_DATE")
	private Date qCPlanDate;
	
	
	@ManyToOne
	@JoinColumn(name="PO_ID")
	private ModelPO modelPO;
	
	
	@Column(name="QC_PLAN_QTY")
	private Double qCPlanQty;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
		
	@Column(name="REMARKS")
	private String qCPlanRemarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
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
	@JoinColumn(name="PO_MST_ID")
	private ModelPOMst modelPOMst;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getqCPlanId() {
		return qCPlanId;
	}

	public void setqCPlanId(Long qCPlanId) {
		this.qCPlanId = qCPlanId;
	}

	public Date getqCPlanDate() {
		return qCPlanDate;
	}

	public void setqCPlanDate(Date qCPlanDate) {
		this.qCPlanDate = qCPlanDate;
	}

	public ModelPO getModelPO() {
		return modelPO;
	}

	public void setModelPO(ModelPO modelPO) {
		this.modelPO = modelPO;
	}

	public Double getqCPlanQty() {
		return qCPlanQty;
	}

	public void setqCPlanQty(Double qCPlanQty) {
		this.qCPlanQty = qCPlanQty;
	}

	public String getqCPlanRemarks() {
		return qCPlanRemarks;
	}

	public void setqCPlanRemarks(String qCPlanRemarks) {
		this.qCPlanRemarks = qCPlanRemarks;
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

	public ModelUOM getModelUOM() {
		return modelUOM;
	}

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
	}


	public ModelPOMst getModelPOMst() {
		return modelPOMst;
	}

	public void setModelPOMst(ModelPOMst modelPOMst) {
		this.modelPOMst = modelPOMst;
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
		return "ModelQCPlan [qCPlanId=" + qCPlanId + ", qCPlanDate=" + qCPlanDate + ", modelPO=" + modelPO
				+ ", qCPlanQty=" + qCPlanQty + ", modelUOM=" + modelUOM + ", qCPlanRemarks=" + qCPlanRemarks
				+ ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimeStamp=" + entryTimeStamp
				+ ", updatedBy=" + updatedBy + ", updateTimeStamp=" + updateTimeStamp + ", flex1=" + flex1 + ", flex2="
				+ flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", modelPOMst=" + modelPOMst
				+ ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	

}
