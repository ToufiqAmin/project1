package com.biziitech.mlfm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.biziitech.mlfm.model.ModelUserCluster;

public interface UserClusterRepository extends JpaRepository<ModelUserCluster,Long>{
	
	
	@Query("select a from MLFM_USER_CLUSTER a where a.modelUser.userId=:id")
	public List<ModelUserCluster> getClusterByUser(@Param("id")Long id);
	
	
	@Query("select a from MLFM_USER_CLUSTER a where a.activeStatus=1")
	public List<ModelUserCluster> findUserCluster();
	
	@Query("select a from MLFM_USER_CLUSTER a where a.activeStatus=1" )
	public List<ModelUserCluster> getUserClusterId();
	
	@Query("select a from MLFM_USER_CLUSTER a where a.modelUser.userId=COALESCE(:userId, a.modelUser.userId) and a.modelCluster.clusterId=COALESCE(:clusterId, a.modelCluster.clusterId) and a.remarks LIKE CONCAT('%',:remarks,'%') and a.activeStatus=:status")
	public List <ModelUserCluster> findUserClusterDetails(@Param("userId")Long userId, @Param("clusterId")Long clusterId, @Param("remarks")String remarks, @Param("status") int status);
	
	
	@Query("select a from MLFM_USER_CLUSTER a where a.activeStatus=1 and a.modelCluster.clusterId=:clusterId " )
	public List<ModelUserCluster> getUserClusterByClusterId(@Param("clusterId")Long clusterId);
	

}
