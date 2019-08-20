package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoOrg;
import com.biziitech.mlfm.bg.model.ModelOrg;

import com.biziitech.mlfm.repository.BgOrgRepository;


@Service
public class DaoOrgImp implements DaoOrg{
	
	@Autowired
	private BgOrgRepository BgOrgRepository;
	
	private Long orgId;
	
	@Override
	public List<ModelOrg> getOrgList() {
		
		List<ModelOrg> resultList = BgOrgRepository.findOrg();
		List<ModelOrg> orgList= new ArrayList<>();
		
		for(ModelOrg org: resultList) {
			if(org.getActiveStatus()==1)
				if(org.getActiveStatus()==1)
				 { org.setsActive("Yes");
				  org.setActive(true);
				 }
				 
				 else
				 {	 org.setsActive("NO");
				     org.setActive(false);
				     
				 }
				 
			orgList.add(org);
		}
		
		
		
		return orgList;
	}

	@Override
	public List<ModelOrg> getOrgName() {
		// TODO Auto-generated method stub
		return BgOrgRepository.findOrg();
	}

	@Override
	public Optional<ModelOrg> getOrgById(Long id) {
		// TODO Auto-generated method stub
		
		Optional<ModelOrg> modelOrg= BgOrgRepository.findById(id);
		
		if(modelOrg.get().getActiveStatus()==1)
		 {
			 modelOrg.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 modelOrg.get().setActive(false);
		 }
		
		setOrgId(modelOrg.get().getOrgId());
		
		return modelOrg;
	}

	private void setOrgId(Long orgId) {
		// TODO Auto-generated method stub
		this.orgId=orgId;
		
	}

	@Override
	public void saveOrg(ModelOrg modelOrg) {
		// TODO Auto-generated method stub
		
		if(modelOrg.isActive()) 
		{
			modelOrg.setActiveStatus(1);
		}else {
			modelOrg.setActiveStatus(0);
		}
		
		BgOrgRepository.save(modelOrg);
		
	}

	@Override
	public List<ModelOrg> getOrgByOrgGroupId(Long id) {
		// TODO Auto-generated method stub
		List<ModelOrg> resultList = BgOrgRepository.findOrgByOrgGroup(id);
		List<ModelOrg> orgList= new ArrayList<>();
		
		for(ModelOrg org: resultList) {
			if(org.getActiveStatus()==1)
				if(org.getActiveStatus()==1)
				 { org.setsActive("Yes");
				  org.setActive(true);
				 }
				 
				 else
				 {	 org.setsActive("NO");
				     org.setActive(false);
				     
				 }
				 
			orgList.add(org);
		}
		
		
		
		return orgList;
	}



}
