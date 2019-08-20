package com.biziitech.mlfm.custom.model;

import java.util.Date;

public class ModelDeliverChallanCustom {

	private Long deliverChallanId;
	private Long pOMstId;
	private Long uOMId;
	private Long itemId;
	private String uOMName;
	private String itemName;
	private String uomName;
	private Date deliverDate;
	private Date challanDate;
	private String deliveredBy;
	private String size;
	private String grade;
	private Double challanQty;
	private String remarks;
	private String description;
	private String challanNo;
	private Long userId;
	private int status;
	public Long getDeliverChallanId() {
		return deliverChallanId;
	}
	public Long getpOMstId() {
		return pOMstId;
	}
	public Long getuOMId() {
		return uOMId;
	}
	public Long getItemId() {
		return itemId;
	}
	public String getuOMName() {
		return uOMName;
	}
	public String getItemName() {
		return itemName;
	}
	public String getUomName() {
		return uomName;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public Date getChallanDate() {
		return challanDate;
	}
	public String getDeliveredBy() {
		return deliveredBy;
	}
	public String getSize() {
		return size;
	}
	public String getGrade() {
		return grade;
	}
	public Double getChallanQty() {
		return challanQty;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getDescription() {
		return description;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public Long getUserId() {
		return userId;
	}
	public int getStatus() {
		return status;
	}
	public void setDeliverChallanId(Long deliverChallanId) {
		this.deliverChallanId = deliverChallanId;
	}
	public void setpOMstId(Long pOMstId) {
		this.pOMstId = pOMstId;
	}
	public void setuOMId(Long uOMId) {
		this.uOMId = uOMId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public void setuOMName(String uOMName) {
		this.uOMName = uOMName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}
	public void setDeliveredBy(String deliveredBy) {
		this.deliveredBy = deliveredBy;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setChallanQty(Double challanQty) {
		this.challanQty = challanQty;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ModelDeliverChallanCustom [deliverChallanId=" + deliverChallanId + ", pOMstId=" + pOMstId + ", uOMId="
				+ uOMId + ", itemId=" + itemId + ", uOMName=" + uOMName + ", itemName=" + itemName + ", uomName="
				+ uomName + ", deliverDate=" + deliverDate + ", challanDate=" + challanDate + ", deliveredBy="
				+ deliveredBy + ", size=" + size + ", grade=" + grade + ", challanQty=" + challanQty + ", remarks="
				+ remarks + ", description=" + description + ", challanNo=" + challanNo + ", userId=" + userId
				+ ", status=" + status + "]";
	}
	
	
	
	
}
