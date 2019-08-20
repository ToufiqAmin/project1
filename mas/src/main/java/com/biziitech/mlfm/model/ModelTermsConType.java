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

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_TERMSCON_TYPE")
public class ModelTermsConType {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="TERMSCON_TYPE_ID")
	private Long termsConTypeId;
	
	@Column(name="TYPE_NAME")
	private String typeName;
	
	@Column(name="SHORT_CODE")
	private String shortCode;
		
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimestamp;
	
	
	
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
	
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;
	
    public ModelTermsConType() {
    	
    }

	public Long getTermsConTypeId() {
		return termsConTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}
	
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;

	
	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public String getFlex1() {
		return flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public String getFlex4() {
		return flex4;
	}

	public String getFlex5() {
		return flex5;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setTermsConTypeId(Long termsConTypeId) {
		this.termsConTypeId = termsConTypeId;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	
	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	
	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public void setFlex4(String flex4) {
		this.flex4 = flex4;
	}

	public void setFlex5(String flex5) {
		this.flex5 = flex5;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	public ModelUser getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(ModelUser enteredBy) {
		this.enteredBy = enteredBy;
	}

	public ModelUser getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(ModelUser updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "ModelTermsConType [termsConTypeId=" + termsConTypeId + ", typeName=" + typeName + ", shortCode="
				+ shortCode + ", remarks=" + remarks + ", activeStatus=" + activeStatus + ", entryTimestamp="
				+ entryTimestamp + ", updateTimestamp=" + updateTimestamp + ", flex1=" + flex1 + ", flex2=" + flex2
				+ ", flex3=" + flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive="
				+ sActive + ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + "]";
	}

	

    

}
