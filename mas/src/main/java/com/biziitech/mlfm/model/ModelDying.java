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

@Entity(name="MLFM_DYING")
public class ModelDying {
				
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="DYING_ID")
	private Long dyingId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="DYING_DATE")
	private Date dyingDate;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
				
	@Column(name="DYING_QTY")
	private Double dyingQty;
		
	@Column(name="DYING_BY")
	private Long dyingBy;
	
	@Column(name="REMARKS")
	private String dyingRemarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@ManyToOne
	@JoinColumn(name="DYING_PLAN_ID")
	private ModelDyingPlan modelDyingPlan;
	
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

	public Long getDyingId() {
		return dyingId;
	}

	public void setDyingId(Long dyingId) {
		this.dyingId = dyingId;
	}

	public Date getDyingDate() {
		return dyingDate;
	}

	public void setDyingDate(Date dyingDate) {
		this.dyingDate = dyingDate;
	}

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	public Double getDyingQty() {
		return dyingQty;
	}

	public void setDyingQty(Double dyingQty) {
		this.dyingQty = dyingQty;
	}

	public Long getDyingBy() {
		return dyingBy;
	}

	public void setDyingBy(Long dyingBy) {
		this.dyingBy = dyingBy;
	}

	public String getDyingRemarks() {
		return dyingRemarks;
	}

	public void setDyingRemarks(String dyingRemarks) {
		this.dyingRemarks = dyingRemarks;
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

	public ModelDyingPlan getModelDyingPlan() {
		return modelDyingPlan;
	}

	public void setModelDyingPlan(ModelDyingPlan modelDyingPlan) {
		this.modelDyingPlan = modelDyingPlan;
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
		return "ModelDying [dyingId=" + dyingId + ", dyingDate=" + dyingDate + ", modelProduction=" + modelProduction
				+ ", dyingQty=" + dyingQty + ", dyingBy=" + dyingBy + ", dyingRemarks=" + dyingRemarks
				+ ", activeStatus=" + activeStatus + ", modelDyingPlan=" + modelDyingPlan + ", enteredBy=" + enteredBy
				+ ", updatedBy=" + updatedBy + ", entryTimeStamp=" + entryTimeStamp + ", updateTimeStamp="
				+ updateTimeStamp + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", modelUOM=" + modelUOM + ", active=" + active + ", sActive=" + sActive + "]";
	}
	
}
