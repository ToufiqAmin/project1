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

import com.biziitech.mlfm.bg.model.ModelCurrency;


@Entity(name="MLFM_PI_CHD")
public class ModelPIChd {

		@Id @GenericGenerator(name = "custom_sequence", strategy = 
				"com.biziitech.mlfm.IdGenerator")
		@GeneratedValue(generator = "custom_sequence")
		@Column(name="PI_CHD_ID")
		private Long pIChdId;
		
		
		@ManyToOne
		@JoinColumn(name="PI_MST_ID")
		private ModelPIMst modelPIMst;
		
		@ManyToOne
		@JoinColumn(name="WO_CHD_ID")
		private ModelWOChd modelWOChd;
			
			
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
		
		@Column(name="ITEM_QTY")
		private double itemQty  ;
		
		@ManyToOne
		@JoinColumn(name="UOM_ID")
		private ModelUOM modelUOM  ;
		
		@Column(name="ITEM_RATE")
		private double itemRate  ;
		
		@ManyToOne
		@JoinColumn(name="CURRENCY_ID")
		private ModelCurrency modelCurrency  ;
		
		
		@Transient
		private boolean active;
		
		@Transient
		private String sActive;
		
	    public ModelPIChd() {
	    	
	    }

		public Long getpIChdId() {
			return pIChdId;
		}

		public ModelPIMst getModelPIMst() {
			return modelPIMst;
		}

		public ModelWOChd getModelWOChd() {
			return modelWOChd;
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

		public double getItemQty() {
			return itemQty;
		}

		public ModelUOM getModelUOM() {
			return modelUOM;
		}

		public double getItemRate() {
			return itemRate;
		}


		public boolean isActive() {
			return active;
		}

		public String getsActive() {
			return sActive;
		}

		public void setpIChdId(Long pIChdId) {
			this.pIChdId = pIChdId;
		}

		public void setModelPIMst(ModelPIMst modelPIMst) {
			this.modelPIMst = modelPIMst;
		}

		public void setModelWOChd(ModelWOChd modelWOChd) {
			this.modelWOChd = modelWOChd;
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

		public void setItemQty(double itemQty) {
			this.itemQty = itemQty;
		}

		public void setModelUOM(ModelUOM modelUOM) {
			this.modelUOM = modelUOM;
		}

		public void setItemRate(double itemRate) {
			this.itemRate = itemRate;
		}
       
		

		public void setActive(boolean active) {
			this.active = active;
		}

		public void setsActive(String sActive) {
			this.sActive = sActive;
		}

		public ModelCurrency getModelCurrency() {
			return modelCurrency;
		}

		public void setModelCurrency(ModelCurrency modelCurrency) {
			this.modelCurrency = modelCurrency;
		}

		
		
}
