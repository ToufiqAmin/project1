package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoUserCluster;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.repository.UserClusterRepository;
@Service
public class DaoUserClusterImp implements DaoUserCluster {

	@Autowired
	private UserClusterRepository userClusterRepository;
	
	
	@Override
	public List<ModelUserCluster> getClusterByUser(Long userId) {
		
		return userClusterRepository.getClusterByUser(userId);
	}
	@Override
	public void saveUserCluster(ModelUserCluster modelUserCluster) {
		// TODO Auto-generated method stub
		if(modelUserCluster.isActive()) 
		{
			
			modelUserCluster.setActiveStatus(1);
		}
		else 
		{
			
			modelUserCluster.setActiveStatus(0);
		}
		
		userClusterRepository.save(modelUserCluster);
		
	}
	@Override
	public List<ModelUserCluster> getUserClusterList() {
		// TODO Auto-generated method stub
		List<ModelUserCluster> resultList=userClusterRepository.findUserCluster();
		List<ModelUserCluster> userClusterList=new ArrayList<>();
		
		for(ModelUserCluster userCluster: resultList) {
				if(userCluster.getActiveStatus()==1)
				 { 
				 userCluster.setsActive("Yes");
				 userCluster.setActive(true);
				 }
				 
				 else
				 {
				 userCluster.setsActive("NO");
				 userCluster.setActive(false);
				     
				 }
				 
				userClusterList.add(userCluster);
		}
		
		
		
		return userClusterList;
	}
	@Override
	public List<ModelUserCluster> getUserClusterId() {
		// TODO Auto-generated method stub
		return userClusterRepository.getUserClusterId();
	}
	@Override
	public List<ModelUserCluster> getUserClusterListByCraiteria(Long userId, Long clusterId, String remarks,
			int status) {
		// TODO Auto-generated method stub
		List<ModelUserCluster> resultList=userClusterRepository.findUserClusterDetails(userId, clusterId, remarks, status);
		List<ModelUserCluster> userClusterList=new ArrayList<>();
		
		for(ModelUserCluster userCluster: resultList) {
				if(userCluster.getActiveStatus()==1)
				 { 
				 userCluster.setsActive("Yes");
				 userCluster.setActive(true);
				 }
				 
				 else
				 {
				 userCluster.setsActive("NO");
				 userCluster.setActive(false);
				     
				 }
				 
				userClusterList.add(userCluster);
		}
		
		
		
		return userClusterList;
	}
	@Override
	public List<ModelUserCluster> findUserClusterByClusterId(Long clusterId) {
		// TODO Auto-generated method stub
		return userClusterRepository.getUserClusterByClusterId(clusterId);
	}

}
