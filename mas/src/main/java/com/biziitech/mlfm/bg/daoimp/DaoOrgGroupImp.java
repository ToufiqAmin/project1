package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoOrgGroup;
import com.biziitech.mlfm.bg.model.ModelOrgGroup;

import com.biziitech.mlfm.repository.BgOrgGroupRepository;


@Service
public class DaoOrgGroupImp implements DaoOrgGroup{
	
	@Autowired
	private BgOrgGroupRepository bgOrgGroupRepository;
	private Long orgGroupId;
	
	
public void saveOrgGroup(ModelOrgGroup modelOrgGroup) {
		
		if(modelOrgGroup.isActive()) 
		{
			modelOrgGroup.setActiveStatus(1);
		}else 
		{
			modelOrgGroup.setActiveStatus(0);
		}

		bgOrgGroupRepository.save(modelOrgGroup);
		
	}
@Override
public List<ModelOrgGroup> getOrgGroupListByCraiteria(String groupName,String groupCode,String orgGroupRemarks,int status) {
	List<ModelOrgGroup> resultList = bgOrgGroupRepository.findOrgGroupDetails(groupName,groupCode,orgGroupRemarks,status);
	List<ModelOrgGroup> orgGroupList= new ArrayList<>();
	
	System.out.println("Records : " + resultList.size());
	
	int i=0;
	for(ModelOrgGroup orgGroup: resultList) {
		
	
			if(orgGroup.getActiveStatus()==1)
			 { orgGroup.setsActive("Yes");
			  orgGroup.setActive(true);
			 }
			 
			 else
			 {	 orgGroup.setsActive("NO");
			     orgGroup.setActive(false);
			     
			 }
			i++; 
		orgGroupList.add(orgGroup);
	}
	
	System.out.println("list size " + i);
	return orgGroupList;
}

public Optional<ModelOrgGroup> getGroupById(Long id){
	
	
	Optional<ModelOrgGroup> modelOrgGroup= bgOrgGroupRepository.findById(id);

	if(modelOrgGroup.get().getActiveStatus()==1)
	 {
		 modelOrgGroup.get().setActive(true);
		 
	 }
	 
	 else
	 {	 
		 modelOrgGroup.get().setActive(false);
	 }
	
	setOrgGroupId(modelOrgGroup.get().getOrgGroupId());
	
	return modelOrgGroup;
}



public void setOrgGroupId(Long orgGroupId) {
	this.orgGroupId = orgGroupId;
}
@Override
public List<ModelOrgGroup> findOrgGroup() {
	// TODO Auto-generated method stub
	
	
	List<ModelOrgGroup> resultList=bgOrgGroupRepository.findOrgGroup();
	List<ModelOrgGroup> groupList= new ArrayList<>();
	for(ModelOrgGroup groupList2: resultList) {
	if(groupList2.getActiveStatus()==1)
	 {
		 groupList2.setActive(true);
		 
	 }
	 
	 else
	 {	 
		 groupList2.setActive(false);
	 }
	groupList.add(groupList2);
}
	return groupList;
}
@Override
public List<ModelOrgGroup> getOrgGroupName() {
	// TODO Auto-generated method stub
	return bgOrgGroupRepository.findOrgGroup();
}





}
