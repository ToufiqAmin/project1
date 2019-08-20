package com.biziitech.mlfm.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoDesignCost;
import com.biziitech.mlfm.model.ModelDesignCost;
import com.biziitech.mlfm.repository.DesignCostRepository;


@Service
public class DaoDesignCostImp implements DaoDesignCost{
   
	@Autowired
	private DesignCostRepository designCostRepository;
	
	@Override
	public void saveDesignCost(ModelDesignCost designCost) {
		
		designCostRepository.save(designCost);
		
	}

	@Override
	public void saveDesignCostConsumItem(ModelDesignCost designCost) {
		
		designCostRepository.save(designCost);
		
	}

}
