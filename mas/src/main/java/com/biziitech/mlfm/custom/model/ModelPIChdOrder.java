package com.biziitech.mlfm.custom.model;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelCurrency;

public class ModelPIChdOrder {
	
	private Long pIChdId;
	private Long pIMstId;
	private Long wOMstId;
	private Long wOChdId;
	private Long orderItemQtyId;	
	private Long orderId;
	private String ownerName;
	private String itemName;
	private double itemQty;
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	//edited by KTA
	private Long currencyId;
	// end edited by KTA 
	private String currencyName;
	private Long uOMId;
	private String uOMName;
	private double itemRate;
	private Double pIQtySaved;
	private double commissionRate;
	private double commissionPerUOM;
	private double commissionTotal;
	
	private List<ModelCurrency> modelCurrencyList;
	
	
	
	
	public Long getpIChdId() {
		return pIChdId;
	}
	public void setpIChdId(Long pIChdId) {
		this.pIChdId = pIChdId;
	}
	public Long getpIMstId() {
		return pIMstId;
	}
	public void setpIMstId(Long pIMstId) {
		this.pIMstId = pIMstId;
	}
	public Long getwOMstId() {
		return wOMstId;
	}
	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}
	public Long getwOChdId() {
		return wOChdId;
	}
	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}
	public Long getOrderItemQtyId() {
		return orderItemQtyId;
	}
	public void setOrderItemQtyId(Long orderItemQtyId) {
		this.orderItemQtyId = orderItemQtyId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
		
	}
	
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	public Long getuOMId() {
		return uOMId;
	}
	public void setuOMId(Long uOMId) {
		this.uOMId = uOMId;
	}
	public String getuOMName() {
		return uOMName;
	}
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}
	public double getItemRate() {
		return itemRate;
	}
	public void setItemRate(double itemRate) {
		this.itemRate = itemRate;
	}
	public Double getpIQtySaved() {
		return pIQtySaved;
	}
	public void setpIQtySaved(Double pIQtySaved) {
		this.pIQtySaved = pIQtySaved;
	}
	public double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public double getCommissionPerUOM() {
		return commissionPerUOM;
	}
	public void setCommissionPerUOM(double commissionPerUOM) {
		this.commissionPerUOM = commissionPerUOM;
	}
	public double getCommissionTotal() {
		return commissionTotal;
	}
	public void setCommissionTotal(double commissionTotal) {
		this.commissionTotal = commissionTotal;
	}
	public List<ModelCurrency> getModelCurrencyList() {
		return modelCurrencyList;
	}
	public void setModelCurrencyList(List<ModelCurrency> modelCurrencyList) {
		this.modelCurrencyList = modelCurrencyList;
	}
	@Override
	public String toString() {
		return "ModelPIChdOrder [pIChdId=" + pIChdId + ", pIMstId=" + pIMstId + ", wOMstId=" + wOMstId + ", wOChdId="
				+ wOChdId + ", orderItemQtyId=" + orderItemQtyId + ", orderId=" + orderId + ", ownerName=" + ownerName
				+ ", itemName=" + itemName + ", itemQty=" + itemQty + ", currencyName=" + currencyName + ", uOMId="
				+ uOMId + ", uOMName=" + uOMName + ", itemRate=" + itemRate + ", pIQtySaved=" + pIQtySaved
				+ ", commissionRate=" + commissionRate + ", commissionPerUOM=" + commissionPerUOM + ", commissionTotal="
				+ commissionTotal + ", modelCurrencyList=" + modelCurrencyList + "]";
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	
	
	
	

}
