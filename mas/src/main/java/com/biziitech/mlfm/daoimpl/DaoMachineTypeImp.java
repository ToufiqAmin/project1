package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoMachineType;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.MachineTypeRepository;

@Service
public class DaoMachineTypeImp implements DaoMachineType{
	
	@Autowired
	private MachineTypeRepository machineTypeRepository;
	
	public void saveMachine(ModelMachineType modelmachine) {
		if (modelmachine.isActive()) {
			modelmachine.setActiveStatus(1);
		}
		
		else
		{
			modelmachine.setActiveStatus(0);
		}
		
		
		machineTypeRepository.save(modelmachine);
		
	}
	
	@Override
	public List<ModelMachineType> getMachineTypeList(){
		
		List<ModelMachineType> resultList = machineTypeRepository.findMachienType();
		List<ModelMachineType> machineTypeList= new ArrayList<>();
		
		for(ModelMachineType machineType: resultList) {
			if(machineType.getActiveStatus()==1)
				if(machineType.getActiveStatus()==1)
				 { machineType.setsActive("Yes");
				 machineType.setActive(true);
				 }
				 
				 else
				 {	 machineType.setsActive("NO");
				     machineType.setActive(false);
				     
				 }
				 
			machineTypeList.add(machineType);
		}
		
		return machineTypeList;
		
	}

	@Override
	public List<ModelMachineType> getMachineTypeListByCraiteria(String machineTypeName, String shortCode,
			String remarks, int status) {
		List<ModelMachineType> resultList = machineTypeRepository.findMachineTypeDetails(machineTypeName, shortCode, remarks, status);
		List<ModelMachineType> machineTypeList= new ArrayList<>();
		for(ModelMachineType machineType: resultList) {
			if(machineType.getActiveStatus()==1)
				if(machineType.getActiveStatus()==1)
				 { machineType.setsActive("Yes");
				 machineType.setActive(true);
				 }
				 
				 else
				 {	 machineType.setsActive("NO");
				     machineType.setActive(false);
				     
				 }
				 
			machineTypeList.add(machineType);
		}
		
		return machineTypeList;
	}

	@Override
	public List<ModelMachineType> getMachineName() {
		
		//return machineTypeRepository.findAll();
		return machineTypeRepository.findMachienType();
	}
	

}
