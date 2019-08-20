package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoCluster;
import com.biziitech.mlfm.model.ModelCluster;
import com.biziitech.mlfm.repository.ClusterRepository;

@Service
public class DaoClusterImp implements DaoCluster{
	
	@Autowired
	private ClusterRepository clusterRepository;

	@Override
	public void saveCluster(ModelCluster modelCluster) {
		// TODO Auto-generated method stub
		if(modelCluster.isActive()) 
		{
			
			modelCluster.setActiveStatus(1);
		}
		else 
		{
			
			modelCluster.setActiveStatus(0);
		}
		clusterRepository.save(modelCluster);
		
	}

	@Override
	public List<ModelCluster> getClusterList() {
		// TODO Auto-generated method stub
		List<ModelCluster> resultList=clusterRepository.findAll();
		List<ModelCluster> clusterList=new ArrayList<>();
		
		for(ModelCluster cluster: resultList) {
				if(cluster.getActiveStatus()==1)
				 { cluster.setsActive("Yes");
				 cluster.setActive(true);
				 }
				 
				 else
				 {	 cluster.setsActive("NO");
				 cluster.setActive(false);
				     
				 }
				 
			clusterList.add(cluster);
		}
		
		
		
		return clusterList;
	}

	@Override
	public List<ModelCluster> getClusterName() {
		// TODO Auto-generated method stub
		return clusterRepository.getClusterName();
	}

	@Override
	public List<ModelCluster> getClusterListByCraiteria(String clusterName, String shortCode, String remarks,
			int status) {				
		// TODO Auto-generated method stub
		List<ModelCluster> resultList=clusterRepository.findClusterDetails(clusterName, shortCode, remarks, status);
		List<ModelCluster> clusterList=new ArrayList<>();
		
		for(ModelCluster cluster: resultList) {
			if(cluster.getActiveStatus()==1)
				if(cluster.getActiveStatus()==1)
				 { cluster.setsActive("Yes");
				 cluster.setActive(true);
				 }
				 
				 else
				 {	 cluster.setsActive("NO");
				 cluster.setActive(false);
				     
				 }
				 
			clusterList.add(cluster);
		}
		
		
		
		return clusterList;
	}
	

}
