package com.biziitech.mlfm.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelOrderOwner;

public interface MachineRepository extends JpaRepository<ModelMachine,Long>{
	
	
	@Query("select a from MLFM_MACHINE a where a.activeStatus=1 order by a.machineName" )
	public List<ModelMachine> findMachine();


	@Query("select a from MLFM_MACHINE a  where a.machineName LIKE CONCAT('%',:machineName,'%') and a.capacityPerShiftPerDay LIKE CONCAT('%',:capacityPerShiftPerDay,'%') and a.modelUOM.uomName LIKE CONCAT('%',:capacityUom,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelMachine> findMachineDetails(@Param("machineName")String machineName, @Param("capacityPerShiftPerDay")String capacityPerShiftPerDay,  @Param("capacityUom")String capacityUom, @Param("remarks")String remarks, @Param("status") int status);

	@Query("select a from MLFM_MACHINE a where a.activeStatus=1 and a.modelMachineType.machineTypeId=:id")
	public List<ModelMachine> findMachinebyType(@Param("id") Long id);
	
	

}
