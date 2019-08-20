package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.model.ModelCluster;


public interface DaoCluster {

	public void saveCluster(ModelCluster modelCluster);
	
	public List<ModelCluster> getClusterList();
	public List<ModelCluster> getClusterName();
	public List<ModelCluster> getClusterListByCraiteria(String clusterName, String shortCode, String remarks, int  status);
	
	
}
