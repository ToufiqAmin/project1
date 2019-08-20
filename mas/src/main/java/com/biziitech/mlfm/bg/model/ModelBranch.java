package com.biziitech.mlfm.bg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity(name="BG_BRANCH")
public class ModelBranch {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="BRANCH_ID")
	private Long branchId;
	
	@Column(name="BRANCH_NAME")
	private String branchName;
	
	@Column(name="SHORT_CODE")
	private String shortCode;
	
	@Column(name="BRANCH_MAIN")
	private int branchMain;
	
	@ManyToOne
	@JoinColumn(name="ORG_ID")
	private ModelOrg orgId;
	
	@ManyToOne
	@JoinColumn(name= "COUNTRY_ID", nullable =true)
	private ModelCountry countryId;
	
	
	@Column(name="BRANCH_ADDRESS")
	private String branchAddress;
	
	@Column(name="BRANCH_EMAIL")
	private String branchEmail;
	
	@Column(name="BRANCH_WEB")
	private String branchWeb;
	
	@Column(name="BRANCH_PHONE")
	private String branchPhone;
	
	@Column(name="POST_CODE")
	private int postCode;
	
	@Column(name="BRANCH_CITY")
	private String branchCity;
	
	
	@Column(name="POLICE_STATION")
	private String policeStation;
	
	@Column(name="BRANCH_UPAZILLA")
	private String branchUpazilla;
	
	@Column(name="BRANCH_DISTRICT")
	private String branchDistrict;
	
	@Column(name="BRANCH_DIVISION")
	private String branchDivision;
	
	@Column(name="REMARKS")
	private String branchRemarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	
	@Column(name="UPDATED_BY")
	private long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP")
	private Date updateTimeStamp;
	
	@Column(name="FLEX_FIELD1")
	private String flexField1;
	
	@Column(name="FLEX_FIELD2")
	private String flexField2;
	
	@Column(name="FLEX_FIELD3")
	private String flexField3;
	
	@Column(name="FLEX_FIELD4")
	private String flexField4;
	
	@Column(name="FLEX_FIELD5")
	private String flexField5;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public int getBranchMain() {
		return branchMain;
	}

	public void setBranchMain(int branchMain) {
		this.branchMain = branchMain;
	}

	public ModelOrg getOrgId() {
		return orgId;
	}

	public void setOrgId(ModelOrg orgId) {
		this.orgId = orgId;
	}

	public ModelCountry getCountryId() {
		return countryId;
	}

	public void setCountryId(ModelCountry countryId) {
		this.countryId = countryId;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchEmail() {
		return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
	}

	public String getBranchWeb() {
		return branchWeb;
	}

	public void setBranchWeb(String branchWeb) {
		this.branchWeb = branchWeb;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public String getBranchCity() {
		return branchCity;
	}

	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public String getBranchUpazilla() {
		return branchUpazilla;
	}

	public void setBranchUpazilla(String branchUpazilla) {
		this.branchUpazilla = branchUpazilla;
	}

	public String getBranchDistrict() {
		return branchDistrict;
	}

	public void setBranchDistrict(String branchDistrict) {
		this.branchDistrict = branchDistrict;
	}

	public String getBranchDivision() {
		return branchDivision;
	}

	public void setBranchDivision(String branchDivision) {
		this.branchDivision = branchDivision;
	}

	public String getBranchRemarks() {
		return branchRemarks;
	}

	public void setBranchRemarks(String branchRemarks) {
		this.branchRemarks = branchRemarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public long getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}

	public String getFlexField1() {
		return flexField1;
	}

	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}

	public String getFlexField2() {
		return flexField2;
	}

	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}

	public String getFlexField3() {
		return flexField3;
	}

	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}

	public String getFlexField4() {
		return flexField4;
	}

	public void setFlexField4(String flexField4) {
		this.flexField4 = flexField4;
	}

	public String getFlexField5() {
		return flexField5;
	}

	public void setFlexField5(String flexField5) {
		this.flexField5 = flexField5;
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
	
	
	

}
