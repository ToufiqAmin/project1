package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelDyingPlanCustom {

	
	private Long pOId;
	private Date pODate;
	private Double pOQty;
	private String buyerName;
	private Long buyerId;
	private Long itemId;
	private String itemName;
	private Long uomId;
	private String uomName;
	
	private Date dyingPlanDate;
	private Double dyingPlanQty;
	private Long dyingPlanId;
	private String dyingPlanRemarks;
	private boolean active;
	private String sActive;
	private int activeStatus;
	
	
	private Double dyingPlanQtyUnDone;


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


	public Date getDyingPlanDate() {
		return dyingPlanDate;
	}


	public Double getDyingPlanQty() {
		return dyingPlanQty;
	}


	public Long getDyingPlanId() {
		return dyingPlanId;
	}


	public String getDyingPlanRemarks() {
		return dyingPlanRemarks;
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


	public Double getDyingPlanQtyUnDone() {
		return dyingPlanQtyUnDone;
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


	public void setDyingPlanDate(Date dyingPlanDate) {
		this.dyingPlanDate = dyingPlanDate;
	}


	public void setDyingPlanQty(Double dyingPlanQty) {
		this.dyingPlanQty = dyingPlanQty;
	}


	public void setDyingPlanId(Long dyingPlanId) {
		this.dyingPlanId = dyingPlanId;
	}


	public void setDyingPlanRemarks(String dyingPlanRemarks) {
		this.dyingPlanRemarks = dyingPlanRemarks;
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


	public void setDyingPlanQtyUnDone(Double dyingPlanQtyUnDone) {
		this.dyingPlanQtyUnDone = dyingPlanQtyUnDone;
	}
	
}
