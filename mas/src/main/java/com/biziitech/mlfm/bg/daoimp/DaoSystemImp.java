package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoPhone;
import com.biziitech.mlfm.bg.dao.DaoSystem;
import com.biziitech.mlfm.bg.model.ModelSystem;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.SystemRepository;


@Service
public class DaoSystemImp implements DaoSystem {
	
	@Autowired
	private SystemRepository systemRepository;
	
	public void saveSystem(ModelSystem system) {
		if (system.isActive()) {
			system.setActiveStatus(1);
		}
		
		else
		{
			system.setActiveStatus(0);
		}
		
		
		systemRepository.save(system);
		
	}

	@Override
	public List<ModelSystem> getSystemListByCraiteria(String systemName, String remarks, int status) {
		
		List<ModelSystem> resultList = systemRepository.findSystemDetails(systemName, remarks, status);
		List<ModelSystem> systemList= new ArrayList<>();
		
		for(ModelSystem system: resultList) {
			if(system.getActiveStatus()==1)
				if(system.getActiveStatus()==1)
				 { system.setsActive("Yes");
				   system.setActive(true);
				 }
				 
				 else
				 {	 system.setsActive("NO");
				     system.setActive(false);
				     
				 }
				 
			systemList.add(system);
		}	
		
		return systemList;
	}

	@Override
	public List<ModelSystem> getSystemList() {
		List<ModelSystem> resultList = systemRepository.findAll();
		List<ModelSystem> systemList= new ArrayList<>();
		
		for(ModelSystem system: resultList) {
			if(system.getActiveStatus()==1)
				if(system.getActiveStatus()==1)
				 { system.setsActive("Yes");
				   system.setActive(true);
				 }
				 
				 else
				 {	 system.setsActive("NO");
				     system.setActive(false);
				     
				 }
				 
			    systemList.add(system);
		}
		
		
		
		return systemList;
	}

	@Override
	public Optional<ModelSystem> getSystemById(Long systemId) {
		  
	Optional<ModelSystem> system=systemRepository.findById(systemId);
		
		if(system.get().getActiveStatus()==1)
		 {
			 system.get().setActive(true);
			 
		 }
		 
		 else
		 {	 
			 system.get().setActive(false);
		 }
		return system;
	}
}
