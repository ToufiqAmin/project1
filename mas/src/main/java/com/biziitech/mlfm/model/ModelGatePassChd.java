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

@Entity(name="MLFM_GATE_PASS_CHD")
public class ModelGatePassChd {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="GATE_PASS_CHD_ID")
	private Long gatePassChdId;
	
	@ManyToOne
	@JoinColumn(name="GATE_PASS_MST_ID")
	private ModelGatePassMst modelGatePassMst;
	
	@ManyToOne
	@JoinColumn(name="PRODUCTION_ID")
	private ModelProduction modelProduction;
	
	@Column(name="ITEM_QTY")
	private Double itemQty;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP")
	private Date updateTimestamp;
	
	@Column(name="FLEX_FIELD1")
	private String flexField1;
	
	@Column(name="FLEX_FIELD2")
	private String flexField2;
	
	@Column(name="FLEX_FIELD3")
	private String flexField3;
	
	@Column(name="FLEX_FIELD4")
	private String flexField4;
	
	@Column(name="FLEX_FIELD5")
	private String flexField5;	 
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
    
    public ModelGatePassChd() {
    	
    }

	public Long getGatePassChdId() {
		return gatePassChdId;
	}

	public ModelGatePassMst getModelGatePassMst() {
		return modelGatePassMst;
	}

	public ModelProduction getModelProduction() {
		return modelProduction;
	}

	public Double getItemQty() {
		return itemQty;
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

	public String getFlexField1() {
		return flexField1;
	}

	public String getFlexField2() {
		return flexField2;
	}

	public String getFlexField3() {
		return flexField3;
	}

	public String getFlexField4() {
		return flexField4;
	}

	public String getFlexField5() {
		return flexField5;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setGatePassChdId(Long gatePassChdId) {
		this.gatePassChdId = gatePassChdId;
	}

	public void setModelGatePassMst(ModelGatePassMst modelGatePassMst) {
		this.modelGatePassMst = modelGatePassMst;
	}

	public void setModelProduction(ModelProduction modelProduction) {
		this.modelProduction = modelProduction;
	}

	public void setItemQty(Double itemQty) {
		this.itemQty = itemQty;
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

	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}

	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}

	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}

	public void setFlexField4(String flexField4) {
		this.flexField4 = flexField4;
	}

	public void setFlexField5(String flexField5) {
		this.flexField5 = flexField5;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	@Override
	public String toString() {
		return "ModelGatePassChd [gatePassChdId=" + gatePassChdId + ", modelGatePassMst=" + modelGatePassMst
				+ ", modelProduction=" + modelProduction + ", itemQty=" + itemQty + ", remarks=" + remarks
				+ ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp=" + entryTimestamp
				+ ", updatedBy=" + updatedBy + ", updateTimestamp=" + updateTimestamp + ", flexField1=" + flexField1
				+ ", flexField2=" + flexField2 + ", flexField3=" + flexField3 + ", flexField4=" + flexField4
				+ ", flexField5=" + flexField5 + ", active=" + active + ", sActive=" + sActive + "]";
	}
    
    
	
	
}
