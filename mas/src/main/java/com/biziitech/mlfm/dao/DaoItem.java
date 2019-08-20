package com.biziitech.mlfm.dao;

import java.util.List;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelItemType;


public interface DaoItem {

	public List<ModelItem> findItems();
	
	public void saveItem(ModelItem  item);
	public List<ModelItem> findItemByType(Long id);
	//public void saveItem(ModelItem item);
	//public Optional<ModelItem> findItemById(Long id);
	public List<ModelItem> getAllItemName();
	public List<ModelItem> searchItem(String name,String remarks,boolean active);
	public List<ModelItem> getItemListActive();
	
	public List<ModelItem> getItemTypeByItemId(Long itemId);
	
	
}
