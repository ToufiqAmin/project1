package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.dao.DaoOrderOwner;
import com.biziitech.mlfm.model.ModelBusinessType;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.repository.OrderOwnerRepository;

@Service
public class DaoOrderOwnerImp implements DaoOrderOwner{
	
	@Autowired
	private OrderOwnerRepository ownerRepository;

	
	
	//modfied by sohel rana on 17/03/2019
	
	@Override
	public List<ModelOrderOwner> getAllOwner(Long countryId, String name,String shortCode, int  status,String contactPerson) {
		List<ModelOrderOwner> resultList= ownerRepository.findOwnerDetails(countryId, name, shortCode, status, contactPerson);
		List<ModelOrderOwner>  ownerList= new ArrayList<>();
		for(ModelOrderOwner owner:resultList) {
			if(owner.getActiveStatus()==1)
			 { owner.setsActive("Yes");
			  owner.setActive(true);
			 }
			 
			 else
			 {	 owner.setsActive("NO");
			     owner.setActive(false);
			 }
			 
			
			 ownerList.add(owner);
		 }
		
		return ownerList;
	}

	@Override
	public List<ModelOrderOwner> getAllOwnerNotActive(String type, String name, String shortCode, String country,
			String number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOwner(ModelOrderOwner owner) {
		 if(owner.isActive())
			 owner.setActiveStatus(1);
		 else
			 owner.setActiveStatus(0);
		 //System.out.println(owner.getCountryName());
		 ModelCountry country= new ModelCountry();
		// ModelBusinessType type= new ModelBusinessType();
		 ModelOrderOwnerType ownerType= new ModelOrderOwnerType();
		// type.setTypeId(owner.getModelBusinessType().getTypeId());
		 country.setCountryId(owner.getOwnerCountry().getCountryId());
		 ownerType.setOrderOwnerTypeId(owner.getOrderOwnerType().getOrderOwnerTypeId());
		 owner.setOwnerCountry(country);;//setCountryId(Long.valueOf(owner.getCountryName()));
		// owner.setModelBusinessType(type);
		 owner.setOrderOwnerType(ownerType);
		ownerRepository.save(owner);
		
	}

	@Override
	public void updateOwner(ModelOrderOwner owner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getOwnerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ModelOrderOwner getOwner(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModelOrderOwner> getAllOwnerName() {
		// TODO Auto-generated method stub
		return ownerRepository.findAll();//ownerRepository.findOwnerNames();	
	}

	@Override
	public List<ModelOrderOwner> getOwnerByType(Long ownerTypeId) {
		// TODO Auto-generated method stub
		return ownerRepository.findOwnerbyType(ownerTypeId);
	}
	
	

	@Override
	public List<ModelOrderOwner> getAllOwner() {
		// TODO Auto-generated method stub
		return ownerRepository.findAllOwnerInOrder();
	}

	@Override
	public List<ModelOrderOwner> getOwnerByTypeInOrder(Long ownerTypeId) {
		// TODO Auto-generated method stub
		return ownerRepository.findOwnerbyTypeInOrder(ownerTypeId);
	}
	
	@Override
	public List<ModelOrderOwner> getUltimateBuyerList() {
		// TODO Auto-generated method stub
		return ownerRepository.findUltimateBuyerActive();
	}
	

	@Override
	public List<ModelOrderOwner> getActiveOrderOwnerList() {
		// TODO Auto-generated method stub
		return ownerRepository.findOwnerNames();
	}

	@Override
	public List<ModelOrderOwner> getActiveOrderOwnerListByOwnerTypeId(Long ownerTypeId) {
		
		
		
		// TODO Auto-generated method stub
		return ownerRepository.findActiveOrderOwnerListByOwnerTypeId(ownerTypeId);
	}

}
