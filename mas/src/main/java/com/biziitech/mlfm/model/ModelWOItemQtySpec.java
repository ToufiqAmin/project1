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

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_WO_ITEM_QTY_SPEC")
public class ModelWOItemQtySpec {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="WO_ITEM_QTY_SPEC_ID")
	private Long wOItemQtySpecId;
	
	@ManyToOne
	@JoinColumn(name="WO_CHD_ID")
	private ModelWOChd modelWOchd;
	
	@ManyToOne
	@JoinColumn(name="WO_SPEC_ID")
	private ModelSpec modelSpecWO;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ITEM_QTY_ID")
	private ModelOrderItemQty modelOrderItemQty;
	
	@Column(name="WO_SPEC_VALUE")
	private String wOSpecValue;
	
	@ManyToOne
	@JoinColumn(name="WO_SPEC_UOM_ID")
	private ModelUOM modelUOMWO;
	
	@ManyToOne
	@JoinColumn(name="ORDER_SPEC_ID")
	private ModelSpec modelSpecOrder;
	
	@Column(name="ORDER_SPEC_VALUE")
	private String orderSpecValue;
	
	@ManyToOne
	@JoinColumn(name="ORDER_SPEC_UOM_ID")
	private ModelUOM ModelUOMOrder;
	
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

	public Long getwOItemQtySpecId() {
		return wOItemQtySpecId;
	}

	public void setwOItemQtySpecId(Long wOItemQtySpecId) {
		this.wOItemQtySpecId = wOItemQtySpecId;
	}

	public ModelWOChd getModelWOchd() {
		return modelWOchd;
	}

	public void setModelWOchd(ModelWOChd modelWOchd) {
		this.modelWOchd = modelWOchd;
	}

	
	public ModelSpec getModelSpecWO() {
		return modelSpecWO;
	}

	

	public void setModelSpecWO(ModelSpec modelSpecWO) {
		this.modelSpecWO = modelSpecWO;
	}

	public ModelOrderItemQty getModelOrderItemQty() {
		return modelOrderItemQty;
	}

	public void setModelOrderItemQty(ModelOrderItemQty modelOrderItemQty) {
		this.modelOrderItemQty = modelOrderItemQty;
	}

	public String getwOSpecValue() {
		return wOSpecValue;
	}

	public void setwOSpecValue(String wOSpecValue) {
		this.wOSpecValue = wOSpecValue;
	}




	

	public String getOrderSpecValue() {
		return orderSpecValue;
	}

	public void setOrderSpecValue(String orderSpecValue) {
		this.orderSpecValue = orderSpecValue;
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

	public ModelUOM getModelUOMWO() {
		return modelUOMWO;
	}

	public void setModelUOMWO(ModelUOM modelUOMWO) {
		this.modelUOMWO = modelUOMWO;
	}

	public ModelSpec getModelSpecOrder() {
		return modelSpecOrder;
	}

	public void setModelSpecOrder(ModelSpec modelSpecOrder) {
		this.modelSpecOrder = modelSpecOrder;
	}

	public ModelUOM getModelUOMOrder() {
		return ModelUOMOrder;
	}

	public void setModelUOMOrder(ModelUOM modelUOMOrder) {
		ModelUOMOrder = modelUOMOrder;
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
		return "ModelWOItemQtySpec [wOItemQtySpecId=" + wOItemQtySpecId + ", modelWOchd=" + modelWOchd
				+ ", modelSpecWO=" + modelSpecWO + ", modelOrderItemQty=" + modelOrderItemQty + ", wOSpecValue="
				+ wOSpecValue + ", modelUOMWO=" + modelUOMWO + ", modelSpecOrder=" + modelSpecOrder
				+ ", orderSpecValue=" + orderSpecValue + ", ModelUOMOrder=" + ModelUOMOrder + ", remarks=" + remarks
				+ ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy
				+ ", entryTimestamp=" + entryTimestamp + ", updateTimestap=" + updateTimestap + ", flex1=" + flex1
				+ ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active="
				+ active + ", sActive=" + sActive + "]";
	}

	
	
	
	
	
}
