package com.biziitech.mlfm.custom.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelCurrency;

public class ModelWOInquiryData {
	
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
	
	
	private Long orderId;
	private String orderOwnerName;
	
	private Long ownerId;
	private Long orderOwnerTypeId;
	
	
	
	
	
	
	//this property create for Sohel
	private Long designId;
	private String designName;
	private Date designDate;
	private String designBy;
	private Long designerId;
	private List<ModelUOMCustom> modelUOMCustomList;
	private List<ModelCurrency> modelCurrencyList;
	
	private Double totalCost;
	private Double itemRate;
	private Long currencyId;
	//this property create for Sohel
	
	
	//this property create for WOCHD List Populated
	private Long wOChdId;
	private double wOChdItemQty;
	private double commissionRate;
	private double commissionTotal;
	private Long commissionPerUOM;
	private String commissionPerUOMName;
	private String currencyName;
	
	
	//this property create for WOCHD List Populated
	
	
	
	//this property create for WOMST List Populated
	private Long wOMstId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date wODate;
	private String userWONo;
	private String remarks;
	private int activeStatus;
	
	
	//getter and setter
	public Long getOrderItemQuantityId() {
		return orderItemQuantityId;
	}
	
	public void setOrderItemQuantityId(Long orderItemQuantityId) {
		this.orderItemQuantityId = orderItemQuantityId;
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
	public Date getDesignDate() {
		return designDate;
	}
	public void setDesignDate(Date designDate) {
		this.designDate = designDate;
	}
	public String getDesignBy() {
		return designBy;
	}
	public void setDesignBy(String designBy) {
		this.designBy = designBy;
	}
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
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
	public List<ModelUOMCustom> getModelUOMCustomList() {
		return modelUOMCustomList;
	}
	public void setModelUOMCustomList(List<ModelUOMCustom> modelUOMCustomList) {
		this.modelUOMCustomList = modelUOMCustomList;
	}
	
	public List<ModelCurrency> getModelCurrencyList() {
		return modelCurrencyList;
	}
	public void setModelCurrencyList(List<ModelCurrency> modelCurrencyList) {
		this.modelCurrencyList = modelCurrencyList;
	}
	
	@Override
	public String toString() {
		return "ModelWOInquiryData [orderItemQuantityId=" + orderItemQuantityId + ", orderDate=" + orderDate
				+ ", itemName=" + itemName + ", itemQty=" + itemQty + ", uomId=" + uomId + ", uomName=" + uomName
				+ ", itemOrderId=" + itemOrderId + ", itemId=" + itemId + ", enteredBy=" + enteredBy
				+ ", userClusterId=" + userClusterId + ", orderId=" + orderId + ", orderOwnerName=" + orderOwnerName
				+ ", ownerId=" + ownerId + ", orderOwnerTypeId=" + orderOwnerTypeId + ", designId=" + designId
				+ ", designName=" + designName + ", designDate=" + designDate + ", designBy=" + designBy
				+ ", designerId=" + designerId + ", modelUOMCustomList=" + modelUOMCustomList + ", modelCurrencyList="
				+ modelCurrencyList + "]";
	}

	public Long getwOMstId() {
		return wOMstId;
	}

	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}

	public Date getwODate() {
		return wODate;
	}

	public void setwODate(Date wODate) {
		this.wODate = wODate;
	}

	public String getUserWONo() {
		return userWONo;
	}

	public void setUserWONo(String userWONo) {
		this.userWONo = userWONo;
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

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getItemRate() {
		return itemRate;
	}

	public void setItemRate(Double itemRate) {
		this.itemRate = itemRate;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public Long getwOChdId() {
		return wOChdId;
	}

	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}

	public double getwOChdItemQty() {
		return wOChdItemQty;
	}

	public void setwOChdItemQty(double wOChdItemQty) {
		this.wOChdItemQty = wOChdItemQty;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public double getCommissionTotal() {
		return commissionTotal;
	}

	public void setCommissionTotal(double commissionTotal) {
		this.commissionTotal = commissionTotal;
	}

	public Long getCommissionPerUOM() {
		return commissionPerUOM;
	}

	public void setCommissionPerUOM(Long commissionPerUOM) {
		this.commissionPerUOM = commissionPerUOM;
	}

	public String getCommissionPerUOMName() {
		return commissionPerUOMName;
	}

	public void setCommissionPerUOMName(String commissionPerUOMName) {
		this.commissionPerUOMName = commissionPerUOMName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	
	

}
