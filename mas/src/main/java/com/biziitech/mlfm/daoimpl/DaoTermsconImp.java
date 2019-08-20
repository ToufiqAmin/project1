package com.biziitech.mlfm.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoTermscon;
import com.biziitech.mlfm.model.ModelTermsCon;
import com.biziitech.mlfm.repository.TermsConRepository;

@Service
public class DaoTermsconImp implements DaoTermscon {

	@Autowired
	private TermsConRepository termsconRepository; 
	
	
	@Override
	public void saveTermscon(ModelTermsCon modelTermscon) {
		
		if(modelTermscon.isActive()) {
			modelTermscon.setActiveStatus(1);
		}
		else {
			modelTermscon.setActiveStatus(0);
		}
		
		termsconRepository.save(modelTermscon);
		
		
	}

}
