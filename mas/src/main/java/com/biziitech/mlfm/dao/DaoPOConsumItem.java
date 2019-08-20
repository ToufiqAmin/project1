package com.biziitech.mlfm.dao;


import java.util.List;


import com.biziitech.mlfm.model.ModelPOConsumItem;

public interface DaoPOConsumItem {
	
	
	public List<ModelPOConsumItem>getPOConsumItemDetailsByPOMstId(Long pOId);

}
