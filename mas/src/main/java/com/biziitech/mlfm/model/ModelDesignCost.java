package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import com.biziitech.mlfm.bg.model.ModelCurrency;

@Entity(name="MLFM_DESIGN_COST")
public class ModelDesignCost {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="DESIGN_COST_ID")
	private Long designCostId;
	
	@ManyToOne
	@JoinColumn(name="DESIGN_ID")
	private ModelDesign modelDesign;
	
	@ManyToOne
	@JoinColumn(name="COST_HEAD_ID")
	private ModelCostHead costHeadId;
	
	@Column(name="UNIT_PRICE")
	private Double unitPrice;
	
	@Column(name="QTY")
	private Double qty;
	
	@ManyToOne
	@JoinColumn(name="UOM_ID")
	private ModelUOM modelUOM;
	
	
	@Column(name="TOTAL_COST")
	private Double totalCost;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	//@ColumnDefault("0")
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
		
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	private ModelItem modelItem;
	
	@ManyToOne
	@JoinColumn(name="CURRENCY_ID")
	private ModelCurrency modelCurrency;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
    public ModelDesignCost() {
    	
    }

	public Long getDesignCostId() {
		return designCostId;
	}

	public ModelDesign getModelDesign() {
		return modelDesign;
	}

	public ModelCostHead getCostHeadId() {
		return costHeadId;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public Double getQty() {
		return qty;
	}

	public ModelUOM getModelUOM() {
		return modelUOM;
	}

	public Double getTotalCost() {
		return totalCost;
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

	public ModelItem getModelItem() {
		return modelItem;
	}

	public ModelCurrency getModelCurrency() {
		return modelCurrency;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setDesignCostId(Long designCostId) {
		this.designCostId = designCostId;
	}

	public void setModelDesign(ModelDesign modelDesign) {
		this.modelDesign = modelDesign;
	}

	public void setCostHeadId(ModelCostHead costHeadId) {
		this.costHeadId = costHeadId;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public void setModelUOM(ModelUOM modelUOM) {
		this.modelUOM = modelUOM;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
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

	public void setModelItem(ModelItem modelItem) {
		this.modelItem = modelItem;
	}

	public void setModelCurrency(ModelCurrency modelCurrency) {
		this.modelCurrency = modelCurrency;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	@Override
	public String toString() {
		return "ModelDesignCost [designCostId=" + designCostId + ", modelDesign=" + modelDesign + ", costHeadId="
				+ costHeadId + ", unitPrice=" + unitPrice + ", qty=" + qty + ", modelUOM=" + modelUOM + ", totalCost="
				+ totalCost + ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy
				+ ", entryTimestamp=" + entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestap="
				+ updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4
				+ ", flex5=" + flex5 + ", modelItem=" + modelItem + ", modelCurrency=" + modelCurrency + ", active="
				+ active + ", sActive=" + sActive + "]";
	}

	
}
