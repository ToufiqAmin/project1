package com.biziitech.mlfm.bg.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelOrg;



public interface DaoOrg {

	
	public void saveOrg(ModelOrg modelOrg);
	public List<ModelOrg> getOrgList();
//	public List<ModelOrg> getOrgName() ;
	public List<ModelOrg> getOrgName();
	public List<ModelOrg> getOrgByOrgGroupId(Long id);
	public Optional<ModelOrg> getOrgById(Long id);
	
	
}
