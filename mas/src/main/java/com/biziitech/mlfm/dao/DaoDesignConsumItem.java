package com.biziitech.mlfm.dao;

import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.model.ModelDesignConsumItem;


public interface DaoDesignConsumItem {
	public void saveConsume(Long designId,Long itemQty,String remarks);
	
	
	
	public List<ModelDesignConsumItem> getDesignConsumItemList(Long designId);
	
	public void saveDesignConsumeItem(ModelDesignConsumItem modelDesignConsumItem);
	public List<ModelDesignCustom> getDesignConsumItemListByDesignConItemId(Long designConsumeItemId);
	public Optional<ModelDesignConsumItem> findDesignConsumeItemByConsumeId(Long designConsumeItemId);
	public List<ModelDesignCustom> getAllDesignConsumItemListByDesignId(Long designId);
		
     //created by sohel rana on 07/04/2019
	
	public List<ModelDesignConsumItem> getConsumItemNae(Long designId);
	
	
}
