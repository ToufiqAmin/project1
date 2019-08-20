package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelOrderOwnerZone;
import com.biziitech.mlfm.model.ModelUOM;

public interface OrderOwnerZoneRepository extends JpaRepository<ModelOrderOwnerZone,Long> {
      
	@Query("select a from MLFM_ORDER_OWNER_ZONE a where a.activeStatus=1 order by a.zoneName")
	public List<ModelOrderOwnerZone> findOrderOwnerZone();
	
	@Query("select a from MLFM_ORDER_OWNER_ZONE a  where a.zoneName LIKE CONCAT('%',:zoneName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelOrderOwnerZone> findOrderOwnerZoneDetails(@Param("zoneName")String zoneName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);
	
}
