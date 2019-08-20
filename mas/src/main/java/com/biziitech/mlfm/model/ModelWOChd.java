package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_WO_CHD")
public class ModelWOChd {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="WO_CHD_ID")
	private Long woChdId;
	
	
	//@ManyToOne(cascade = {CascadeType.ALL})
	@ManyToOne
	@JoinColumn(name="WO_MST_ID")
    private ModelWOMst modelWOMst;
	
	//@ManyToOne(cascade = {CascadeType.ALL})
	@ManyToOne
	@JoinColumn(name="ORDER_ITEM_QTY_ID")
    private ModelOrderItemQty modelOrderItemQty;
	
	@ManyToOne
	@JoinColumn(name="CURRENCY_ID")
    private ModelCurrency modelCurrency;
	
	

	@Column(name="ITEM_QTY")
	private Double itemQty;
	
	@Column(name="ITEM_RATE")
	private Double itemRate;
	
	@Column(name="COMMISSION_RATE")
	private Double commissionRate;
	
	@Column(name="COMMISSION_PER_UOM")
	private Long commissionPerUOM;
	
	@Column(name="COMMISSION_TOTAL")
	private Double commissionTotal;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	//@Column(name="ENTERED_BY")
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	//@Column(name="UPDATED_BY")
	//@ColumnDefault("0")
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
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
	
	// getter and setter

	public Long getWoChdId() {
		return woChdId;
	}

	public void setWoChdId(Long woChdId) {
		this.woChdId = woChdId;
	}

	

	public ModelWOMst getModelWOMst() {
		return modelWOMst;
	}

	public void setModelWOMst(ModelWOMst modelWOMst) {
		this.modelWOMst = modelWOMst;
	}

	

	public ModelOrderItemQty getModelOrderItemQty() {
		return modelOrderItemQty;
	}

	public void setModelOrderItemQty(ModelOrderItemQty modelOrderItemQty) {
		this.modelOrderItemQty = modelOrderItemQty;
	}

	

	
	
	public ModelCurrency getModelCurrency() {
		return modelCurrency;
	}

	public void setModelCurrency(ModelCurrency modelCurrency) {
		this.modelCurrency = modelCurrency;
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

	
	public Long getCommissionPerUOM() {
		return commissionPerUOM;
	}

	public void setCommissionPerUOM(Long commissionPerUOM) {
		this.commissionPerUOM = commissionPerUOM;
	}



	
	

	public Double getItemQty() {
		return itemQty;
	}

	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public Double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public Double getCommissionTotal() {
		return commissionTotal;
	}

	public void setCommissionTotal(Double commissionTotal) {
		this.commissionTotal = commissionTotal;
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
		return "ModelWOChd [woChdId=" + woChdId + ", modelWOMst=" + modelWOMst + ", modelOrderItemQty="
				+ modelOrderItemQty + ", modelCurrency=" + modelCurrency + ", itemQty=" + itemQty + ", itemRate="
				+ itemRate + ", commissionRate=" + commissionRate + ", commissionPerUOM=" + commissionPerUOM
				+ ", commissionTotal=" + commissionTotal + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", entryTimestamp=" + entryTimestamp + ", updatedBy=" + updatedBy
				+ ", updateTimestap=" + updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3
				+ ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	
	
	

}
