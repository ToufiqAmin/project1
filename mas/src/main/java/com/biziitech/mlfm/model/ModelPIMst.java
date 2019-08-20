package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="MLFM_PI_MST")
public class ModelPIMst {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PI_MST_ID")
	private Long pIMstId;
	
	@Column(name="USER_PI_NO")
	private String userPINo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="PI_DATE")
	private Date piDate;
		
	@Column(name="LC_NO")
	private String lcNo;
		
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@Column(name="ENTERED_BY")
	private long enteredBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	@Column(name="UPDATED_BY")
	
	private Long updatedBy;
	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimestamp;
	
	@Column(name="FLEX_FIELD1")
	private String flex1  ;
	
	@Column(name="FLEX_FIELD2")
	private String flex2  ;
	
	@Column(name="FLEX_FIELD3")
	private String flex3  ;
	
	@Column(name="FLEX_FIELD4")
	private String flex4  ;
	
	@Column(name="FLEX_FIELD5")
	private String flex5  ;
	
	@ManyToOne
	@JoinColumn(name="PI_TYPE")
	private ModelPIType modelPIType;
	
	
	@Column(name="TERMSCON_DESC")
	private String termsconDesc;
	
	@ManyToOne
	@JoinColumn(name="ORDER_OWNER_ID")
	private ModelOrderOwner modelOrderOwner;
	
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
    public ModelPIMst() {
    	
    }

	
    public Long getpIMstId() {
		return pIMstId;
	}


	public void setpIMstId(Long pIMstId) {
		this.pIMstId = pIMstId;
	}
    


	public String getUserPINo() {
		return userPINo;
	}



	public void setUserPINo(String userPINo) {
		this.userPINo = userPINo;
	}



	public Date getPiDate() {
		return piDate;
	}



	public void setPiDate(Date piDate) {
		this.piDate = piDate;
	}



	public String getLcNo() {
		return lcNo;
	}



	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
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



	public long getEnteredBy() {
		return enteredBy;
	}



	public void setEnteredBy(long enteredBy) {
		this.enteredBy = enteredBy;
	}



	public Date getEntryTimestamp() {
		return entryTimestamp;
	}



	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}



	public Long getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}



	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
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



	public ModelPIType getModelPIType() {
		return modelPIType;
	}



	public void setModelPIType(ModelPIType modelPIType) {
		this.modelPIType = modelPIType;
	}



	


	public String getTermsconDesc() {
		return termsconDesc;
	}



	public void setTermsconDesc(String termsconDesc) {
		this.termsconDesc = termsconDesc;
	}

   
	public ModelOrderOwner getModelOrderOwner() {
		return modelOrderOwner;
	}



	public void setModelOrderOwner(ModelOrderOwner modelOrderOwner) {
		this.modelOrderOwner = modelOrderOwner;
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



	@Override
	public String toString() {
		return "ModelPIMst [pIMStId=" + pIMstId + ", userPINo=" + userPINo + ", remarks=" + remarks
				+ ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp=" + entryTimestamp
				+ ", updatedBy=" + updatedBy + ", updateTimestamp=" + updateTimestamp + ", flex1=" + flex1 + ", flex2="
				+ flex2 + ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active
				+ ", sActive=" + sActive + ", modelPIType=" + modelPIType + ", termsconDesc=" + termsconDesc + ", modelOrderOwner=" + modelOrderOwner + "]";
	}




}
