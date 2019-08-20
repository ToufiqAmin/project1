package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelMachineType;

public interface MachineTypeRepository extends JpaRepository<ModelMachineType, Long> {
	
	@Query("select a from MLFM_MACHINE_TYPE a where a.activeStatus=1 order by a.machineTypeName" )
	public List<ModelMachineType> findMachienType();


	@Query("select a from MLFM_MACHINE_TYPE a  where a.machineTypeName LIKE CONCAT('%',:machineTypeName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelMachineType> findMachineTypeDetails(@Param("machineTypeName")String machineTypeName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);

	@Query("select a from MLFM_MACHINE_TYPE a where a.activeStatus=1 order by a.machineTypeName" )
	public List<ModelMachineType> getMachineName();

}
