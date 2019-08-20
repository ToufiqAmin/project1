package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelPIMstCustom {

	
	private Long pIMstId;
	private String userPINo;
	private Date pIDate;
	private String lcNo;
	private String remarks;
	private int pIType;
	private String pITypeName;
	private String termsconDesc;
	private Long orderOwnerId;
	private String ownerName;
	private String itemName;
	private String uOMName;
	private Double itemRate;
	private Double pIQty;
	private Double comissionPerUOM;
	private Double comissionTotal;
	private Long wOMstId;
	private Long wOChdId;
	private Long pIChdId;
	private Long currencyId;
	private String currencyName;
	
	
	
	
	public String getUserPINo() {
		return userPINo;
	}
	public void setUserPINo(String userPINo) {
		this.userPINo = userPINo;
	}
	
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTermsconDesc() {
		return termsconDesc;
	}
	public void setTermsconDesc(String termsconDesc) {
		this.termsconDesc = termsconDesc;
	}
	
	public Long getpIMstId() {
		return pIMstId;
	}
	public void setpIMstId(Long pIMstId) {
		this.pIMstId = pIMstId;
	}
	public int getpIType() {
		return pIType;
	}
	public void setpIType(int pIType) {
		this.pIType = pIType;
	}
	public String getpITypeName() {
		return pITypeName;
	}
	public void setpITypeName(String pITypeName) {
		this.pITypeName = pITypeName;
	}
	public Date getpIDate() {
		return pIDate;
	}
	public void setpIDate(Date pIDate) {
		this.pIDate = pIDate;
	}
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getItemName() {
		return itemName;
	}
	public String getuOMName() {
		return uOMName;
	}
	public Double getItemRate() {
		return itemRate;
	}
	public Double getpIQty() {
		return pIQty;
	}
	public Double getComissionPerUOM() {
		return comissionPerUOM;
	}
	public Double getComissionTotal() {
		return comissionTotal;
	}
	public Long getwOMstId() {
		return wOMstId;
	}
	public Long getwOChdId() {
		return wOChdId;
	}
	public Long getpIChdId() {
		return pIChdId;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}
	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}
	public void setpIQty(Double pIQty) {
		this.pIQty = pIQty;
	}
	public void setComissionPerUOM(Double comissionPerUOM) {
		this.comissionPerUOM = comissionPerUOM;
	}
	public void setComissionTotal(Double comissionTotal) {
		this.comissionTotal = comissionTotal;
	}
	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}
	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}
	public void setpIChdId(Long pIChdId) {
		this.pIChdId = pIChdId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	@Override
	public String toString() {
		return "ModelPIMstCustom [pIMstId=" + pIMstId + ", userPINo=" + userPINo + ", pIDate=" + pIDate + ", lcNo="
				+ lcNo + ", remarks=" + remarks + ", pIType=" + pIType + ", pITypeName=" + pITypeName
				+ ", termsconDesc=" + termsconDesc + ", orderOwnerId=" + orderOwnerId + ", ownerName=" + ownerName
				+ ", itemName=" + itemName + ", uOMName=" + uOMName + ", itemRate=" + itemRate + ", pIQty=" + pIQty
				+ ", comissionPerUOM=" + comissionPerUOM + ", comissionTotal=" + comissionTotal + ", wOMstId=" + wOMstId
				+ ", wOChdId=" + wOChdId + ", pIChdId=" + pIChdId + ", currencyId=" + currencyId + ", currencyName="
				+ currencyName + "]";
	}
	
	
	
	
	
	
}
