package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoObject;
import com.biziitech.mlfm.bg.dao.DaoObjectProcess;
import com.biziitech.mlfm.bg.model.ModelObjectProcess;
import com.biziitech.mlfm.repository.ObjectProcessRepository;

@Service
public class DaoObjectProcessImp implements DaoObjectProcess {
	
	@Autowired
	private ObjectProcessRepository objectProcessRepository;
	
	
	private List<ModelObjectProcess> objectProcessList;
	public List<ModelObjectProcess> getObjectProcessList() {
		return objectProcessList;
	}
	public void setObjectProcessList(List<ModelObjectProcess> objectProcessList) {
		this.objectProcessList = objectProcessList;
	}
	
	public void saveObjectProcess(ModelObjectProcess objectProcess) {
		if (objectProcess.isActive()) {
			objectProcess.setActiveStatus(1);
		}
		
		else
		{
			objectProcess.setActiveStatus(0);
		}
		
		
		objectProcessRepository.save(objectProcess);
		
	}

	@Override
	public List<ModelObjectProcess> getObjectProcessFromObject(Long id) {
		List<ModelObjectProcess> objectProcessList=objectProcessRepository.findObjectProcessFromObject(id);
		setObjectProcessList(objectProcessList);
		return objectProcessList;
	}

}
