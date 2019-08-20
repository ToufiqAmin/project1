package com.biziitech.mlfm.custom.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelPOCustom {
	
	private Long wOMstId;
	private Long wOChdId;
	private Long orderItemQuantityId;
	private Long productionPlanChdId;
	private Long productionPlanMstId;
	
	private Long orderOwnerId;
	private String orderOwnerName;
	private String itemName;
	
	private Long designId;
	
	private Long machineTypeId;
	private String machineTypeName;
	private String machineName;
	private Long machineShiftId;
	
	private String shiftName;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date productionPlanDate;
	private double productionPlanQty;
	
	private Date pODate;
	
	private Date planMstDate;
	private Date planChdDate;
	
	private String refNo;
	private String remarks;
	
	private Long plannedById;
	private String plannedByName;
	
	private String remarksChd;
	
	private Integer laceTypeId;
	private String laceTypeName;
	
	private Long fabricTypeId;
	private String fabricTypeName;
	
	private Long itemId;
	
	private String dTM;
	
	private Double machineCapacityQty;
	private String machineCapacityUOMName;
	
	private Long pOMstId;
	private Date pOMstDate;
	
	private String userPONo;
	
	private Long pOChdId;
	private Date pOChdDate;
	private double pOQty;
	private int activeStatus;
	
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	
	public Long getOrderOwnerId() {
		return orderOwnerId;
	}
	public void setOrderOwnerId(Long orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getwOMstId() {
		return wOMstId;
	}
	public void setwOMstId(Long wOMstId) {
		this.wOMstId = wOMstId;
	}
	public Long getOrderItemQuantityId() {
		return orderItemQuantityId;
	}
	public void setOrderItemQuantityId(Long orderItemQuantityId) {
		this.orderItemQuantityId = orderItemQuantityId;
	}
	public String getOrderOwnerName() {
		return orderOwnerName;
	}
	public void setOrderOwnerName(String orderOwnerName) {
		this.orderOwnerName = orderOwnerName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getDesignId() {
		return designId;
	}
	public void setDesignId(Long designId) {
		this.designId = designId;
	}
	public String getMachineTypeName() {
		return machineTypeName;
	}
	public void setMachineTypeName(String machineTypeName) {
		this.machineTypeName = machineTypeName;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public Long getMachineShiftId() {
		return machineShiftId;
	}
	public void setMachineShiftId(Long machineShiftId) {
		this.machineShiftId = machineShiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public double getProductionPlanQty() {
		return productionPlanQty;
	}
	public void setProductionPlanQty(double productionPlanQty) {
		this.productionPlanQty = productionPlanQty;
	}
	public Long getProductionPlanChdId() {
		return productionPlanChdId;
	}
	public void setProductionPlanChdId(Long productionPlanChdId) {
		this.productionPlanChdId = productionPlanChdId;
	}
	public Long getMachineTypeId() {
		return machineTypeId;
	}
	public void setMachineTypeId(Long machineTypeId) {
		this.machineTypeId = machineTypeId;
	}
	public Date getProductionPlanDate() {
		return productionPlanDate;
	}
	public void setProductionPlanDate(Date productionPlanDate) {
		this.productionPlanDate = productionPlanDate;
	}
	public Date getpODate() {
		return pODate;
	}
	public void setpODate(Date pODate) {
		this.pODate = pODate;
	}
	public Long getProductionPlanMstId() {
		return productionPlanMstId;
	}
	public void setProductionPlanMstId(Long productionPlanMstId) {
		this.productionPlanMstId = productionPlanMstId;
	}
	public Date getPlanMstDate() {
		return planMstDate;
	}
	public void setPlanMstDate(Date planMstDate) {
		this.planMstDate = planMstDate;
	}
	public Date getPlanChdDate() {
		return planChdDate;
	}
	public void setPlanChdDate(Date planChdDate) {
		this.planChdDate = planChdDate;
	}
	public Long getwOChdId() {
		return wOChdId;
	}
	public void setwOChdId(Long wOChdId) {
		this.wOChdId = wOChdId;
	}
	public Long getPlannedById() {
		return plannedById;
	}
	public void setPlannedById(Long plannedById) {
		this.plannedById = plannedById;
	}
	public String getPlannedByName() {
		return plannedByName;
	}
	public void setPlannedByName(String plannedByName) {
		this.plannedByName = plannedByName;
	}
	public String getRemarksChd() {
		return remarksChd;
	}
	public void setRemarksChd(String remarksChd) {
		this.remarksChd = remarksChd;
	}
	public Integer getLaceTypeId() {
		return laceTypeId;
	}
	public void setLaceTypeId(Integer laceTypeId) {
		this.laceTypeId = laceTypeId;
	}
	public String getLaceTypeName() {
		return laceTypeName;
	}
	public void setLaceTypeName(String laceTypeName) {
		this.laceTypeName = laceTypeName;
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
	public String getdTM() {
		return dTM;
	}
	public void setdTM(String dTM) {
		this.dTM = dTM;
	}
	public Double getMachineCapacityQty() {
		return machineCapacityQty;
	}
	public void setMachineCapacityQty(Double machineCapacityQty) {
		this.machineCapacityQty = machineCapacityQty;
	}
	public String getMachineCapacityUOMName() {
		return machineCapacityUOMName;
	}
	public void setMachineCapacityUOMName(String machineCapacityUOMName) {
		this.machineCapacityUOMName = machineCapacityUOMName;
	}
	public Long getpOMstId() {
		return pOMstId;
	}
	public void setpOMstId(Long pOMstId) {
		this.pOMstId = pOMstId;
	}
	public Date getpOMstDate() {
		return pOMstDate;
	}
	public void setpOMstDate(Date pOMstDate) {
		this.pOMstDate = pOMstDate;
	}
	public String getUserPONo() {
		return userPONo;
	}
	public void setUserPONo(String userPONo) {
		this.userPONo = userPONo;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Long getpOChdId() {
		return pOChdId;
	}
	public void setpOChdId(Long pOChdId) {
		this.pOChdId = pOChdId;
	}
	public Date getpOChdDate() {
		return pOChdDate;
	}
	public void setpOChdDate(Date pOChdDate) {
		this.pOChdDate = pOChdDate;
	}
	public double getpOQty() {
		return pOQty;
	}
	public void setpOQty(double pOQty) {
		this.pOQty = pOQty;
	}
	
	
	

}
