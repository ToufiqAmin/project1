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

@Entity(name="MLFM_PO")
public class ModelPO {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PO_ID")
	private Long pOId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="PO_DATE")
	private Date pODate;
	
	@ManyToOne
	@JoinColumn(name="DESIGN_ID")
	private ModelDesign modelDesign;
	
	
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
	
	@Column(name="NO_OF_BED")
	private Integer nOOfBed;
	
	@Column(name="PATCH")
	private String patch;
	
	
	@Column(name="FABRIC_TYPE_ID")
	private int fabricTypeId;
	
	@Column(name="DTM")
	private String dTM;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="DELIVERY_DATE")
	private Date deliveryDate;
	
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private Long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;
	
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
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
	
	
	/*getter and setter
	 * 
	 * 
	 * Creator : SAS
	 * Date : 7/2/19
	 * 
	 * */

	public Long getpOId() {
		return pOId;
	}

	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}

	public Date getpODate() {
		return pODate;
	}

	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}

	public ModelDesign getModelDesign() {
		return modelDesign;
	}

	public void setModelDesign(ModelDesign modelDesign) {
		this.modelDesign = modelDesign;
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

	

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		this.patch = patch;
	}

	public int getFabricTypeId() {
		return fabricTypeId;
	}

	public void setFabricTypeId(int fabricTypeId) {
		this.fabricTypeId = fabricTypeId;
	}

	public String getdTM() {
		return dTM;
	}

	public void setdTM(String dTM) {
		this.dTM = dTM;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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

	public Long getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(Long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	
	public Integer getnOOfBed() {
		return nOOfBed;
	}

	public void setnOOfBed(Integer nOOfBed) {
		this.nOOfBed = nOOfBed;
	}

	@Override
	public String toString() {
		return "ModelPO [pOId=" + pOId + ", pODate=" + pODate + ", modelDesign=" + modelDesign
				+ ", modelProductionPlanChd=" + modelProductionPlanChd + ", modelMachineType=" + modelMachineType
				+ ", modelMachineShift=" + modelMachineShift + ", pOQty=" + pOQty + ", nOOfBed=" + nOOfBed + ", patch="
				+ patch + ", fabricTypeId=" + fabricTypeId + ", dTM=" + dTM + ", deliveryDate=" + deliveryDate
				+ ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy
				+ ", entryTimeStamp=" + entryTimeStamp + ", updatedBy=" + updatedBy + ", updateTimeStamp="
				+ updateTimeStamp + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	

}
