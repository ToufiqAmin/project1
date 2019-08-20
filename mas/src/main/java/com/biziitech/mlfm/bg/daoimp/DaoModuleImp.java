package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoModule;
import com.biziitech.mlfm.bg.model.ModelModule;
import com.biziitech.mlfm.bg.model.ModelSystem;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.repository.ModuleRepository;

@Service
public class DaoModuleImp implements DaoModule {
    
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	private List<ModelModule> moduleList;
	public List<ModelModule> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModelModule> moduleList) {
		this.moduleList = moduleList;
	}
	
	public void saveModule(ModelModule module) {
		if (module.isActive()) {
			module.setActiveStatus(1);
		}
		
		else
		{
			module.setActiveStatus(0);
		}
		
		
		moduleRepository.save(module);
		
	}

	@Override
	public List<ModelModule> getModuleFromSystem(Long id){
		List<ModelModule> moduleListSystem=moduleRepository.findModuleFromSystem(id);
		setModuleList(moduleListSystem);
		return moduleListSystem;
	}
	@Override
	public Optional<ModelModule> getModuleById(Long moduleId) {
    Optional<ModelModule> module=moduleRepository.findById(moduleId);
		
		if(module.get().getActiveStatus()==1)
		 {
			module.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 module.get().setActive(false);
		 }
		return module;
	}
	
}
