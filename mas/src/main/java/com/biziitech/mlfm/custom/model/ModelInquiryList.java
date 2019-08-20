package com.biziitech.mlfm.custom.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelInquiryList {
	
	private Long orderId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date orderDate;
	private Long itemOrderId;
	private String itemName;
	private Long orderItemQuantityId;
	private double itemQty;
	private Long uomId;
	private String uomName;
	private Long designId;
	private Long orderOwnerId;
	private String orderOwnerName;
	private Long productionPlanMstId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDate;
	
	private Long itemId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date designDate;
	
	private Long productionPlanChd;
	
	private Long wOMstId;
	private Long wOChdId;
	private double wOQty;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date wODate;
	
	private String remarks;
	private int activeStatus;
	
	//create by sohel on 04-03-2019 for inquiry
	
	private String buyerMail;
	private String initialBuyer;
	private String ultimateBuyer;
	private String userName;
	private Double quotedPrice;
	private Double expectedPrice;
	private Double fianlPrice;
	
	//created by sohel rana on 08/04/2019
	
	private String refNo;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date buyerExpectedDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date deliveryDate;
	
	private String cancelReason;
	private int orderStatus;
	
	private int verifyStatus;
	
	private String clusterName;
	
	private Long clusterId;
	
	private String itemTypeName;
	
	
	
	
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getClusterId() {
		return clusterId;
	}
	public void setClusterId(Long clusterId) {
		this.clusterId = clusterId;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public int getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getBuyerExpectedDate() {
		return buyerExpectedDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setBuyerExpectedDate(Date buyerExpectedDate) {
		this.buyerExpectedDate = buyerExpectedDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getBuyerMail() {
		return buyerMail;
	}
	public String getInitialBuyer() {
		return initialBuyer;
	}
	public String getUltimateBuyer() {
		return ultimateBuyer;
	}
	public String getUserName() {
		return userName;
	}
	public Double getQuotedPrice() {
		return quotedPrice;
	}
	public Double getExpectedPrice() {
		return expectedPrice;
	}
	public Double getFianlPrice() {
		return fianlPrice;
	}
	public void setBuyerMail(String buyerMail) {
		this.buyerMail = buyerMail;
	}
	public void setInitialBuyer(String initialBuyer) {
		this.initialBuyer = initialBuyer;
	}
	public void setUltimateBuyer(String ultimateBuyer) {
		this.ultimateBuyer = ultimateBuyer;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setQuotedPrice(Double quotedPrice) {
		this.quotedPrice = quotedPrice;
	}
	public void setExpectedPrice(Double expectedPrice) {
		this.expectedPrice = expectedPrice;
	}
	public void setFianlPrice(Double fianlPrice) {
		this.fianlPrice = fianlPrice;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Date getDesignDate() {
		return designDate;
	}
	public void setDesignDate(Date designDate) {
		this.designDate = designDate;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Long getItemOrderId() {
		return itemOrderId;
	}
	public void setItemOrderId(Long itemOrderId) {
		this.itemOrderId = itemOrderId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getOrderItemQuantityId() {
		return orderItemQuantityId;
	}
	public void setOrderItemQuantityId(Long orderItemQuantityId) {
		this.orderItemQuantityId = orderItemQuantityId;
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
	public Long getDesignId() {
		return designId;
	}
	public void setDesignId(Long designId) {
		this.designId = designId;
	}
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public String getOrderOwnerName() {
		return orderOwnerName;
	}
	public void setOrderOwnerName(String orderOwnerName) {
		this.orderOwnerName = orderOwnerName;
	}
	@Override
	public String toString() {
		return "ModelInquiryList [orderId=" + orderId + ", orderDate=" + orderDate + ", itemOrderId=" + itemOrderId
				+ ", itemName=" + itemName + ", orderItemQuantityId=" + orderItemQuantityId + ", itemQty=" + itemQty
				+ ", uomId=" + uomId + ", uomName=" + uomName + ", designId=" + designId + ", orderOwnerId="
				+ orderOwnerId + ", orderOwnerName=" + orderOwnerName + "]";
	}
	public Long getProductionPlanChd() {
		return productionPlanChd;
	}
	public void setProductionPlanChd(Long productionPlanChd) {
		this.productionPlanChd = productionPlanChd;
	}
	public Date getwODate() {
		return wODate;
	}
	public void setwODate(Date wODate) {
		this.wODate = wODate;
	}

	
	public double getwOQty() {
		return wOQty;
	}
	public void setwOQty(double wOQty) {
		this.wOQty = wOQty;
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
	public Long getProductionPlanMstId() {
		return productionPlanMstId;
	}
	public void setProductionPlanMstId(Long productionPlanMstId) {
		this.productionPlanMstId = productionPlanMstId;
	}
	
	
	
//	select a.order_id, a.order_date,b.item_order_id,b.item_id,d.item_name,c.order_item_qty_id,c.item_qty,c.uom_id,e.uom_name,c.design_id,a.order_owner_id,f.owner_name 
	
	
	
	
	

	
	
	
	

}
