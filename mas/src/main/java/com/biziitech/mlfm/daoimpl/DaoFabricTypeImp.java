package com.biziitech.mlfm.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoFabricType;
import com.biziitech.mlfm.model.ModelFabricType;
import com.biziitech.mlfm.repository.FabricTypeRepository;



@Service
public class DaoFabricTypeImp implements DaoFabricType {
	
	
	@Autowired
	private FabricTypeRepository fabricTypeRepository;

	@Override
	public List<ModelFabricType> getFabricTypeList() {
		
		
		List<ModelFabricType> fabricTypeList = fabricTypeRepository.findFabricType();
		
		// TODO Auto-generated method stub
		return fabricTypeList;
	}

}
