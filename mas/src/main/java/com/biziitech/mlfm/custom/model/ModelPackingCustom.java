package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelPackingCustom {
	
	private Long inquiryId;
	private Date inquiryDate;
	private Long buyerId;
	private String buyerName;
	private Long itemId;
	private String itemName;
	private Long inquiryItemQtyId;
	private Double inquiryItemQty;
	private Long productionId;
	private Date productionDate;
	private Double productionQty;
	private Long uomId;
	private String uomName;
	private String packingStatus;
	private Long userId;
	private String userName;
	private Double qCQtySum;
	private Double mendingQtySum;
	
	private Double washingQtySum;
	
	private Double dyingQtySum;
	
	private Double finishedQtySum;
	
	private Double packedQtySum;
	private Double packedQty;
	private Date packingDate;
	private Long packedBy;
	
	private Double packingQty;
	private Long packingId;
	
	private boolean active;
	private String sActive;
	private int activeStatus;
	private String packedRemarks;

	public Long getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(Long inquiryId) {
		this.inquiryId = inquiryId;
	}

	public Date getInquiryDate() {
		return inquiryDate;
	}

	public void setInquiryDate(Date inquiryDate) {
		this.inquiryDate = inquiryDate;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getInquiryItemQtyId() {
		return inquiryItemQtyId;
	}

	public void setInquiryItemQtyId(Long inquiryItemQtyId) {
		this.inquiryItemQtyId = inquiryItemQtyId;
	}

	public Double getInquiryItemQty() {
		return inquiryItemQty;
	}

	public void setInquiryItemQty(Double inquiryItemQty) {
		this.inquiryItemQty = inquiryItemQty;
	}

	public Long getProductionId() {
		return productionId;
	}

	public void setProductionId(Long productionId) {
		this.productionId = productionId;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Double getProductionQty() {
		return productionQty;
	}

	public void setProductionQty(Double productionQty) {
		this.productionQty = productionQty;
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

	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getqCQtySum() {
		return qCQtySum;
	}

	public void setqCQtySum(Double qCQtySum) {
		this.qCQtySum = qCQtySum;
	}

	public Double getMendingQtySum() {
		return mendingQtySum;
	}

	public void setMendingQtySum(Double mendingQtySum) {
		this.mendingQtySum = mendingQtySum;
	}

	public Double getWashingQtySum() {
		return washingQtySum;
	}

	public void setWashingQtySum(Double washingQtySum) {
		this.washingQtySum = washingQtySum;
	}

	public Double getDyingQtySum() {
		return dyingQtySum;
	}

	public void setDyingQtySum(Double dyingQtySum) {
		this.dyingQtySum = dyingQtySum;
	}

	public Double getFinishedQtySum() {
		return finishedQtySum;
	}

	public void setFinishedQtySum(Double finishedQtySum) {
		this.finishedQtySum = finishedQtySum;
	}

	public Double getPackedQtySum() {
		return packedQtySum;
	}

	public void setPackedQtySum(Double packedQtySum) {
		this.packedQtySum = packedQtySum;
	}

	public Double getPackedQty() {
		return packedQty;
	}

	public void setPackedQty(Double packedQty) {
		this.packedQty = packedQty;
	}

	public Date getPackingDate() {
		return packingDate;
	}

	public void setPackingDate(Date packingDate) {
		this.packingDate = packingDate;
	}

	public Long getPackedBy() {
		return packedBy;
	}

	public void setPackedBy(Long packedBy) {
		this.packedBy = packedBy;
	}

	public Double getPackingQty() {
		return packingQty;
	}

	public void setPackingQty(Double packingQty) {
		this.packingQty = packingQty;
	}

	public String getPackingStatus() {
		return packingStatus;
	}

	public void setPackingStatus(String packingStatus) {
		this.packingStatus = packingStatus;
	}

	public Long getPackingId() {
		return packingId;
	}

	public void setPackingId(Long packingId) {
		this.packingId = packingId;
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

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getPackedRemarks() {
		return packedRemarks;
	}

	public void setPackedRemarks(String packedRemarks) {
		this.packedRemarks = packedRemarks;
	}

}
