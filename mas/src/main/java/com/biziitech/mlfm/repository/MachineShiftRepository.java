package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelMachineShift;

public interface MachineShiftRepository extends JpaRepository<ModelMachineShift,Long>  {
	
	

	@Query("select a from MLFM_MACHINE_SHIFT a where a.activeStatus=1 order by a.modelMachine.machineName,a.modelShift.shiftName" )
	public List<ModelMachineShift> getMachineShiftActiveAll();
  @Query("select a from MLFM_MACHINE_SHIFT a where a.activeStatus=1 and a.modelMachine.machineId=:id ")
  public List<ModelMachineShift> findMachineShiftbyName(@Param("id") Long id);
	//@Query("select a from MLFM_MACHINE_SHIFT a where a.activeStatus=1 and a.modelMachine.machineId=:id ")
	//  public List<ModelMachineShift> findMachineShiftbyName(@Param("id") Long id);
	
	//saj - machine_shift_list search query
	@Query("select a from MLFM_MACHINE_SHIFT a  where a.machineShiftName LIKE CONCAT('%',:machineShiftName,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelMachineShift> findMachineShiftDetails(@Param("machineShiftName")String shiftName, @Param("remarks")String remarks, @Param("status") int status);
    //saj - machine_shift_list search query
	
	@Query("select a from MLFM_MACHINE_SHIFT a where a.activeStatus=1 order by a.machineShiftName")
	public List <ModelMachineShift> findMachineShiftList();

}
