package com.biziitech.mlfm.bg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "BG_COUNTRY")
public class ModelCountry {
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name = "COUNTRY_ID")
	private Long countryId;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "SHORT_CODE_ISO")
	private String isoCode;

	@Column(name = "SHORT_CODE_UN")
	private String unCode;

	@Column(name = "NUM_UN")
	private Integer unNum;

	@Column(name = "DIAL_CODE")
	private Integer dialCode;

	@Column(name = "SHORT_CODE")
	private String shortCode;

	@Column(name = "COUNTRY_REMARKS")
	private String remarks;

	@Column(name = "ACTIVE_STATUS")
	private int activeStatus;

	@Column(name = "ENTERED_BY")
	private Long enteredBy;

	@Column(name = "ENTRY_TIMESTAMP")
	private Date entryTimestap;

	@Column(name = "UPDATED_BY")
	@ColumnDefault("0")
	private Long  updatedBy;

	@Column(name = "UPDATE_TIMESTAMP", nullable=true)
	private Date updateTimestap;

	@Column(name = "FLEX_FIELD1")
	private String flex1;

	@Column(name = "FLEX_FIELD2")
	private String flex2;

	@Column(name = "FLEX_FIELD3")
	private String flex3;

	@Column(name = "FLEX_FIELD4")
	private String flex4;

	@Column(name = "FLEX_FIELD5")
	private String flex5;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public ModelCountry() {

	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getUnCode() {
		return unCode;
	}

	public void setUnCode(String unCode) {
		this.unCode = unCode;
	}

	




	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	

	public Date getEntryTimestap() {
		return entryTimestap;
	}

	public void setEntryTimestap(Date entryTimestap) {
		this.entryTimestap = entryTimestap;
	}

	

	public Date getUpdateTimestap() {
		return updateTimestap;
	}

	public void setUpdateTimestap(Date updateTimestap) {
		this.updateTimestap = updateTimestap;
	}

	public String getFlex1() {
		return flex1;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public String getFlex4() {
		return flex4;
	}

	public void setFlex4(String flex4) {
		this.flex4 = flex4;
	}

	public String getFlex5() {
		return flex5;
	}

	public void setFlex5(String flex5) {
		this.flex5 = flex5;
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

	public Integer getUnNum() {
		return unNum;
	}

	public void setUnNum(Integer unNum) {
		this.unNum = unNum;
	}

	public Integer getDialCode() {
		return dialCode;
	}

	public void setDialCode(Integer dialCode) {
		this.dialCode = dialCode;
	}

	public Long getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(Long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
//		return "ModelCountry [countryId=" + countryId + ", countryName=" + countryName + ", isoCode=" + isoCode
//				+ ", unCode=" + unCode + ", unNum=" + unNum + ", dialCode=" + dialCode + ", shortCode=" + shortCode
//				+ ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy
//				+ ", entryTimestap=" + entryTimestap + ", updatedBy=" + updatedBy + ", updateTimestap=" + updateTimestap
//				+ ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5
//				+ ", active=" + active + ", sActive=" + sActive + "]";

		return null;
	}

	
}
