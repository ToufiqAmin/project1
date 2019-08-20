package com.biziitech.mlfm.bg.daoimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoObject;
import com.biziitech.mlfm.bg.model.ModelModule;
import com.biziitech.mlfm.bg.model.ModelObject;
import com.biziitech.mlfm.repository.ModuleRepository;
import com.biziitech.mlfm.repository.ObjectRepository;


@Service
public class DaoObjectImp implements DaoObject {

	@Autowired
	private ObjectRepository objectRepository;
	
	private List<ModelObject> objectList;
	public List<ModelObject> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<ModelObject> objectList) {
		this.objectList = objectList;
	}
	
	@Override
	public void saveObject(ModelObject object) {
		if (object.isActive()) {
			object.setActiveStatus(1);
		}
		
		else
		{
			object.setActiveStatus(0);
		}
		
		
		objectRepository.save(object);
		
	}

	@Override
	public List<ModelObject> getObjectFromModule(Long id) {
		List<ModelObject> objectListModule=objectRepository.findModuleFromSystem(id);
		setObjectList(objectListModule);
		return objectListModule;
	}
	@Override
	public List<ModelObject> getAllObjectList() {
		
		return objectRepository.findAll();
	}

}
