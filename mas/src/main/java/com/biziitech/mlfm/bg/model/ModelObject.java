package com.biziitech.mlfm.bg.model;

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

@Entity(name="BG_OBJECT")
public class ModelObject {
		
		@Id @GenericGenerator(name = "custom_sequence", strategy = 
				"com.biziitech.mlfm.IdGenerator")
		@GeneratedValue(generator = "custom_sequence")
		@Column(name="OBJECT_ID")
		private Long objectId;
		
		@Column(name="OBJECT_NAME")
		private String objectName;
		
		@Column(name="FILE_PATH")
		private String filePath;
		
		@Column(name="SL_NO")
		private int slNo;
		
		@Column(name="IMAGE_PATH")
		private String imagePath;
		
		@Column(name="OBJECT_CODE")
		private String objectCode;
		
		@ManyToOne
		@JoinColumn(name="MODULE_ID")
		private ModelModule modelModule;
		
		@ManyToOne
		@JoinColumn(name="OBJECT_GROUP_ID")
		private ModelObjectGroup modelObjectGroup;
		
		@ManyToOne
		@JoinColumn(name="OBJECT_TYPE_ID")
		private ModelObjectType modelObjectType;
		
		@Column(name="NOT_SHOW")
		private int notShow;
		
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
		private Date entryTimestamp;
		
		
		
		@Column(name="UPDATE_TIMESTAMP" , nullable=true)
		private Date updateTimestap;
		

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
		
		
		public ModelObject() {
			
		}

		public Long getObjectId() {
			return objectId;
		}

		public void setObjectId(Long objectId) {
			this.objectId = objectId;
		}

		public String getObjectName() {
			return objectName;
		}

		public void setObjectName(String objectName) {
			this.objectName = objectName;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public int getSlNo() {
			return slNo;
		}

		public void setSlNo(int slNo) {
			this.slNo = slNo;
		}
		
		

		public String getImagePath() {
			return imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}

		public String getObjectCode() {
			return objectCode;
		}

		public void setObjectCode(String objectCode) {
			this.objectCode = objectCode;
		}

		public ModelModule getModelModule() {
			return modelModule;
		}

		public void setModelModule(ModelModule modelModule) {
			this.modelModule = modelModule;
		}

	

		public ModelObjectGroup getModelObjectGroup() {
			return modelObjectGroup;
		}

		public void setModelObjectGroup(ModelObjectGroup modelObjectGroup) {
			this.modelObjectGroup = modelObjectGroup;
		}

		
		
		public ModelObjectType getModelObjectType() {
			return modelObjectType;
		}

		public void setModelObjectType(ModelObjectType modelObjectType) {
			this.modelObjectType = modelObjectType;
		}

		public int getNotShow() {
			return notShow;
		}

		public void setNotShow(int notShow) {
			this.notShow = notShow;
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

		

		public Date getEntryTimestamp() {
			return entryTimestamp;
		}

		public void setEntryTimestamp(Date entryTimestamp) {
			this.entryTimestamp = entryTimestamp;
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
			return "ModelObject [objectId=" + objectId + ", objectName=" + objectName + ", filePath=" + filePath
					+ ", slNo=" + slNo + ", imagePath=" + imagePath + ", objectCode=" + objectCode + ", modelModule="
					+ modelModule + ", modelObjectGroup=" + modelObjectGroup + ", modelObjectType=" + modelObjectType
					+ ", notShow=" + notShow + ", remarks=" + remarks + ", activeStatus=" + activeStatus
					+ ", enteredBy=" + enteredBy + ", updatedBy=" + updatedBy + ", entryTimestamp=" + entryTimestamp
					+ ", updateTimestap=" + updateTimestap + ", flex1=" + flex1 + ", flex2=" + flex2 + ", flex3="
					+ flex3 + ", flex4=" + flex4 + ", flex5=" + flex5 + ", active=" + active + ", sActive=" + sActive
					+ "]";
		}
		

	
}
