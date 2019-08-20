package com.biziitech.mlfm.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPOCustom;
import com.biziitech.mlfm.dao.DaoPOConsumItem;
import com.biziitech.mlfm.model.ModelPOConsumItem;
import com.biziitech.mlfm.repository.POConsumItemRepository;


@Service
public class DaoPOConsumItemImp implements DaoPOConsumItem {
	
	
	@Autowired
	private POConsumItemRepository pOConsumItemRepository;

	@Override
	public List<ModelPOConsumItem> getPOConsumItemDetailsByPOMstId(Long pOId) {
		
		
		
		// TODO Auto-generated method stub
		return pOConsumItemRepository.findPOConsumItemDetailsByPOMstId(pOId);
	}

}
