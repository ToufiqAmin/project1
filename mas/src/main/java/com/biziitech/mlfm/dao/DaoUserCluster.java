package com.biziitech.mlfm.dao;

import java.util.List;


import com.biziitech.mlfm.model.ModelUserCluster;

public interface DaoUserCluster {
	
	public List<ModelUserCluster> getClusterByUser(Long userId);
	
	public void saveUserCluster(ModelUserCluster modelUserCluster);
	
	public List<ModelUserCluster> findUserClusterByClusterId(Long clusterId);
	public List<ModelUserCluster> getUserClusterList();
	public List<ModelUserCluster> getUserClusterId();
	public List<ModelUserCluster> getUserClusterListByCraiteria(Long userId, Long clusterId, String remarks, int  status);

}
