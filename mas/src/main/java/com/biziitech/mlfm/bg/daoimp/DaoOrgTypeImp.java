package com.biziitech.mlfm.bg.daoimp;

import java.util.ArrayList;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoOrgType;
import com.biziitech.mlfm.bg.model.ModelOrgType;
import com.biziitech.mlfm.repository.bgOrgTypeRepository;




@Service
public class DaoOrgTypeImp implements DaoOrgType {
	
	@Autowired
	private bgOrgTypeRepository bgOrgTypeRepository;
	

	
	public void saveOrgType(ModelOrgType modelOrgType) {
		
		if(modelOrgType.isActive()) 
		{
			modelOrgType.setActiveStatus(1);
		}else 
		{
			modelOrgType.setActiveStatus(0);
		}

		bgOrgTypeRepository.save(modelOrgType);
		
	}
	
	
	@Override
	public List<ModelOrgType> getOrgTypeListByCraiteria(String orgTypeName, String shortCode, String orgTypeRemarks, int  status) {
		
		List<ModelOrgType> resultList = bgOrgTypeRepository.findOrgTypeDetails(orgTypeName, shortCode, orgTypeRemarks, status);
		List<ModelOrgType> orgTypeList= new ArrayList<>();
		
		for(ModelOrgType bgOrgType: resultList) {
		
				if(bgOrgType.getActiveStatus()==1)
				 { bgOrgType.setsActive("Yes");
				  bgOrgType.setActive(true);
				 }
				 
				 else
				 {	 bgOrgType.setsActive("NO");
				     bgOrgType.setActive(false);
				     
				 }
				 
			orgTypeList.add(bgOrgType);
		}
		
		
		
		return orgTypeList;
	}


	@Override
	public List<ModelOrgType> getOrgTypeName() {
		// TODO Auto-generated method stub
		
		List<ModelOrgType>resultList= bgOrgTypeRepository.findOrgType();
		List<ModelOrgType> groupList= new ArrayList<>();
		for(ModelOrgType list: resultList) {
			if(list.getActiveStatus()==1) {
				list.setActive(true);
			}else {
				list.setActive(false);
			}
			groupList.add(list);
		}
		
		System.out.println("test: ");
		return groupList;
	}

}
