package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelGatePassMst;

public interface GatePassRepository extends JpaRepository<ModelGatePassMst,Long> {
   
	
	@Query("select a from MLFM_GATE_PASS_MST a where a.gatePassMstId=:id and a.activeStatus=1")
	public List<ModelGatePassMst> findGatePassDataById(@Param("id") Long id);
}
