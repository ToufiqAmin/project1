package com.biziitech.mlfm.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoItem;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.repository.ItemRepository;

@Service
public class DaoItemImp implements DaoItem {
	
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	
	@Override
	public List<ModelItem> findItems() {
		
		List<ModelItem> resultList = itemRepository.findAll();
		List<ModelItem> itemList= new ArrayList<>();
		for(ModelItem type: resultList) {
			if(type.getActiveStatus()==1)
				{
				type.setActive(true);
				type.setsActive("Yes");
				}
			else
			{
				type.setActive(true);
				type.setsActive("No");
			}
			itemList.add(type);
		}
		
		
		
		return itemList;
	}
	
	
	@Override
	public void saveItem(ModelItem item) {
		 if(item.isActive())
			 item.setActiveStatus(1);
		 else
			 item.setActiveStatus(0);
		 //System.out.println(owner.getCountryName());
		
		itemRepository.save(item);
		
	}

	
	@Override
	public List<ModelItem> searchItem(String name,String remarks,boolean active) {
		
		int activeStatus=active?1:0;
		
		List<ModelItem> resultList= itemRepository.searchItems(name,remarks,activeStatus);
		
	     List<ModelItem> itemList= new ArrayList<>();
		
		for (ModelItem item: resultList) {
			
			if (item.getActiveStatus()==1) {
				item.setsActive("Yes");
				item.setActive(true);
			}
			else {
				item.setsActive("No");
				item.setActive(false);
			}
			
			itemList.add(item);
		}
		
		return itemList;
		
		
	}

	
	@Override
	public List<ModelItem> findItemByType(Long id) {
		
		
		 
		return itemRepository.findItemByTypeId(id);
		
	}


	@Override
	public List<ModelItem> getAllItemName() {
		// TODO Auto-generated method stub
		
		return itemRepository.findAll();
	}


	@Override
	public List<ModelItem> getItemListActive() {
		
		
		// TODO Auto-generated method stub
		return itemRepository.findItemListActive();
	}


	//
	
	public List<ModelItem> getItemTypeByItemId(Long itemId) {
		
		return itemRepository.findItemById(itemId);
	}
	
}