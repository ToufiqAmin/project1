package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignSpec;

public interface DaoDesignSpec {
	public void saveSpecification(String specValue,String remarks, Long designId);
	
	public List<ModelDesignSpec> getDesignSpecDataByDesignId(Long designId);
	public void saveSpec(ModelDesignSpec modelDesignSpec);
	
	public List<ModelDesignCustom> getDesignSpecListBySpecId(Long specId);
	public Optional<ModelDesignSpec> findDesignSpecById(Long specId);
	public List<ModelDesignCustom> getAllDesignSpecListByDesignId(Long designId);
	
	
	///reated by sohel rana on 07/04/2019
	
	public List<ModelDesignSpec> getSpecName(Long designId);
	
	
	
	public List<ModelDesignSpec> checkDesignSpecForDesignId(Long specId,Long designId);
	
	
}
