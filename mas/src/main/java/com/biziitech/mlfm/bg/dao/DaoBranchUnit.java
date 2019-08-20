package com.biziitech.mlfm.bg.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.bg.model.ModelBranchUnit;
import com.biziitech.mlfm.bg.model.ModelOrg;


public interface DaoBranchUnit {
	
	
	public List<ModelBranchUnit> getBranchUnitList();
	public Optional<ModelBranchUnit> getBranchUnitById(Long id);
	public List<ModelOrg> getOrgByOrgGroup(Long orgGroupId);
	public List<ModelBranch> getBranchByOrg(Long orgId);
	public List<ModelBranchUnit> getBranchListByCraiteria(Long branchId);

}
