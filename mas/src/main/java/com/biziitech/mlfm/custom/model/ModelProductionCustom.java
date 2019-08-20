package com.biziitech.mlfm.custom.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.biziitech.mlfm.bg.model.ModelUser;

public class ModelProductionCustom {
	
	
	private Long machineId;
	private Long shiftId;
	private Long uomId;
	private Long machineShiftId;
	
	private Long productionPlanChdId;
	private String uomName;
	private String machineName;
	private String shiftName;
	private double productionPlanQty;
	private double itemQty;
	private double productionQty;
	private Long productionId;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date productionDate;
	private Long orderItemQtyId;
	
	
	// below property for Production Search
		private Long pOId;
		private Date pODate;
		private String ownerName;
		private Long designId;
		private Long machineTypeId;
		private String machineTypeName;
		private String itemName;
		private String dTM;
		private double pOQty;
		private String remarks;
		private int activeStatus;
		private int noOfStitches;
		private Long fabricTypeId;
		private String fabricTypeName;
		private Long wOChdId;
		
		
		// below property for ProductionPlanMst 
		private Long productionPlanMstId;
		private String userName;
		private String refNo;
		private Long userId;
	   @DateTimeFormat(pattern="yyyy-MM-dd")
	   private Date planDate;
	   
	   private String machineShiftName;
	   
	   
	   
		private Long orderId;
		
	
	
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public Long getOrderItemQtyId() {
		return orderItemQtyId;
	}
	public void setOrderItemQtyId(Long orderItemQtyId) {
		this.orderItemQtyId = orderItemQtyId;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}
	public Long getShiftId() {
		return shiftId;
	}
	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}
	public Long getUomId() {
		return uomId;
	}
	public void setUomId(Long uomId) {
		this.uomId = uomId;
	}
	public String getUomName() {
		return uomName;
	}
	public void setUomName(String uomName) {
		this.uomName = uomName;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
	@Override
	public String toString() {
		return "ModelProductionCustom [orderItemQtyId=" + orderItemQtyId + ", machineId=" + machineId + ", shiftId="
				+ shiftId + ", uomId=" + uomId + ", uomName=" + uomName + ", machineName=" + machineName
				+ ", shiftName=" + shiftName + "]";
	}
	public double getProductionPlanQty() {
		return productionPlanQty;
	}
	public void setProductionPlanQty(double productionPlanQty) {
		this.productionPlanQty = productionPlanQty;
	}
	public double getItemQty() {
		return itemQty;
	}
	public void setItemQty(double itemQty) {
		this.itemQty = itemQty;
	}
	public Long getMachineShiftId() {
		return machineShiftId;
	}
	public void setMachineShiftId(Long machineShiftId) {
		this.machineShiftId = machineShiftId;
	}
	public Long getDesignId() {
		return designId;
	}
	public void setDesignId(Long designId) {
		this.designId = designId;
	}
	public Long getProductionPlanChdId() {
		return productionPlanChdId;
	}
	public void setProductionPlanChdId(Long productionPlanChdId) {
		this.productionPlanChdId = productionPlanChdId;
	}
	public double getProductionQty() {
		return productionQty;
	}
	public void setProductionQty(double productionQty) {
		this.productionQty = productionQty;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public Long getProductionId() {
		return productionId;
	}
	public void setProductionId(Long productionId) {
		this.productionId = productionId;
	}
	public Long getpOId() {
		return pOId;
	}
	public void setpOId(Long pOId) {
		this.pOId = pOId;
	}
	public Date getpODate() {
		return pODate;
	}
	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}
	public Long getMachineTypeId() {
		return machineTypeId;
	}
	public void setMachineTypeId(Long machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
	public String getMachineTypeName() {
		return machineTypeName;
	}
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getdTM() {
		return dTM;
	}
	public void setdTM(String dTM) {
		this.dTM = dTM;
	}
	public double getpOQty() {
		return pOQty;
	}
	public void setpOQty(double pOQty) {
		this.pOQty = pOQty;
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
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getNoOfStitches() {
		return noOfStitches;
	}
	public void setNoOfStitches(int noOfStitches) {
		this.noOfStitches = noOfStitches;
	}
	public Long getProductionPlanMstId() {
		return productionPlanMstId;
	}
	public void setProductionPlanMstId(Long productionPlanMstId) {
		this.productionPlanMstId = productionPlanMstId;
	}
	
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMachineShiftName() {
		return machineShiftName;
	}
	public void setMachineShiftName(String machineShiftName) {
		this.machineShiftName = machineShiftName;
	}
	public Long getFabricTypeId() {
		return fabricTypeId;
	}
	public void setFabricTypeId(Long fabricTypeId) {
		this.fabricTypeId = fabricTypeId;
	}
	public String getFabricTypeName() {
		return fabricTypeName;
	}
	public void setFabricTypeName(String fabricTypeName) {
		this.fabricTypeName = fabricTypeName;
	}
	public Long getwOChdId() {
		return wOChdId;
	}
	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	
	

}
