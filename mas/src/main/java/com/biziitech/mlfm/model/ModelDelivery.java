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

@Entity(name="MLFM_DELIVER")
public class ModelDelivery {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="DELIVER_ID")
	private Long deliverId;
	
	@ManyToOne
	@JoinColumn(name="GATE_PASS_CHD_ID")
	private ModelGatePassChd modelGatePassChd;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="DELIVER_DATE")
	private Date deliverDate;
	
	
	@Column(name="DELIVER_QTY")
	private Double deliverQty;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	private long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimestap;
	
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
	
	@Column(name="DELIVER_CHALLAN_NO")
	private String deliverChallanNo;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ModelItem modelItem;
	
	@ManyToOne
	@JoinColumn(name="DELIVER_CHALLAN_ID")
	private ModelDeliveryChallan modelDeliveryChallan;
	
	
	@Transient
	private boolean active;
	
	
	@Transient
	private String sActive;


	 public ModelDelivery() {
	    	
	    }


	public Long getDeliverId() {
		return deliverId;
	}


	public ModelGatePassChd getModelGatePassChd() {
		return modelGatePassChd;
	}


	public Date getDeliverDate() {
		return deliverDate;
	}


	public Double getDeliverQty() {
		return deliverQty;
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


	public long getUpdatedBy() {
		return updatedBy;
	}


	public Date getUpdateTimestap() {
		return updateTimestap;
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


	public String getDeliverChallanNo() {
		return deliverChallanNo;
	}


	public ModelItem getModelItem() {
		return modelItem;
	}


	public ModelDeliveryChallan getModelDeliveryChallan() {
		return modelDeliveryChallan;
	}


	public boolean isActive() {
		return active;
	}


	public String getsActive() {
		return sActive;
	}


	public void setDeliverId(Long deliverId) {
		this.deliverId = deliverId;
	}


	public void setModelGatePassChd(ModelGatePassChd modelGatePassChd) {
		this.modelGatePassChd = modelGatePassChd;
	}


	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}


	public void setDeliverQty(Double deliverQty) {
		this.deliverQty = deliverQty;
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


	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}


	public void setUpdateTimestap(Date updateTimestap) {
		this.updateTimestap = updateTimestap;
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


	public void setDeliverChallanNo(String deliverChallanNo) {
		this.deliverChallanNo = deliverChallanNo;
	}


	public void setModelItem(ModelItem modelItem) {
		this.modelItem = modelItem;
	}


	public void setModelDeliveryChallan(ModelDeliveryChallan modelDeliveryChallan) {
		this.modelDeliveryChallan = modelDeliveryChallan;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setsActive(String sActive) {
		this.sActive = sActive;
	}


	@Override
	public String toString() {
		return "ModelDelivery [deliverId=" + deliverId + ", modelGatePassChd=" + modelGatePassChd + ", deliverDate="
				+ deliverDate + ", deliverQty=" + deliverQty + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", entryTimestamp=" + entryTimestamp + ", updatedBy=" + updatedBy
				+ ", updateTimestap=" + updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3
				+ ", flex4=" + flex4 + ", flex5=" + flex5 + ", deliverChallanNo=" + deliverChallanNo + ", modelItem="
				+ modelItem + ", modelDeliveryChallan=" + modelDeliveryChallan + ", active=" + active + ", sActive="
				+ sActive + "]";
	}
	 
	 
	
}
