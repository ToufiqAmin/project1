package com.biziitech.mlfm.bg.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelBranch;
import com.biziitech.mlfm.bg.model.ModelBranchUnit;


public interface DaoBranch {
	
	public void saveBranch(ModelBranch modelBranch);
	public List<ModelBranch> getBranchName();
	public List<ModelBranch> getBranchList();
	public List<ModelBranch> getBranchByOrgId(Long id);
	public Optional<ModelBranch> getBranchById(Long id);

}
