package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelGatePassCustom {

	private Long orderId;
	private Long gatePassMstId;
	private Date gatePassDate;
	private Long orderOwnerId;
	private String ownerName;
	private Long issuer;
	private String issuerName;
	private String purpose;
	private String vehicleTypeName;
	private String vehicleNumber;
	private String driverName;
	private String remarks;
	private String contactNo;
	private int gatePassType;
	
	
	
	public Long getOrderId() {
		return orderId;
	}
	public Long getGatePassMstId() {
		return gatePassMstId;
	}
	public Date getGatePassDate() {
		return gatePassDate;
	}
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public Long getIssuer() {
		return issuer;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public String getPurpose() {
		return purpose;
	}
	public String getVehicleTypeName() {
		return vehicleTypeName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public int getGatePassType() {
		return gatePassType;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void setGatePassMstId(Long gatePassMstId) {
		this.gatePassMstId = gatePassMstId;
	}
	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setIssuer(Long issuer) {
		this.issuer = issuer;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setGatePassType(int gatePassType) {
		this.gatePassType = gatePassType;
	}
	@Override
	public String toString() {
		return "ModelGatePassCustom [orderId=" + orderId + ", gatePassMstId=" + gatePassMstId + ", gatePassDate="
				+ gatePassDate + ", orderOwnerId=" + orderOwnerId + ", ownerName=" + ownerName + ", issuer=" + issuer
				+ ", issuerName=" + issuerName + ", purpose=" + purpose + ", vehicleTypeName=" + vehicleTypeName
				+ ", vehicleNumber=" + vehicleNumber + ", driverName=" + driverName + ", remarks=" + remarks
				+ ", contactNo=" + contactNo + ", gatePassType=" + gatePassType + "]";
	}
	
	
	
	
	
}
