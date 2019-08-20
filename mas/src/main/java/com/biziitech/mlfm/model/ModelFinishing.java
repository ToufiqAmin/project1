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

@Entity(name="MLFM_FINISHING")
public class ModelFinishing {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="FINISHING_ID")
	private Long finishingId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="FINISHING_DATE")
	private Date finishingDate;
	
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
				
	@Column(name="FINISHED_QTY")
	private Double finishedQty;
		
	@Column(name="FINISHED_BY")
	private Long finishedBy;
	
	@Column(name="REMARKS")
	private String finishedRemarks;
	
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
	@JoinColumn(name="FINISHING_PLAN_ID")
	private ModelFinishingPlan modelFinishingPlan;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getFinishingId() {
		return finishingId;
	}

	public void setFinishingId(Long finishingId) {
		this.finishingId = finishingId;
	}

	public Date getFinishingDate() {
		return finishingDate;
	}

	public void setFinishingDate(Date finishingDate) {
		this.finishingDate = finishingDate;
	}

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	public Double getFinishedQty() {
		return finishedQty;
	}

	public void setFinishedQty(Double finishedQty) {
		this.finishedQty = finishedQty;
	}

	public Long getFinishedBy() {
		return finishedBy;
	}

	public void setFinishedBy(Long finishedBy) {
		this.finishedBy = finishedBy;
	}

	public String getFinishedRemarks() {
		return finishedRemarks;
	}

	public void setFinishedRemarks(String finishedRemarks) {
		this.finishedRemarks = finishedRemarks;
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

	public ModelFinishingPlan getModelFinishingPlan() {
		return modelFinishingPlan;
	}

	public void setModelFinishingPlan(ModelFinishingPlan modelFinishingPlan) {
		this.modelFinishingPlan = modelFinishingPlan;
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

	


}
