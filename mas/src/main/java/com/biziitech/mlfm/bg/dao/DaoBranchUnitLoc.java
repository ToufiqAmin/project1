package com.biziitech.mlfm.bg.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelBranchUnitLoc;

public interface DaoBranchUnitLoc {
	
	
	public void saveBranchUnitLoc(ModelBranchUnitLoc modelBranchUnitLoc);
	public List<ModelBranchUnitLoc> getBranchUnitLocList();
	public List<ModelBranchUnitLoc> getBranchUnitLocListByBranchUnitId(Long id);
	public Optional<ModelBranchUnitLoc> getBranchUnitLocById(Long id);

}
