package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelCluster;


public interface ClusterRepository extends JpaRepository<ModelCluster,Long>{
	
	@Query("select a from MLFM_CLUSTER a where a.activeStatus=1 order by a.clusterName")
	public List<ModelCluster> findCluster();
	
	@Query("select a from MLFM_CLUSTER a where a.activeStatus=1 order by a.clusterName" )
	public List<ModelCluster> getClusterName();
	
	@Query("select a from MLFM_CLUSTER a  where a.clusterName LIKE CONCAT('%',:clusterName,'%') and a.shortCode LIKE CONCAT('%',:shortCode,'%') and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelCluster> findClusterDetails(@Param("clusterName")String clusterName, @Param("shortCode")String shortCode, @Param("remarks")String remarks, @Param("status") int status);

}
