package com.biziitech.mlfm.custom.model;

import java.util.Date;
import java.util.List;

import com.biziitech.mlfm.bg.model.ModelUser;


public class ModelQCCustom {
	
	private Long wOChdId;
	private Long wOMstId;
	
	private Date wODate;
	
	private Long productionId;
	
	private Long inquiryId;
	private Date inquiryDate;
	private Long buyerId;
	private String buyerName;
	private Long itemId;
	private String itemName;
	private Long pOId;
	private Date pODate;
	private Long uomId;
	private String uomName;

	
	private Long qCPlanId;
	private Date qCPlanDate;
	private Double qCPlanQty;


	private Long qCId;
	private Date qCDate;
	private Double qCQty;
	private Long qCBy;
	private Long bedNo;
	private Double mendingRequiredQty;
	
	private boolean active;
	private String sActive;
	private int activeStatus;
	private String qCRemarks;
	
	private Long userId;
	private String userName;
	
	private List<ModelUser> modelUserList;
	
	private Double qCQtyMax;
	

	
	
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
	
	
	
	public Double getqCQty() {
		return qCQty;
	}
	public void setqCQty(Double qCQty) {
		this.qCQty = qCQty;
	}
	public Date getqCDate() {
		return qCDate;
	}
	public void setqCDate(Date qCDate) {
		this.qCDate = qCDate;
	}
	public Long getqCBy() {
		return qCBy;
	}
	public void setqCBy(Long qCBy) {
		this.qCBy = qCBy;
	}

	public Long getqCId() {
		return qCId;
	}
	public void setqCId(Long qCId) {
		this.qCId = qCId;
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
	public String getqCRemarks() {
		return qCRemarks;
	}
	public void setqCRemarks(String qCRemarks) {
		this.qCRemarks = qCRemarks;
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
	public Long getqCPlanId() {
		return qCPlanId;
	}
	public void setqCPlanId(Long qCPlanId) {
		this.qCPlanId = qCPlanId;
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
	public Long getBedNo() {
		return bedNo;
	}
	public void setBedNo(Long bedNo) {
		this.bedNo = bedNo;
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
	public Long getProductionId() {
		return productionId;
	}
	public void setProductionId(Long productionId) {
		this.productionId = productionId;
	}
	public Double getMendingRequiredQty() {
		return mendingRequiredQty;
	}
	public void setMendingRequiredQty(Double mendingRequiredQty) {
		this.mendingRequiredQty = mendingRequiredQty;
	}
	public Double getqCQtyMax() {
		return qCQtyMax;
	}
	public void setqCQtyMax(Double qCQtyMax) {
		this.qCQtyMax = qCQtyMax;
	}
	public List<ModelUser> getModelUserList() {
		return modelUserList;
	}
	public void setModelUserList(List<ModelUser> modelUserList) {
		this.modelUserList = modelUserList;
	}

	

}
