package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelPIInquiryList {
	
	
	private Long woMstId;
	private Date woDate;
	private String ownerName;
	private Long orderId;
	private Long orderOwnerId;
	
	// creator : sas date : 1/12/2019 
	private String userWono;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getWoMstId() {
		return woMstId;
	}
	public void setWoMstId(Long woMstId) {
		this.woMstId = woMstId;
	}
	public Date getWoDate() {
		return woDate;
	}
	public void setWoDate(Date woDate) {
		this.woDate = woDate;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	
	public String getUserWono() {
		return userWono;
	}
	public void setUserWono(String userWono) {
		this.userWono = userWono;
	}
	
	

}
