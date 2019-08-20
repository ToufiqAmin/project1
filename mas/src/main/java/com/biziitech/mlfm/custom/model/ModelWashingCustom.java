package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelWashingCustom {
	
	private Long inquiryId;
	private Date inquiryDate;
	private Long buyerId;
	private String buyerName;
	private Long itemId;
	private String itemName;
	private Long uomId;
	private String uomName;
	private String washingStatus;
	private Long userId;
	private String userName;
	
	private Long washingPlanId;
	private Date washingPlanDate;
	private Double washingPlanQty;
	private String washingPlanRemarks;
	
	
	private Long washingId;
	private Date washingDate;
	private Double washingQty;
	private Long washedBy;
	private Double washingQtyUnDone;
	
	private boolean active;
	private String sActive;
	private int activeStatus;
	private String washingRemarks;
	
	private Long gatePassChdId;
	private Long pOId;
	private Double pOQty;
	private Long pOMstId;
	private Date pOMstDate;
	
	private Long wOChdId;
	private Long wOMstId;
	private Long wOId;
	private Date wODate;

	private Double qCQty;
	private Double mendedQty;
	private Double finishedQty;
	private Double washedQty;
	
	private Long productionId;
	private Date productionDate;
	private Double productionQty;
	
	private Long inquiryItemQtyId;
	private Double inquiryItemQty;
	
	private Long gatePassId;
	private int gatePassType;

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

	public String getWashingStatus() {
		return washingStatus;
	}

	public void setWashingStatus(String washingStatus) {
		this.washingStatus = washingStatus;
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

	public Date getWashingDate() {
		return washingDate;
	}

	public void setWashingDate(Date washingDate) {
		this.washingDate = washingDate;
	}

	public Long getWashedBy() {
		return washedBy;
	}

	public void setWashedBy(Long washedBy) {
		this.washedBy = washedBy;
	}

	public Double getWashingQty() {
		return washingQty;
	}

	public void setWashingQty(Double washingQty) {
		this.washingQty = washingQty;
	}

	public Long getWashingId() {
		return washingId;
	}

	public void setWashingId(Long washingId) {
		this.washingId = washingId;
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

	public Long getwOId() {
		return wOId;
	}

	public void setwOId(Long wOId) {
		this.wOId = wOId;
	}

	public Date getwODate() {
		return wODate;
	}

	public void setwODate(Date wODate) {
		this.wODate = wODate;
	}

	public Long getGatePassChdId() {
		return gatePassChdId;
	}

	public void setGatePassChdId(Long gatePassChdId) {
		this.gatePassChdId = gatePassChdId;
	}

	public Long getpOId() {
		return pOId;
	}

	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}

	public Double getpOQty() {
		return pOQty;
	}

	public void setpOQty(Double pOQty) {
		this.pOQty = pOQty;
	}

	public Long getpOMstId() {
		return pOMstId;
	}

	public void setpOMstId(Long pOMstId) {
		this.pOMstId = pOMstId;
	}

	public Long getWashingPlanId() {
		return washingPlanId;
	}

	public void setWashingPlanId(Long washingPlanId) {
		this.washingPlanId = washingPlanId;
	}

	public Date getWashingPlanDate() {
		return washingPlanDate;
	}

	public void setWashingPlanDate(Date washingPlanDate) {
		this.washingPlanDate = washingPlanDate;
	}

	public Double getWashingPlanQty() {
		return washingPlanQty;
	}

	public void setWashingPlanQty(Double washingPlanQty) {
		this.washingPlanQty = washingPlanQty;
	}

	public String getWashingPlanRemarks() {
		return washingPlanRemarks;
	}

	public void setWashingPlanRemarks(String washingPlanRemarks) {
		this.washingPlanRemarks = washingPlanRemarks;
	}

	public Double getWashingQtyUnDone() {
		return washingQtyUnDone;
	}

	public void setWashingQtyUnDone(Double washingQtyUnDone) {
		this.washingQtyUnDone = washingQtyUnDone;
	}

	public String getWashingRemarks() {
		return washingRemarks;
	}

	public void setWashingRemarks(String washingRemarks) {
		this.washingRemarks = washingRemarks;
	}

	public Long getwOChdId() {
		return wOChdId;
	}

	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}

	public Long getwOMstId() {
		return wOMstId;
	}

	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}

	public Double getqCQty() {
		return qCQty;
	}

	public void setqCQty(Double qCQty) {
		this.qCQty = qCQty;
	}

	public Double getMendedQty() {
		return mendedQty;
	}

	public void setMendedQty(Double mendedQty) {
		this.mendedQty = mendedQty;
	}

	public Double getFinishedQty() {
		return finishedQty;
	}

	public void setFinishedQty(Double finishedQty) {
		this.finishedQty = finishedQty;
	}

	public Double getWashedQty() {
		return washedQty;
	}

	public void setWashedQty(Double washedQty) {
		this.washedQty = washedQty;
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

	public Date getpOMstDate() {
		return pOMstDate;
	}

	public void setpOMstDate(Date pOMstDate) {
		this.pOMstDate = pOMstDate;
	}

	public Long getGatePassId() {
		return gatePassId;
	}

	public int getGatePassType() {
		return gatePassType;
	}

	public void setGatePassId(Long gatePassId) {
		this.gatePassId = gatePassId;
	}

	public void setGatePassType(int gatePassType) {
		this.gatePassType = gatePassType;
	}
	
	
	

}
