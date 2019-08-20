package com.biziitech.mlfm.custom.model;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;


public class ModelWOChdListCustom {
	
	private Long orderItemQuantityId;
	private Date orderDate;
	private String itemName;
	private double itemQty;
	private Long uomId;
	private String uomName;
	private Long itemOrderId;
	private Long itemId;
	private Long enteredBy;
	private Long userClusterId;
	private String remarks;
	private Long orderId;
	private String orderOwnerName;
	private Long ownerId;
	private Long orderOwnerTypeId;
	
	private double itemRate;
	private Long wOMstId;
	
	private List<ModelUOMCustom> modelUOMCustomList;
	
	private List<ModelCurrency> modelCurrencyList;
	
	
	
	//for Wo Spec table
	private Long wOItemQtySpecId;
	
	
	private Long orderSpecId;
	private String orderSpecName;
	
	private String orderSpecValue;
	
	private Long orderUomId;
	private String orderUomName;
	
	private Long wOSpecId;
	private String wOSpecName;
	
	private String wOSpecValue;
	
	private Long wOUomId;
	private String wOUomName;
	
	//created by sohel rana on 23/04/2019
	
	private int activeStatus;
	
	private List<ModelSpec> modelSpecList;
	
	//for Wo Spec table
	
	
	
	
	
	public List<ModelCurrency> getModelCurrencyList() {
		return modelCurrencyList;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public void setModelCurrencyList(List<ModelCurrency> modelCurrencyList) {
		this.modelCurrencyList = modelCurrencyList;
	}
	//getter and setter
	public Long getOrderItemQuantityId() {
		return orderItemQuantityId;
	}
	public void setOrderItemQuantityId(Long orderItemQuantityId) {
		this.orderItemQuantityId = orderItemQuantityId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getItemQty() {
		return itemQty;
	}
	public void setItemQty(double itemQty) {
		this.itemQty = itemQty;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public Long getItemOrderId() {
		return itemOrderId;
	}
	public void setItemOrderId(Long itemOrderId) {
		this.itemOrderId = itemOrderId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getUserClusterId() {
		return userClusterId;
	}
	public void setUserClusterId(Long userClusterId) {
		this.userClusterId = userClusterId;
	}
	public Long getEnteredBy() {
		return enteredBy;
	}
	public void setEnteredBy(Long enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderOwnerName() {
		return orderOwnerName;
	}
	public void setOrderOwnerName(String orderOwnerName) {
		this.orderOwnerName = orderOwnerName;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getOrderOwnerTypeId() {
		return orderOwnerTypeId;
	}
	public void setOrderOwnerTypeId(Long orderOwnerTypeId) {
		this.orderOwnerTypeId = orderOwnerTypeId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public double getItemRate() {
		return itemRate;
	}
	public void setItemRate(double itemRate) {
		this.itemRate = itemRate;
	}
	
	public Long getwOMstId() {
		return wOMstId;
	}
	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}
	
	
	
	public List<ModelUOMCustom> getModelUOMCustomList() {
		return modelUOMCustomList;
	}
	public void setModelUOMCustomList(List<ModelUOMCustom> modelUOMCustomList) {
		this.modelUOMCustomList = modelUOMCustomList;
	}
	@Override
	public String toString() {
		return "ModelWOChdListCustom [orderItemQuantityId=" + orderItemQuantityId + ", orderDate=" + orderDate
				+ ", itemName=" + itemName + ", itemQty=" + itemQty + ", uomId=" + uomId + ", uomName=" + uomName
				+ ", itemOrderId=" + itemOrderId + ", itemId=" + itemId + ", enteredBy=" + enteredBy
				+ ", userClusterId=" + userClusterId + ", remarks=" + remarks + ", orderId=" + orderId
				+ ", orderOwnerName=" + orderOwnerName + ", ownerId=" + ownerId + ", orderOwnerTypeId="
				+ orderOwnerTypeId + "]";
	}
	public Long getwOItemQtySpecId() {
		return wOItemQtySpecId;
	}
	public void setwOItemQtySpecId(Long wOItemQtySpecId) {
		this.wOItemQtySpecId = wOItemQtySpecId;
	}
	public Long getOrderSpecId() {
		return orderSpecId;
	}
	public void setOrderSpecId(Long orderSpecId) {
		this.orderSpecId = orderSpecId;
	}
	public String getOrderSpecName() {
		return orderSpecName;
	}
	public void setOrderSpecName(String orderSpecName) {
		this.orderSpecName = orderSpecName;
	}
	public String getOrderSpecValue() {
		return orderSpecValue;
	}
	public void setOrderSpecValue(String orderSpecValue) {
		this.orderSpecValue = orderSpecValue;
	}
	public Long getOrderUomId() {
		return orderUomId;
	}
	public void setOrderUomId(Long orderUomId) {
		this.orderUomId = orderUomId;
	}
	public String getOrderUomName() {
		return orderUomName;
	}
	public void setOrderUomName(String orderUomName) {
		this.orderUomName = orderUomName;
	}
	public Long getwOSpecId() {
		return wOSpecId;
	}
	public void setwOSpecId(Long wOSpecId) {
		this.wOSpecId = wOSpecId;
	}
	public String getwOSpecName() {
		return wOSpecName;
	}
	public void setwOSpecName(String wOSpecName) {
		this.wOSpecName = wOSpecName;
	}
	public String getwOSpecValue() {
		return wOSpecValue;
	}
	public void setwOSpecValue(String wOSpecValue) {
		this.wOSpecValue = wOSpecValue;
	}
	public Long getwOUomId() {
		return wOUomId;
	}
	public void setwOUomId(Long wOUomId) {
		this.wOUomId = wOUomId;
	}
	public String getwOUomName() {
		return wOUomName;
	}
	public void setwOUomName(String wOUomName) {
		this.wOUomName = wOUomName;
	}
	public List<ModelSpec> getModelSpecList() {
		return modelSpecList;
	}
	public void setModelSpecList(List<ModelSpec> modelSpecList) {
		this.modelSpecList = modelSpecList;
	}
	
	

}
