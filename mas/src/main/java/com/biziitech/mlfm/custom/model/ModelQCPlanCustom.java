package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelQCPlanCustom {
	

	
	private Long pOId;
	private Date pODate;
	private Double pOQty;
	private String buyerName;
	private Long buyerId;
	private Long itemId;
	private String itemName;
	
	private Date qCPlanDate;
	private Double qCPlanQty;
	private Long qCPlanId;
	private String qCPlanRemarks;
	private boolean active;
	private String sActive;
	private int activeStatus;
	
	private Double qCPlanQtyUnDone;
	
	private Long uomId;
	private String uomName;
	
	
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
	public Date getqCPlanDate() {
		return qCPlanDate;
	}
	public void setqCPlanDate(Date qCPlanDate) {
		this.qCPlanDate = qCPlanDate;
	}
	public Double getqCPlanQty() {
		return qCPlanQty;
	}
	public void setqCPlanQty(Double qCPlanQty) {
		this.qCPlanQty = qCPlanQty;
	}
	public Long getqCPlanId() {
		return qCPlanId;
	}
	public void setqCPlanId(Long qCPlanId) {
		this.qCPlanId = qCPlanId;
	}
	public String getqCPlanRemarks() {
		return qCPlanRemarks;
	}
	public void setqCPlanRemarks(String qCPlanRemarks) {
		this.qCPlanRemarks = qCPlanRemarks;
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
	public Double getqCPlanQtyUnDone() {
		return qCPlanQtyUnDone;
	}
	public void setqCPlanQtyUnDone(Double qCPlanQtyUnDone) {
		this.qCPlanQtyUnDone = qCPlanQtyUnDone;
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


	

}
