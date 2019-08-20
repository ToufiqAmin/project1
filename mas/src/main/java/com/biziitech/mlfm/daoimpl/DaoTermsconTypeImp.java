package com.biziitech.mlfm.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoTermsconType;
import com.biziitech.mlfm.model.ModelTermsConType;
import com.biziitech.mlfm.repository.TermsconTypeRepository;

@Service
public class DaoTermsconTypeImp implements DaoTermsconType {

	@Autowired
	private TermsconTypeRepository termsconTypeRepository; 
	
	@Override
	public void saveTermsconType(ModelTermsConType modelTermsconType) {
		
		if(modelTermsconType.isActive()) {
			
			modelTermsconType.setActiveStatus(1);
		}
		else {
			modelTermsconType.setActiveStatus(0);
		}
		
		termsconTypeRepository.save(modelTermsconType);
		
	}

}
