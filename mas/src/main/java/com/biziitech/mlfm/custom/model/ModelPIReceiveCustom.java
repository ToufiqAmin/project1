package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelPIReceiveCustom {
   
	
	private Long pIReceiveMstId;
	private Long pIReceiveChdId;
	private Date receiveMstDate;
	private Long pIMstId;
	private Long pIChdId;
	private Double receiveAmount;
	private Double pIChdAmount;
	private String orderOwnerName;
	private Long orderOwnerId;
	private int activeStatus;
	public Long getpIReceiveMstId() {
		return pIReceiveMstId;
	}
	public Long getpIReceiveChdId() {
		return pIReceiveChdId;
	}
	public Date getReceiveMstDate() {
		return receiveMstDate;
	}
	public Long getpIMstId() {
		return pIMstId;
	}
	public Long getpIChdId() {
		return pIChdId;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public Double getpIChdAmount() {
		return pIChdAmount;
	}
	public String getOrderOwnerName() {
		return orderOwnerName;
	}
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setpIReceiveMstId(Long pIReceiveMstId) {
		this.pIReceiveMstId = pIReceiveMstId;
	}
	public void setpIReceiveChdId(Long pIReceiveChdId) {
		this.pIReceiveChdId = pIReceiveChdId;
	}
	public void setReceiveMstDate(Date receiveMstDate) {
		this.receiveMstDate = receiveMstDate;
	}
	public void setpIMstId(Long pIMstId) {
		this.pIMstId = pIMstId;
	}
	public void setpIChdId(Long pIChdId) {
		this.pIChdId = pIChdId;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public void setpIChdAmount(Double pIChdAmount) {
		this.pIChdAmount = pIChdAmount;
	}
	public void setOrderOwnerName(String orderOwnerName) {
		this.orderOwnerName = orderOwnerName;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	@Override
	public String toString() {
		return "ModelPIReceiveCustom [pIReceiveMstId=" + pIReceiveMstId + ", pIReceiveChdId=" + pIReceiveChdId
				+ ", receiveMstDate=" + receiveMstDate + ", pIMstId=" + pIMstId + ", pIChdId=" + pIChdId
				+ ", receiveAmount=" + receiveAmount + ", pIChdAmount=" + pIChdAmount + ", orderOwnerName="
				+ orderOwnerName + ", orderOwnerId=" + orderOwnerId + ", activeStatus=" + activeStatus + "]";
	}
	
	
}
