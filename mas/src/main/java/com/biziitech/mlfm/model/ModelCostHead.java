package com.biziitech.mlfm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import com.biziitech.mlfm.bg.model.ModelCurrency;

@Entity(name="MLFM_COST_HEAD")
public class ModelCostHead {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="COST_HEAD_ID")
	private Long costHeadId;
	
	@Column(name="HEAD_NAME")
	private String headName;
	
	@Column(name="SHORT_CODE")
	private String shortCode;
	
	@Column(name="SL_NO")
	private int slNo;
	
	@Column(name="DEFAULT_FLAG")
	private int defaultFlag;
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;
	
	@ManyToOne
	@JoinColumn(name="UOM")
	private ModelUOM modelUOM;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	@ColumnDefault("0")
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
	
	@Transient
	private List<ModelCurrency> modelCurrencyList;
	
    public ModelCostHead() {
    	
    }

	public Long getCostHeadId() {
		return costHeadId;
	}

	public String getHeadName() {
		return headName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public int getSlNo() {
		return slNo;
	}

	public int getDefaultFlag() {
		return defaultFlag;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public ModelUOM getModelUOM() {
		return modelUOM;
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

	public void setCostHeadId(Long costHeadId) {
		this.costHeadId = costHeadId;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public void setDefaultFlag(int defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
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

	public List<ModelCurrency> getModelCurrencyList() {
		return modelCurrencyList;
	}

	public void setModelCurrencyList(List<ModelCurrency> modelCurrencyList) {
		this.modelCurrencyList = modelCurrencyList;
	}

	@Override
	public String toString() {
		return "ModelCostHead [costHeadId=" + costHeadId + ", headName=" + headName + ", shortCode=" + shortCode
				+ ", slNo=" + slNo + ", defaultFlag=" + defaultFlag + ", unitPrice=" + unitPrice + ", modelUOM="
				+ modelUOM + ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy
				+ ", entryTimestamp=" + entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestap="
				+ updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	
	
}
