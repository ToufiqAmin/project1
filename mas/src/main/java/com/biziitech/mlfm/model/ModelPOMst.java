package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_PO_MST")
public class ModelPOMst {
	
	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="PO_MST_ID")
	private Long pOMstId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="PO_MST_DATE")
	private Date pOMstDate;
	
	@Column(name="USER_PO_NO")
	private String userPONo;
	
	@ManyToOne
	@JoinColumn(name="DESIGN_ID")
	private ModelDesign modelDesign;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACTIVE_STATUS")
	private int activeStatus;
	
	@ManyToOne
	@JoinColumn(name="ENTERED_BY")
	private ModelUser enteredBy;
	
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY")
	private ModelUser updatedBy;
	
	@Column(name="ENTRY_TIMESTAMP")
	private Date entryTimeStamp;
	

	
	@Column(name="UPDATE_TIMESTAMP" , nullable=true)
	private Date updateTimeStamp;
	
	
	@Column(name="FLEX_FIELD1")
	private String flex1  ;
	
	@Column(name="FLEX_FIELD2")
	private String flex2  ;
	
	@Column(name="FLEX_FIELD3")
	private String flex3  ;
	
	
	
	
	public Long getpOMstId() {
		return pOMstId;
	}

	public void setpOMstId(Long pOMstId) {
		this.pOMstId = pOMstId;
	}



	public String getUserPONo() {
		return userPONo;
	}

	public void setUserPONo(String userPONo) {
		this.userPONo = userPONo;
	}

	public ModelDesign getModelDesign() {
		return modelDesign;
	}

	public void setModelDesign(ModelDesign modelDesign) {
		this.modelDesign = modelDesign;
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



	public Date getEntryTimeStamp() {
		return entryTimeStamp;
	}

	public void setEntryTimeStamp(Date entryTimeStamp) {
		this.entryTimeStamp = entryTimeStamp;
	}


	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}

	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
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

	@Column(name="FLEX_FIELD4")
	private String flex4  ;
	
	@Column(name="FLEX_FIELD5")
	private String flex5  ;




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
		return "ModelPOMst [pOMstId=" + pOMstId + ", pOMstDate=" + pOMstDate + ", userPONo=" + userPONo
				+ ", modelDesign=" + modelDesign + ", remarks=" + remarks + ", activeStatus=" + activeStatus
				+ ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimeStamp=" + entryTimeStamp
				+ ", updateTimeStamp=" + updateTimeStamp + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3=" + flex3
				+ ", flex4=" + flex4 + ", flex5=" + flex5 + "]";
	}

	public Date getpOMstDate() {
		return pOMstDate;
	}

	public void setpOMstDate(Date pOMstDate) {
		this.pOMstDate = pOMstDate;
	}
	
	
	
	
	
	
	
	
}
	
	