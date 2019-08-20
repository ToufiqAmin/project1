package com.biziitech.mlfm.custom.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelDesignCustom {
	
	
	//For designSearch List -start
	private Long orderId;
	private String userRefNo;
	private String orderOwnerName;
	private String ultimateOwnerName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	private String refMailId;
	private String remarks;
	private Long userId;
	private String userName;
	private int activeStatus;
	
	private Long designId;
	private Long itemId;
	private String itemName;
	private Long orderItemQtyId;
	
	//For designSearch List -End
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date designDate;
	private Long designerId;
	private String designName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date factPropDeliveryDate;
	private Long machineTypeId;
	private String userDesignNo;
	
	private String designerName;
	
	private Long fabricTypeId;
	private String dTM;
	private int laceTypeId;
	private int noOfStitches;
	
	
	
	//for specification Table
	private Long designSpecId;
	private String specValue;
	private String specName;
	private Long specId;
	//for specification Table
	
	
	private String uomName;
	private Long uomId;
	
	
	
	//for Design Consume Table
		private Long designConsumItemId;
		private Long itemQty;
		
		//for  Design Consume Table
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getUserRefNo() {
		return userRefNo;
	}
	public void setUserRefNo(String userRefNo) {
		this.userRefNo = userRefNo;
	}
	public String getOrderOwnerName() {
		return orderOwnerName;
	}
	public void setOrderOwnerName(String orderOwnerName) {
		this.orderOwnerName = orderOwnerName;
	}
	public String getUltimateOwnerName() {
		return ultimateOwnerName;
	}
	public void setUltimateOwnerName(String ultimateOwnerName) {
		this.ultimateOwnerName = ultimateOwnerName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getRefMailId() {
		return refMailId;
	}
	public void setRefMailId(String refMailId) {
		this.refMailId = refMailId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Long getDesignId() {
		return designId;
	}
	public void setDesignId(Long designId) {
		this.designId = designId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getOrderItemQtyId() {
		return orderItemQtyId;
	}
	public void setOrderItemQtyId(Long orderItemQtyId) {
		this.orderItemQtyId = orderItemQtyId;
	}
	public Date getDesignDate() {
		return designDate;
	}
	public void setDesignDate(Date designDate) {
		this.designDate = designDate;
	}
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}
	public String getDesignName() {
		return designName;
	}
	public void setDesignName(String designName) {
		this.designName = designName;
	}
	public Date getFactPropDeliveryDate() {
		return factPropDeliveryDate;
	}
	public void setFactPropDeliveryDate(Date factPropDeliveryDate) {
		this.factPropDeliveryDate = factPropDeliveryDate;
	}
	
	public String getUserDesignNo() {
		return userDesignNo;
	}
	public void setUserDesignNo(String userDesignNo) {
		this.userDesignNo = userDesignNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	public Long getDesignSpecId() {
		return designSpecId;
	}
	public void setDesignSpecId(Long designSpecId) {
		this.designSpecId = designSpecId;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public Long getDesignConsumItemId() {
		return designConsumItemId;
	}
	public void setDesignConsumItemId(Long designConsumItemId) {
		this.designConsumItemId = designConsumItemId;
	}
	public Long getItemQty() {
		return itemQty;
	}
	public void setItemQty(Long itemQty) {
		this.itemQty = itemQty;
	}
	public Long getSpecId() {
		return specId;
	}
	public void setSpecId(Long specId) {
		this.specId = specId;
	}
	public Long getMachineTypeId() {
		return machineTypeId;
	}
	public void setMachineTypeId(Long machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
	public Long getFabricTypeId() {
		return fabricTypeId;
	}
	public void setFabricTypeId(Long fabricTypeId) {
		this.fabricTypeId = fabricTypeId;
	}
	public String getdTM() {
		return dTM;
	}
	public void setdTM(String dTM) {
		this.dTM = dTM;
	}
	public int getLaceTypeId() {
		return laceTypeId;
	}
	public void setLaceTypeId(int laceTypeId) {
		this.laceTypeId = laceTypeId;
	}
	public int getNoOfStitches() {
		return noOfStitches;
	}
	public void setNoOfStitches(int noOfStitches) {
		this.noOfStitches = noOfStitches;
	}
	public String getDesignerName() {
		return designerName;
	}
	public void setDesignerName(String designerName) {
		this.designerName = designerName;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	

}
