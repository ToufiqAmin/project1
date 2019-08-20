package com.biziitech.mlfm.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoPIType;
import com.biziitech.mlfm.model.ModelPIType;
import com.biziitech.mlfm.repository.PITypeRepository;

@Service
public class DaoPITypeImp implements DaoPIType {
   
	@Autowired
	private PITypeRepository pITypeRepository;

	@Override
	public void savePIReceiveMst(ModelPIType pIType) {
		
		if(pIType.isActive()) {
			pIType.setActiveStatus(1);
		}
		else {
			pIType.setActiveStatus(0);
		}
		
		pITypeRepository.save(pIType);
		
	}
	
	
}
