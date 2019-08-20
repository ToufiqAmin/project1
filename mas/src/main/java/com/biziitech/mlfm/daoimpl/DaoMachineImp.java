package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoMachine;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.repository.MachineRepository;

@Service
public class DaoMachineImp implements DaoMachine {
	
	@Autowired
	private MachineRepository machineRepository;

	public void saveMachineData(ModelMachine modelmachine) {
		if (modelmachine.isActive()) {
			modelmachine.setActiveStatus(1);
		}
		
		else
		{
			modelmachine.setActiveStatus(0);
		}
		
		
		machineRepository.save(modelmachine);
		
	}



	@Override
	public List<ModelMachine> getMachineListByCraiteria(String machineName, String capacityPerShiftPerDay,
			String capacityUom, String remarks, int status) {
		
		List<ModelMachine> resultList = machineRepository.findMachineDetails(machineName, capacityPerShiftPerDay, capacityUom, remarks, status);
		List<ModelMachine> machineList= new ArrayList<>();
		for(ModelMachine machine: resultList) {
			if(machine.getActiveStatus()==1)
				if(machine.getActiveStatus()==1)
				 { machine.setsActive("Yes");
				    machine.setActive(true);
				 }
				 
				 else
				 {	 machine.setsActive("NO");
				     machine.setActive(false);
				     
				 }
				 
			machineList.add(machine);
		}
		
		
		
		return machineList;
	}

	
	
	@Override
	public List<ModelMachine> getMachineByType(Long machineTypeId) {
		
    return machineRepository.findMachinebyType(machineTypeId);
	}



	@Override
	public List<ModelMachine> getMachineList() {
		List<ModelMachine> resultList = machineRepository.findMachine();
		List<ModelMachine> machineList= new ArrayList<>();
		
		for(ModelMachine machine: resultList) {
			if(machine.getActiveStatus()==1)
				if(machine.getActiveStatus()==1)
				 { machine.setsActive("Yes");
				    machine.setActive(true);
				 }
				 
				 else
				 {	 machine.setsActive("NO");
				     machine.setActive(false);
				     
				 }
				 
			machineList.add(machine);
		}
		
		
		
		return machineList;
	}
}
