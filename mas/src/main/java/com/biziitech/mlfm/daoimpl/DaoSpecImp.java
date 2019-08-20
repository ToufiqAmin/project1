package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.dao.DaoSpec;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.repository.SpecRepository;

@Service
public class DaoSpecImp implements DaoSpec {
	
	@Autowired
	private SpecRepository spec;
@Override
public List<ModelSpec> findAll(){
	List<ModelSpec> specList=new ArrayList<>();
	specList=spec.findAll();
	return specList;
	
}

@Override
public List<ModelSpec> findAllActive(){
	List<ModelSpec> specList=new ArrayList<>();
	specList=spec.findAllActive();
	return specList;
	
}


@Override
public List<ModelDesignCustom> specSave(ModelSpec modelspec) {
	
	spec.save(modelspec);
	// TODO Auto-generated method stub
	return null;
	

	
	
}
}
