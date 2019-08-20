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
@Entity(name="MLFM_PRODUCTION_PLAN_MST")
public class ModelProductionPlanMst {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PRODUCTION_PLAN_MST_ID")
	private Long productionPlanMstId;
	
	// 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="PLAN_DATE")
	private Date planDate;
	
	@ManyToOne
	@JoinColumn(name="PLANNED_BY")
	private ModelUser modelUser;
	
	@Column(name="REF_NO")
	private String refNo;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ITEM_QTY_ID")
	private ModelOrderItemQty modelOrderItemQty;
	
	@ManyToOne
	@JoinColumn(name="WO_CHD_ID")
	private ModelWOChd modelWOChd;
	
//	@ManyToOne
//	@JoinColumn(name="ORDER_ID")
//	private ModelOrder modelOrder;
	
	/*@ManyToOne
	@JoinColumn(name="DESIGN_ID")
	private ModelDesign modelDesign;
	*/
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	

	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
	
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	
	
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

	public Long getProductionPlanMstId() {
		return productionPlanMstId;
	}

	public void setProductionPlanMstId(Long productionPlanMstId) {
		this.productionPlanMstId = productionPlanMstId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	
	public ModelUser getModelUser() {
		return modelUser;
	}

	public void setModelUser(ModelUser modelUser) {
		this.modelUser = modelUser;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

//	public ModelOrder getModelOrder() {
//		return modelOrder;
//	}
//
//	public void setModelOrder(ModelOrder modelOrder) {
//		this.modelOrder = modelOrder;
//	}

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	

	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}



	public Date getUpdateTimestap() {
		return updateTimestap;
	}

	public void setUpdateTimestap(Date updateTimestap) {
		this.updateTimestap = updateTimestap;
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

	public ModelOrderItemQty getModelOrderItemQty() {
		return modelOrderItemQty;
	}

	public void setModelOrderItemQty(ModelOrderItemQty modelOrderItemQty) {
		this.modelOrderItemQty = modelOrderItemQty;
	}

	public ModelWOChd getModelWOChd() {
		return modelWOChd;
	}

	public void setModelWOChd(ModelWOChd modelWOChd) {
		this.modelWOChd = modelWOChd;
	}

	

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
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
		return "ModelProductionPlanMst [productionPlanMstId=" + productionPlanMstId + ", planDate=" + planDate
				+ ", modelUser=" + modelUser + ", refNo=" + refNo + ", modelOrderItemQty=" + modelOrderItemQty
				+ ", modelWOChd=" + modelWOChd + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimestamp=" + entryTimestamp
				+ ", updateTimestap=" + updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3
				+ ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	

 


}
