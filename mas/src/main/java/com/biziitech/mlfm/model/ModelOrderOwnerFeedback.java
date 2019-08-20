package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="MLFM_ORDER_OWNER_FEEDBACK")
public class ModelOrderOwnerFeedback {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="ORDER_OWNER_FEEDBACK_ID")
	private Long orderOwnerFeedbackId;
		
	@ManyToOne
	@JoinColumn(name="ORDER_ITEM_QTY_ID")
	private ModelOrderItemQty modelOrderItemQty;
	
	@Column(name="ORDER_STATUS")
	private int orderStatus;
	
	@Column(name="CANCEL_REASON")
	private String cancelReason;
	
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	//@ColumnDefault("0")
	private Long updatedBy;
	
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
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="FEEDBACK_DATE")
	private Date feedbackDate;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getOrderOwnerFeedbackId() {
		return orderOwnerFeedbackId;
	}

	public ModelOrderItemQty getModelOrderItemQty() {
		return modelOrderItemQty;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public long getEnteredBy() {
		return enteredBy;
	}

	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
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

	public Date getFeedbackDate() {
		return feedbackDate;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setOrderOwnerFeedbackId(Long orderOwnerFeedbackId) {
		this.orderOwnerFeedbackId = orderOwnerFeedbackId;
	}

	public void setModelOrderItemQty(ModelOrderItemQty modelOrderItemQty) {
		this.modelOrderItemQty = modelOrderItemQty;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setEnteredBy(long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
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

	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	@Override
	public String toString() {
		return "ModelOrderOwnerFeedback [orderOwnerFeedbackId=" + orderOwnerFeedbackId + ", modelOrderItemQty="
				+ modelOrderItemQty + ", orderStatus=" + orderStatus + ", cancelReason=" + cancelReason + ", remarks="
				+ remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp="
				+ entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestamp=" + updateTimestamp + ", flex1="
				+ flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
				+ ", feedbackDate=" + feedbackDate + ", active=" + active + ", sActive=" + sActive + "]";
	}
	
    
	
}
