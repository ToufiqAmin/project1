package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.model.ModelSpec;

public interface DaoSpec {
	public List<ModelSpec> findAll();
	
	public List<ModelSpec> findAllActive();
	
	public List<ModelDesignCustom> specSave(ModelSpec modelspec);
	
	
	
	
}
