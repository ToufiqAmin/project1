package com.biziitech.mlfm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

@Entity(name="MLFM_GATE_PASS_MST")
public class ModelGatePassMst {

	@Id @GenericGenerator(name = "custom_sequence", strategy = 
			"com.biziitech.mlfm.IdGenerator")
	@GeneratedValue(generator = "custom_sequence")
	@Column(name="GATE_PASS_MST_ID")
	private Long gatePassMstId;
    
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="GATE_PASS_DATE")
	private Date gatePassDate;
	
	@Column(name="REF_NO")
	private String refNo;
	
	@Column(name="ISSUE_DEPT_ID")
	private Long issueDeptId;
	
	@ManyToOne
	@JoinColumn(name="ORDER_OWNER_ID")
	private ModelOrderOwner modelOrderOwner;
	
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
	
	@Column(name="UPDATE_TIMESTAMP")
	private Date updateTimestamp;
	
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
	
	@Column(name="VEHICLE_TYPE_NAME")
	private String vehicleTypeName;	
	
	@Column(name="VEHICLE_NUMBER")
	private String vehicleNumber;
	
	@Column(name="VEHICLE_DRIVER_NAME")
	private String vehicleDriverName;
	
	@Column(name="VEHICLE_DRIVER_CONTACT_NO")
	private String vehicleDriverContactNo;
	
	
	@Column(name="PURPOSE")
	private String purpose;
	
	@ManyToOne
	@JoinColumn(name="ISSUER_ID")
	private ModelUser modelUser;
	
	@Column(name="GATE_PASS_TYPE")
	private int gatePassType;
	
	@Transient
	private boolean active;
	
	@Transient
	private String sActive;

    public ModelGatePassMst() {
    	
    }

	public Long getGatePassMstId() {
		return gatePassMstId;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public String getRefNo() {
		return refNo;
	}

	public Long getIssueDeptId() {
		return issueDeptId;
	}

	public ModelOrderOwner getModelOrderOwner() {
		return modelOrderOwner;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getActiveStatus() {
		return activeStatus;
	}

	public long getEnteredBy() {
		return enteredBy;
	}

	public Date getEntryTimestamp() {
		return entryTimestamp;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public String getFlexField1() {
		return flexField1;
	}

	public String getFlexField2() {
		return flexField2;
	}

	public String getFlexField3() {
		return flexField3;
	}

	public String getFlexField4() {
		return flexField4;
	}

	public String getFlexField5() {
		return flexField5;
	}

	public String getVehicleTypeName() {
		return vehicleTypeName;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public String getVehicleDriverName() {
		return vehicleDriverName;
	}

	public String getVehicleDriverContactNo() {
		return vehicleDriverContactNo;
	}

	public String getPurpose() {
		return purpose;
	}

	public ModelUser getModelUser() {
		return modelUser;
	}

	public int getGatePassType() {
		return gatePassType;
	}

	public boolean isActive() {
		return active;
	}

	public String getsActive() {
		return sActive;
	}

	public void setGatePassMstId(Long gatePassMstId) {
		this.gatePassMstId = gatePassMstId;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public void setIssueDeptId(Long issueDeptId) {
		this.issueDeptId = issueDeptId;
	}

	public void setModelOrderOwner(ModelOrderOwner modelOrderOwner) {
		this.modelOrderOwner = modelOrderOwner;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setEnteredBy(long enteredBy) {
		this.enteredBy = enteredBy;
	}

	public void setEntryTimestamp(Date entryTimestamp) {
		this.entryTimestamp = entryTimestamp;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}

	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}

	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}

	public void setFlexField4(String flexField4) {
		this.flexField4 = flexField4;
	}

	public void setFlexField5(String flexField5) {
		this.flexField5 = flexField5;
	}

	public void setVehicleTypeName(String vehicleTypeName) {
		this.vehicleTypeName = vehicleTypeName;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void setVehicleDriverName(String vehicleDriverName) {
		this.vehicleDriverName = vehicleDriverName;
	}

	public void setVehicleDriverContactNo(String vehicleDriverContactNo) {
		this.vehicleDriverContactNo = vehicleDriverContactNo;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setModelUser(ModelUser modelUser) {
		this.modelUser = modelUser;
	}

	public void setGatePassType(int gatePassType) {
		this.gatePassType = gatePassType;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setsActive(String sActive) {
		this.sActive = sActive;
	}

	@Override
	public String toString() {
		return "ModelGatePassMst [gatePassMstId=" + gatePassMstId + ", gatePassDate=" + gatePassDate + ", refNo="
				+ refNo + ", issueDeptId=" + issueDeptId + ", modelOrderOwner=" + modelOrderOwner + ", remarks="
				+ remarks + ", activeStatus=" + activeStatus + ", enteredBy=" + enteredBy + ", entryTimestamp="
				+ entryTimestamp + ", updatedBy=" + updatedBy + ", updateTimestamp=" + updateTimestamp + ", flexField1="
				+ flexField1 + ", flexField2=" + flexField2 + ", flexField3=" + flexField3 + ", flexField4="
				+ flexField4 + ", flexField5=" + flexField5 + ", vehicleTypeName=" + vehicleTypeName
				+ ", vehicleNumber=" + vehicleNumber + ", vehicleDriverName=" + vehicleDriverName
				+ ", vehicleDriverContactNo=" + vehicleDriverContactNo + ", purpose=" + purpose + ", modelUser="
				+ modelUser + ", gatePassType=" + gatePassType + ", active=" + active + ", sActive=" + sActive + "]";
	}

	
	
  	 	
}
