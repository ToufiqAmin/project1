package com.biziitech.mlfm.bg.daoimp;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoObjectType;
import com.biziitech.mlfm.bg.model.ModelObjectType;
import com.biziitech.mlfm.repository.ObjectTypeRepository;

@Service
public class DaoObjectTypeImp implements DaoObjectType {
	
	@Autowired
	private ObjectTypeRepository objectTypeRepository;

	@Override
	public void saveObjectType(ModelObjectType objectType) {
		if (objectType.isActive()) {
			objectType.setActiveStatus(1);
		}
		
		else
		{
			objectType.setActiveStatus(0);
		}
		
		
		objectTypeRepository.save(objectType);
		
	}

	@Override
	public List<ModelObjectType> getObjectTypeListByCraiteria(String typeName, String shortCode, String remarks,
			int status) {
		List<ModelObjectType> resultList = objectTypeRepository.findObjectTypeDetails(typeName, shortCode, remarks, status);
		List<ModelObjectType> objectTypeList= new ArrayList<>();
		
		for(ModelObjectType objectType: resultList) {
			if(objectType.getActiveStatus()==1)
				if(objectType.getActiveStatus()==1)
				 { objectType.setsActive("Yes");
				   objectType.setActive(true);
				 }
				 
				 else
				 {	 objectType.setsActive("NO");
				     objectType.setActive(false);
				     
				 }
				 
			objectTypeList.add(objectType);
		}
		
				
		return objectTypeList;

}

	@Override
	public List<ModelObjectType> getObjectTypeList() {
		
		return objectTypeRepository.findAll();
	}
}
