package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelDyingCustom {

	
	private Long wOChdId;
	private Long wOMstId;
	
	private Date wODate;
	
	private Long pOId;
	private Date pODate;
	
	private Long inquiryId;
	private Date inquiryDate;
	private Long buyerId;
	private String buyerName;
	private Long itemId;
	private String itemName;
	private Long uomId;
	private String uomName;
	private Long userId;
	private String userName;
	
	private Long dyingPlanId;
	private Date dyingPlanDate;
	private Double dyingPlanQty;
	
	private Long dyingId;
	private Date dyingDate;
	private Double dyingQty;	
	private Long dyingBy;
	private String dyingRemarks;
	
	private Double dyingQtyUnDone;

	
	private boolean active;
	private String sActive;
	private int activeStatus;


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

	public Double getDyingQty() {
		return dyingQty;
	}

	public void setDyingQty(Double dyingQty) {
		this.dyingQty = dyingQty;
	}

	public Date getDyingDate() {
		return dyingDate;
	}

	public void setDyingDate(Date dyingDate) {
		this.dyingDate = dyingDate;
	}

	public Long getDyingBy() {
		return dyingBy;
	}

	public void setDyingBy(Long dyingBy) {
		this.dyingBy = dyingBy;
	}

	public Double getDyingQtyUnDone() {
		return dyingQtyUnDone;
	}

	public void setDyingQtyUnDone(Double dyingQtyUnDone) {
		this.dyingQtyUnDone = dyingQtyUnDone;
	}

	public Long getDyingId() {
		return dyingId;
	}

	public void setDyingId(Long dyingId) {
		this.dyingId = dyingId;
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

	public String getDyingRemarks() {
		return dyingRemarks;
	}

	public void setDyingRemarks(String dyingRemarks) {
		this.dyingRemarks = dyingRemarks;
	}

	public Long getwOChdId() {
		return wOChdId;
	}

	public Long getwOMstId() {
		return wOMstId;
	}

	public Date getwODate() {
		return wODate;
	}

	public Long getpOId() {
		return pOId;
	}

	public Date getpODate() {
		return pODate;
	}

	public Long getDyingPlanId() {
		return dyingPlanId;
	}

	public Date getDyingPlanDate() {
		return dyingPlanDate;
	}

	public Double getDyingPlanQty() {
		return dyingPlanQty;
	}

	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}

	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}

	public void setwODate(Date wODate) {
		this.wODate = wODate;
	}

	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}

	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}

	public void setDyingPlanId(Long dyingPlanId) {
		this.dyingPlanId = dyingPlanId;
	}

	public void setDyingPlanDate(Date dyingPlanDate) {
		this.dyingPlanDate = dyingPlanDate;
	}

	public void setDyingPlanQty(Double dyingPlanQty) {
		this.dyingPlanQty = dyingPlanQty;
	}
	
}
