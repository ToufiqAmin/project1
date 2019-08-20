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

import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_DELIVER_CHALLAN")
public class ModelDeliveryChallan {

	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="DELIVER_CHALLAN_ID")
	private Long deliverChallanId;
	
	@Column(name="USER_CHALLAN_NO")
	private String userChallanNo;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="CHALLAN_DATE")
	private Date challanDate;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ModelItem modelItem;
	
	
	@ManyToOne
	@JoinColumn(name="PO_MST_ID")
	private ModelPOMst modelPOMst;
	
	@Column(name="CHALLAN_QTY")
	private Double challanQty;
	
		
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SIZE")
	private String size;
	
	@Column(name="GRADE")
	private String grade;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="DELIVER_DATE")
	private Date deliverDate;
	
	
	@ManyToOne
	@JoinColumn(name="DELIVER_BY")
	private ModelUser modelUser;
	
	
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
	
	@Transient
	private boolean active;
	
	
	@Transient
	private String sActive;


	 public ModelDeliveryChallan() {
	    	
	    }


	public Long getDeliverChallanId() {
		return deliverChallanId;
	}


	public String getUserChallanNo() {
		return userChallanNo;
	}


	public Date getChallanDate() {
		return challanDate;
	}


	public ModelItem getModelItem() {
		return modelItem;
	}


	public ModelPOMst getModelPOMst() {
		return modelPOMst;
	}


	public Double getChallanQty() {
		return challanQty;
	}


	public ModelUOM getModelUOM() {
		return modelUOM;
	}


	public String getDescription() {
		return description;
	}


	public String getSize() {
		return size;
	}


	public String getGrade() {
		return grade;
	}


	public Date getDeliverDate() {
		return deliverDate;
	}


	public ModelUser getModelUser() {
		return modelUser;
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


	public boolean isActive() {
		return active;
	}


	public String getsActive() {
		return sActive;
	}


	public void setDeliverChallanId(Long deliverChallanId) {
		this.deliverChallanId = deliverChallanId;
	}


	public void setUserChallanNo(String userChallanNo) {
		this.userChallanNo = userChallanNo;
	}


	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}


	public void setModelItem(ModelItem modelItem) {
		this.modelItem = modelItem;
	}


	public void setModelPOMst(ModelPOMst modelPOMst) {
		this.modelPOMst = modelPOMst;
	}


	public void setChallanQty(Double challanQty) {
		this.challanQty = challanQty;
	}


	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}


	public void setModelUser(ModelUser modelUser) {
		this.modelUser = modelUser;
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


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setsActive(String sActive) {
		this.sActive = sActive;
	}


	@Override
	public String toString() {
		return "ModelDeliveryChallan [deliverChallanId=" + deliverChallanId + ", userChallanNo=" + userChallanNo
				+ ", challanDate=" + challanDate + ", modelItem=" + modelItem + ", modelPOMst=" + modelPOMst
				+ ", challanQty=" + challanQty + ", modelUOM=" + modelUOM + ", description=" + description + ", size="
				+ size + ", grade=" + grade + ", deliverDate=" + deliverDate + ", modelUser=" + modelUser + ", remarks="
				+ remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp="
				+ entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestap=" + updateTimestap + ", flex1="
				+ flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
				+ ", active=" + active + ", sActive=" + sActive + "]";
	}
	 
	 
	 
}
