package com.biziitech.mlfm.custom.model;

import java.util.Date;



public class ModelWashingPlanCustom {
	
	
	private Long wOChdId;
	private Long wOMstId;
	
	private Date wODate;
	
	private Long pOId;
	private Date pODate;
	private Double pOQty;
	private String buyerName;
	private Long buyerId;
	private Long itemId;
	private String itemName;
	
	private Date qCDate;
	private Double qCQty;
	private Long qCId;
	private String qCRemarks;
	
	private boolean active;
	private String sActive;
	private int activeStatus;
	
	private Long uomId;
	private String uomName;
	
	private Long mendingId;
	private Date mendingDate;
	private Double mendingQty;
	private String mendingRemarks;
	
	
	private Long userId;
	private String userName;
	
	private Long washingPlanId;
	private Date washingPlanDate;
	private Double washingPlanQty;
	private String washingPlanRemarks;
	private Double washingPlanQtyUnDone;
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
	public Date getwODate() {
		return wODate;
	}
	public void setwODate(Date wODate) {
		this.wODate = wODate;
	}
	public Long getpOId() {
		return pOId;
	}
	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}
	public Date getpODate() {
		return pODate;
	}
	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}
	public Double getpOQty() {
		return pOQty;
	}
	public void setpOQty(Double pOQty) {
		this.pOQty = pOQty;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
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
	public Double getWashingPlanQtyUnDone() {
		return washingPlanQtyUnDone;
	}
	public void setWashingPlanQtyUnDone(Double washingPlanQtyUnDone) {
		this.washingPlanQtyUnDone = washingPlanQtyUnDone;
	}
	public Date getqCDate() {
		return qCDate;
	}
	public void setqCDate(Date qCDate) {
		this.qCDate = qCDate;
	}
	public Double getqCQty() {
		return qCQty;
	}
	public void setqCQty(Double qCQty) {
		this.qCQty = qCQty;
	}
	public Long getqCId() {
		return qCId;
	}
	public void setqCId(Long qCId) {
		this.qCId = qCId;
	}
	public String getqCRemarks() {
		return qCRemarks;
	}
	public void setqCRemarks(String qCRemarks) {
		this.qCRemarks = qCRemarks;
	}
	public Long getMendingId() {
		return mendingId;
	}
	public void setMendingId(Long mendingId) {
		this.mendingId = mendingId;
	}
	public Date getMendingDate() {
		return mendingDate;
	}
	public void setMendingDate(Date mendingDate) {
		this.mendingDate = mendingDate;
	}
	public Double getMendingQty() {
		return mendingQty;
	}
	public void setMendingQty(Double mendingQty) {
		this.mendingQty = mendingQty;
	}
	public String getMendingRemarks() {
		return mendingRemarks;
	}
	public void setMendingRemarks(String mendingRemarks) {
		this.mendingRemarks = mendingRemarks;
	}
	
	

}
