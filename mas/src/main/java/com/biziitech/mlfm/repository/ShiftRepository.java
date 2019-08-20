package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelShift;


public interface ShiftRepository extends JpaRepository<ModelShift,Long> {
	
	@Query("select a from MLFM_SHIFT a  where a.shiftName LIKE CONCAT('%',:shiftName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelShift> findShiftDetails(@Param("shiftName")String shiftName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);
    
	@Query("select a from MLFM_SHIFT a where a.activeStatus=1 order by a.shiftName" )
	public List<ModelShift> findShift();
	


}
