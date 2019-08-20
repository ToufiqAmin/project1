package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelBuyer;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelUOM;

public interface UOMRepository extends JpaRepository <ModelUOM,Long> {

@Query("select a from MLFM_UOM a where a.activeStatus=1 order by a.uomName" )
public List<ModelUOM> findUOM();


@Query("select a from MLFM_UOM a  where a.uomName LIKE CONCAT('%',:uomName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
public List <ModelUOM> findUOMDetails(@Param("uomName")String uomName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);

@Query("select a from MLFM_UOM a where a.activeStatus=1 order by a.uomName" )
public List<ModelUOM> getUomName();



}
