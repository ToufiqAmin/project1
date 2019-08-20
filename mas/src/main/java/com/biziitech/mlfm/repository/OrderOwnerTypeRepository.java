package com.biziitech.mlfm.repository;

import com.biziitech.mlfm.model.ModelOrderOwnerType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderOwnerTypeRepository extends JpaRepository<ModelOrderOwnerType,Long>{
       
	
	@Query("select a from MLFM_ORDER_OWNER_TYPE a where a.activeStatus=1 order by a.orderOwnerTypeName")
	public List<ModelOrderOwnerType> findOwnerType();
}
