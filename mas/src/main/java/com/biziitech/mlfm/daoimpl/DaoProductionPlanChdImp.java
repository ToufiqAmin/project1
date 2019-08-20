package com.biziitech.mlfm.daoimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.repository.ProductionPlanChdRepository;

@Service
public class DaoProductionPlanChdImp implements DaoProductionPlanChd {
	
	@Autowired
	private ProductionPlanChdRepository productionPlanChdRepository;

	@Override
	public void saveProductionPlanChd(ModelProductionPlanChd modelProductionPlanChd) {
		
		productionPlanChdRepository.save(modelProductionPlanChd);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ModelProductionPlanChd> findProductionPlanChdListByMstId(Long productionPlanMstId) {
		// TODO Auto-generated method stub
		return productionPlanChdRepository.findProductionPlanChdListByMstId(productionPlanMstId);
	}

	

}
