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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_ORDER_ITEM")
public class ModelOrderItem {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="ITEM_ORDER_ID")
	private Long itemOrderId;
	
	@ManyToOne
	@JoinColumn(name="ORDER_ID")
	//private ModelOrder orderId;
	private ModelOrder modelOrder;
	
	@ManyToOne
	@JoinColumn(name="ITEM_ID")
	//private List<ModelItem> itemId;
	private ModelItem listModelItem;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="ORDER_DATE")
	private Date orderDate;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser modelEnteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser modelUpdatedBy;
	
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
	private String orderDateEdit;
	
   

	public ModelOrderItem() {
    	
    }



	public Long getItemOrderId() {
		return itemOrderId;
	}



	public ModelOrder getModelOrder() {
		return modelOrder;
	}



	public ModelItem getListModelItem() {
		return listModelItem;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public String getRemarks() {
		return remarks;
	}



	public int getActiveStatus() {
		return activeStatus;
	}



	public ModelUser getModelEnteredBy() {
		return modelEnteredBy;
	}



	public Date getEntryTimestamp() {
		return entryTimestamp;
	}



	public ModelUser getModelUpdatedBy() {
		return modelUpdatedBy;
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



	public String getOrderDateEdit() {
		return orderDateEdit;
	}



	public void setItemOrderId(Long itemOrderId) {
		this.itemOrderId = itemOrderId;
	}



	public void setModelOrder(ModelOrder modelOrder) {
		this.modelOrder = modelOrder;
	}



	public void setListModelItem(ModelItem listModelItem) {
		this.listModelItem = listModelItem;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}



	public void setModelEnteredBy(ModelUser modelEnteredBy) {
		this.modelEnteredBy = modelEnteredBy;
	}



	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}



	public void setModelUpdatedBy(ModelUser modelUpdatedBy) {
		this.modelUpdatedBy = modelUpdatedBy;
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



	public void setOrderDateEdit(String orderDateEdit) {
		this.orderDateEdit = orderDateEdit;
	}



	@Override
	public String toString() {
		return "ModelOrderItem [itemOrderId=" + itemOrderId + ", modelOrder=" + modelOrder + ", listModelItem="
				+ listModelItem + ", orderDate=" + orderDate + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", modelEnteredBy=" + modelEnteredBy + ", entryTimestamp=" + entryTimestamp + ", modelUpdatedBy="
				+ modelUpdatedBy + ", updateTimestap=" + updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2
				+ ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive="
				+ sActive + ", orderDateEdit=" + orderDateEdit + "]";
	}

    

}