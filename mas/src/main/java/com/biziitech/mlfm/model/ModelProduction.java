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

@Entity(name="MLFM_PRODUCTION")
public class ModelProduction {
	
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PRODUCTION_ID")
	private Long productionId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="PRODUCTION_DATE")
	private Date productionDate;
	
	
	@Column(name="PRODUCTION_REF_NO")
	private String productionRefNo;
	

	@ManyToOne
	@JoinColumn(name="PRODUCTION_PLAN_CHD_ID")
	private  ModelProductionPlanChd ModelProductionPlanChd;
	
	
	@ManyToOne
	@JoinColumn(name="WO_CHD_ID")
	private  ModelWOChd modelWOChd;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ITEM_QTY_ID")
	private  ModelOrderItemQty ModelOrderItemQty;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	private ModelOrder modelOrder;
	
	@ManyToOne
	@JoinColumn(name="DESIGN_ID")
	private ModelDesign modelDesign;
	
	@ManyToOne
	@JoinColumn(name="MACHINE_SHIFT_ID")
	private ModelMachineShift modelMachineShift;
	
	@ManyToOne
	@JoinColumn(name="PO_MST_ID")
	private ModelPOMst modelPOMst;
	
	@Column(name="PRODUCTION_QTY")
	private Double productionQty;
	
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
	private Date entryTimeStamp;
	
	
	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimeStamp;
	
	
	@Column(name="NO_OF_STITCHES")
	private Integer noOfStitches;
	
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

	public Long getProductionId() {
		return productionId;
	}

	public void setProductionId(Long productionId) {
		this.productionId = productionId;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getProductionRefNo() {
		return productionRefNo;
	}

	public void setProductionRefNo(String productionRefNo) {
		this.productionRefNo = productionRefNo;
	}

	public ModelOrder getModelOrder() {
		return modelOrder;
	}
	
	public void setModelOrder(ModelOrder modelOrder) {
		this.modelOrder = modelOrder;
	}

	

	public ModelProductionPlanChd getModelProductionPlanChd() {
		return ModelProductionPlanChd;
	}

	public void setModelProductionPlanChd(ModelProductionPlanChd modelProductionPlanChd) {
		ModelProductionPlanChd = modelProductionPlanChd;
	}



	public ModelDesign getModelDesign() {
		return modelDesign;
	}

	public void setModelDesign(ModelDesign modelDesign) {
		this.modelDesign = modelDesign;
	}

	public ModelMachineShift getModelMachineShift() {
		return modelMachineShift;
	}

	public void setModelMachineShift(ModelMachineShift modelMachineShift) {
		this.modelMachineShift = modelMachineShift;
	}

	public Double getProductionQty() {
		return productionQty;
	}

	public void setProductionQty(Double productionQty) {
		this.productionQty = productionQty;
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

	
	
	public ModelOrderItemQty getModelOrderItemQty() {
		return ModelOrderItemQty;
	}

	public void setModelOrderItemQty(ModelOrderItemQty modelOrderItemQty) {
		ModelOrderItemQty = modelOrderItemQty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ModelWOChd getModelWOChd() {
		return modelWOChd;
	}

	public void setModelWOChd(ModelWOChd modelWOChd) {
		this.modelWOChd = modelWOChd;
	}



	public Integer getNoOfStitches() {
		return noOfStitches;
	}

	public void setNoOfStitches(Integer noOfStitches) {
		this.noOfStitches = noOfStitches;
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
		return "ModelProduction [productionId=" + productionId + ", productionDate=" + productionDate
				+ ", productionRefNo=" + productionRefNo + ", ModelProductionPlanChd=" + ModelProductionPlanChd
				+ ", modelWOChd=" + modelWOChd + ", ModelOrderItemQty=" + ModelOrderItemQty + ", modelOrder="
				+ modelOrder + ", modelDesign=" + modelDesign + ", modelMachineShift=" + modelMachineShift
				+ ", modelPOMst=" + modelPOMst + ", productionQty=" + productionQty + ", remarks=" + remarks
				+ ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy
				+ ", entryTimeStamp=" + entryTimeStamp + ", updateTimeStamp=" + updateTimeStamp + ", noOfStitches="
				+ noOfStitches + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}
	

}
