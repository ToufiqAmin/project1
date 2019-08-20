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

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_DESIGN")
public class ModelDesign {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="DESIGN_ID")
	private Long designId;
	
	@Column(name="DESIGN_NAME")
	private String designName;
	
	@Column(name="USER_DESIGN_NO")
	private String userDesignNo;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	private ModelOrder modelOrder;
	
	
	
	@Column(name="DESIGNER_ID")
	private Long designerId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="DESIGN_DATE")
	private Date designDate;
	
	@Column(name="NO_OF_STITCHES")
	private Integer noOfStitches;
	
	@Column(name="DTM")
	private String dTM;
	
	@ManyToOne
	@JoinColumn(name="FABRIC_TYPE_ID")
	private ModelFabricType modelFabricType;
	
	@Column(name="lace_type_id")
	private int laceTypeId;
	
	
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
	
	@ManyToOne
	@JoinColumn(name="MACHINE_TYPE")
	private ModelMachineType modelMachineType;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="FACTORY_PROPOSED_DELIVERY_DATE")
	private Date factPropDeliveryDate;
	
	
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
	
	public String getDesign_Date() {
		return design_Date;
	}

	public void setDesign_Date(String design_Date) {
		this.design_Date = design_Date;
	}

	public String getFactoryDate() {
		return factoryDate;
	}

	public void setFactoryDate(String factoryDate) {
		this.factoryDate = factoryDate;
	}

	@Transient
	private String design_Date;
	
	@Transient
	private String factoryDate;
    public ModelDesign() {
    	
    }

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
	}

	public String getDesignName() {
		return designName;
	}

	public void setDesignName(String designName) {
		this.designName = designName;
	}

	public String getUserDesignNo() {
		return userDesignNo;
	}

	public void setUserDesignNo(String userDesignNo) {
		this.userDesignNo = userDesignNo;
	}


	
	public Date getDesignDate() {
		return designDate;
	}

	public void setDesignDate(Date designDate) {
		this.designDate = designDate;
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



	public ModelOrder getModelOrder() {
		return modelOrder;
	}

	public void setModelOrder(ModelOrder modelOrder) {
		this.modelOrder = modelOrder;
	}

	
	
	

	public Date getFactPropDeliveryDate() {
		return factPropDeliveryDate;
	}

	public void setFactPropDeliveryDate(Date factPropDeliveryDate) {
		this.factPropDeliveryDate = factPropDeliveryDate;
	}
	
	



	public Long getDesignerId() {
		return designerId;
	}

	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}

	
	public String getdTM() {
		return dTM;
	}

	public void setdTM(String dTM) {
		this.dTM = dTM;
	}

	public ModelFabricType getModelFabricType() {
		return modelFabricType;
	}

	public void setModelFabricType(ModelFabricType modelFabricType) {
		this.modelFabricType = modelFabricType;
	}

	

	public ModelMachineType getModelMachineType() {
		return modelMachineType;
	}

	public void setModelMachineType(ModelMachineType modelMachineType) {
		this.modelMachineType = modelMachineType;
	}
	
	public int getLaceTypeId() {
		return laceTypeId;
	}

	public void setLaceTypeId(int laceTypeId) {
		this.laceTypeId = laceTypeId;
	}

	public Integer getNoOfStitches() {
		return noOfStitches;
	}

	public void setNoOfStitches(Integer noOfStitches) {
		this.noOfStitches = noOfStitches;
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
		return "ModelDesign [designId=" + designId + ", designName=" + designName + ", userDesignNo=" + userDesignNo
				+ ", modelOrder=" + modelOrder + ", designerId=" + designerId + ", designDate=" + designDate
				+ ", noOfStitches=" + noOfStitches + ", dTM=" + dTM + ", modelFabricType=" + modelFabricType
				+ ", laceTypeId=" + laceTypeId + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimestamp=" + entryTimestamp
				+ ", updateTimestap=" + updateTimestap + ", modelMachineType=" + modelMachineType
				+ ", factPropDeliveryDate=" + factPropDeliveryDate + ", flex1=" + flex1 + ", flex2=" + flex2
				+ ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive="
				+ sActive + ", design_Date=" + design_Date + ", factoryDate=" + factoryDate + "]";
	}



	

}
