package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoMachineShift;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelShift;
import com.biziitech.mlfm.repository.MachineShiftRepository;
import com.biziitech.mlfm.repository.ShiftRepository;



@Service
public class DaoMachineShiftImp implements DaoMachineShift  {
	
	@Autowired
	private MachineShiftRepository machineShiftRepository;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Override
	public List<ModelShift> getShiftList() {
		
		//List<ModelShift> resultList = machineShiftRepository.getAllShift();
		List<ModelShift> resultList = shiftRepository.findShift(); // findShift select all active shifts.
		List<ModelShift> shiftList= new ArrayList<>();
		for(ModelShift type: resultList) {
			if(type.getActiveStatus()==1)
				{
				type.setActive(true);
				type.setsActive("Yes");
				}
			else
			{
				type.setActive(true);

				type.setsActive("No");
			}
			shiftList.add(type);
		}
		
		
		
		return shiftList;
	}

	@Override
	public List<ModelMachineShift> getMachineShiftByName(Long machineId) {
		
		return machineShiftRepository.findMachineShiftbyName(machineId);
	}
	
	@Override
	public List<ModelMachineShift> getMachineShiftList() {
		
		return machineShiftRepository.findMachineShiftList();
	}

	@Override
	public List<ModelMachine> getMachineList(){
		
		/* machine list
		// temporarily stopped
		
		//List<ModelMachine> resultList = machineShiftRepository.getAllMachine();
		
		
		temporarily added
		
		
		*/
		List<ModelMachine> resultList =new ArrayList<>();
		List<ModelMachine> machineList= new ArrayList<>();
		
		ModelMachine m1= new ModelMachine();
		m1.setMachineId(20181200568L);
		m1.setMachineName("machine test1");
		m1.setActiveStatus(1);
	
		resultList.add(m1);
		
		ModelMachine m2= new ModelMachine();
		m2.setMachineId(20181200569L);
		m2.setMachineName("machine test2");
		m2.setActiveStatus(1);
	
		resultList.add(m2);
		
		
		
		
		for(ModelMachine type: resultList) {
			if(type.getActiveStatus()==1)
				{
				type.setActive(true);
				type.setsActive("Yes");
				}
			else
			{
				type.setActive(true);


				type.setsActive("No");
			}
			machineList.add(type);
		}
		
		
		
		
		
		return machineList;
	}
	
	@Override
	public void saveMachineShift(ModelMachineShift machineShift) {
		
		
			if(machineShift.isActive())
				machineShift.setActiveStatus(1);
			else
				machineShift.setActiveStatus(0);
			
			machineShiftRepository.save(machineShift);
			
		
		
	}
	
	/*
	@Override
	public List<ModelMachineShift> getMachineShiftByName(Long machineId) {
		
		return machineShiftRepository.findMachineShiftbyName(machineId);
	}
	*/
	
	//saj - machine_shift_list search query - Start
	@Override
	public List<ModelMachineShift> getMachineShiftListByCraiteria(String shiftName, String remarks, int  status){
		List<ModelMachineShift> resultList = machineShiftRepository.findMachineShiftDetails(shiftName, remarks, status);
		List<ModelMachineShift> MachineShiftList= new ArrayList<>();
		
		for(ModelMachineShift machineShift: resultList) {
			if(machineShift.getActiveStatus()==1)
				if(machineShift.getActiveStatus()==1)
				 { machineShift.setsActive("Yes");
				 machineShift.setActive(true);
				 }
				 
				 else
				 {	 machineShift.setsActive("NO");
				 machineShift.setActive(false);
				     
				 }
				 
			MachineShiftList.add(machineShift);
		}
		// TODO Auto-generated method stub
		return MachineShiftList;
	}
	//saj - machine_shift_list search query - End


}
