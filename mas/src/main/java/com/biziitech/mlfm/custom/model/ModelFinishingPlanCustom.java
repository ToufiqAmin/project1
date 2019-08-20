package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelFinishingPlanCustom {
	
	private Long pOId;
	private Date pODate;
	private Double pOQty;
	private String buyerName;
	private Long buyerId;
	private Long itemId;
	private String itemName;
	private Long uomId;
	private String uomName;
	
	private Date finishingPlanDate;
	private Double finishingPlanQty;
	private Long finishingPlanId;
	private String finishingPlanRemarks;
	private boolean active;
	private String sActive;
	private int activeStatus;
	
	private Double finishingPlanQtyUnDone;
	
	public Long getpOId() {
		return pOId;
	}
	public Date getpODate() {
		return pODate;
	}
	public Double getpOQty() {
		return pOQty;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public Long getItemId() {
		return itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public Long getUomId() {
		return uomId;
	}
	public String getUomName() {
		return uomName;
	}
	public Date getFinishingPlanDate() {
		return finishingPlanDate;
	}
	public Double getFinishingPlanQty() {
		return finishingPlanQty;
	}
	public Long getFinishingPlanId() {
		return finishingPlanId;
	}
	public String getFinishingPlanRemarks() {
		return finishingPlanRemarks;
	}
	public boolean isActive() {
		return active;
	}
	public String getsActive() {
		return sActive;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}
	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}
	public void setpOQty(Double pOQty) {
		this.pOQty = pOQty;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public void setFinishingPlanDate(Date finishingPlanDate) {
		this.finishingPlanDate = finishingPlanDate;
	}
	public void setFinishingPlanQty(Double finishingPlanQty) {
		this.finishingPlanQty = finishingPlanQty;
	}
	public void setFinishingPlanId(Long finishingPlanId) {
		this.finishingPlanId = finishingPlanId;
	}
	public void setFinishingPlanRemarks(String finishingPlanRemarks) {
		this.finishingPlanRemarks = finishingPlanRemarks;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setsActive(String sActive) {
		this.sActive = sActive;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Double getFinishingPlanQtyUnDone() {
		return finishingPlanQtyUnDone;
	}
	public void setFinishingPlanQtyUnDone(Double finishingPlanQtyUnDone) {
		this.finishingPlanQtyUnDone = finishingPlanQtyUnDone;
	}

	
}
