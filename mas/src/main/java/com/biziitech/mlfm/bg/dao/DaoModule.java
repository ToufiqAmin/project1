package com.biziitech.mlfm.bg.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelModule;


public interface DaoModule {

	public void saveModule(ModelModule module);
	public List<ModelModule> getModuleFromSystem(Long id);
	public Optional<ModelModule> getModuleById(Long moduleId);
}
