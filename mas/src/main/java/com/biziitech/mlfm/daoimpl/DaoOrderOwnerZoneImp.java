package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.biziitech.mlfm.dao.DaoOrderOwnerZone;
import com.biziitech.mlfm.model.ModelOrderOwnerZone;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.OrderOwnerZoneRepository;


@Service
public class DaoOrderOwnerZoneImp implements DaoOrderOwnerZone{
	
	@Autowired
	private OrderOwnerZoneRepository orderOwnerZoneRepository;

	@Override
	public void saveOrderOwnerZone(ModelOrderOwnerZone modelOrderOwnerZone) {
		// TODO Auto-generated method stub
		if(modelOrderOwnerZone.isActive()) 
		{
			
			modelOrderOwnerZone.setActiveStatus(1);
		}
		else 
		{
			
			modelOrderOwnerZone.setActiveStatus(0);
		}
		
		orderOwnerZoneRepository.save(modelOrderOwnerZone);
		
	}

	@Override
	public List<ModelOrderOwnerZone> getOrderOwnerZoneInOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelOrderOwnerZone> getOrderOwnerZoneListByCraiteria(String zoneName, String shortCode, String remarks,
			int status) {
		// TODO Auto-generated method stub
		List<ModelOrderOwnerZone> resultList = orderOwnerZoneRepository.findOrderOwnerZoneDetails(zoneName, shortCode, remarks, status);
		List<ModelOrderOwnerZone> orderOwnerZoneList= new ArrayList<>();
		
		for(ModelOrderOwnerZone modelOrderOwnerZone: resultList) {
			if(modelOrderOwnerZone.getActiveStatus()==1)
				if(modelOrderOwnerZone.getActiveStatus()==1)
				 { modelOrderOwnerZone.setsActive("Active");
				 modelOrderOwnerZone.setActive(true);
				 }
				 
				 else
				 {	 modelOrderOwnerZone.setsActive("InActive");
				 modelOrderOwnerZone.setActive(false);
				     
				 }
				 
			orderOwnerZoneList.add(modelOrderOwnerZone);
		}
		
		
		
		return orderOwnerZoneList;
	}



}
