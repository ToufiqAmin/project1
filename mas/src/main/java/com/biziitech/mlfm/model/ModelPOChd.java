package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_PO_CHD")
public class ModelPOChd {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PO_CHD_ID")
	private Long pOChdId;
	
	@ManyToOne
	@JoinColumn(name="PO_MST_ID")
	private ModelPOMst modelPOMst;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="PO_CHD_DATE")
	private Date pOChdDate;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_PLAN_CHD_ID")
	private ModelProductionPlanChd modelProductionPlanChd;
	
	@ManyToOne
	@JoinColumn(name="MACHINE_TYPE_ID")
	private ModelMachineType modelMachineType;
	
	@ManyToOne
	@JoinColumn(name="MACHINE_SHIFT_ID")
	private ModelMachineShift modelMachineShift;
	
	@Column(name="PO_QTY")
	private Double pOQty;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	
	@Column(name="NO_OF_BED")
	private Integer noOfBed;
	
	@Column(name="DELIVER_DATE")
	private Integer deliverDate;
	
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
	
	
	@Column(name="FLEX_FIELD1")
	private String flex1  ;
	
	@Column(name="FLEX_FIELD2")
	private String flex2  ;
	
	@Column(name="FLEX_FIELD3")
	private String flex3  ;

	public Long getpOChdId() {
		return pOChdId;
	}

	public void setpOChdId(Long pOChdId) {
		this.pOChdId = pOChdId;
	}

	public ModelPOMst getModelPOMst() {
		return modelPOMst;
	}

	public void setModelPOMst(ModelPOMst modelPOMst) {
		this.modelPOMst = modelPOMst;
	}

	

	public ModelProductionPlanChd getModelProductionPlanChd() {
		return modelProductionPlanChd;
	}

	public void setModelProductionPlanChd(ModelProductionPlanChd modelProductionPlanChd) {
		this.modelProductionPlanChd = modelProductionPlanChd;
	}

	public ModelMachineType getModelMachineType() {
		return modelMachineType;
	}

	public void setModelMachineType(ModelMachineType modelMachineType) {
		this.modelMachineType = modelMachineType;
	}

	public ModelMachineShift getModelMachineShift() {
		return modelMachineShift;
	}

	public void setModelMachineShift(ModelMachineShift modelMachineShift) {
		this.modelMachineShift = modelMachineShift;
	}

	public Double getpOQty() {
		return pOQty;
	}

	public void setpOQty(Double pOQty) {
		this.pOQty = pOQty;
	}

	public ModelUOM getModelUOM() {
		return modelUOM;
	}

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
	}

	public Integer getNoOfBed() {
		return noOfBed;
	}

	public void setNoOfBed(Integer noOfBed) {
		this.noOfBed = noOfBed;
	}

	public Integer getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Integer deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
		return "ModelPOChd [pOChdId=" + pOChdId + ", modelPOMst=" + modelPOMst + ", pOChdDate=" + pOChdDate
				+ ", modelProductionPlanChd=" + modelProductionPlanChd + ", modelMachineType=" + modelMachineType
				+ ", modelMachineShift=" + modelMachineShift + ", pOQty=" + pOQty + ", modelUOM=" + modelUOM
				+ ", noOfBed=" + noOfBed + ", deliverDate=" + deliverDate + ", remarks=" + remarks + ", activeStatus="
				+ activeStatus + ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimeStamp="
				+ entryTimeStamp + ", updateTimeStamp=" + updateTimeStamp + ", flex1=" + flex1 + ", flex2=" + flex2
				+ ", flex3=" + flex3 + "]";
	}

	public Date getpOChdDate() {
		return pOChdDate;
	}

	public void setpOChdDate(Date pOChdDate) {
		this.pOChdDate = pOChdDate;
	}
	
	
	

}
