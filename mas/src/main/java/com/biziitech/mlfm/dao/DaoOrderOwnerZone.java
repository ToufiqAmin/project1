package com.biziitech.mlfm.dao;


import java.util.List;

import com.biziitech.mlfm.model.ModelOrderOwnerZone;
import com.biziitech.mlfm.model.ModelUOM;

public interface DaoOrderOwnerZone {
	
	public void saveOrderOwnerZone(ModelOrderOwnerZone  modelOrderOwnerZone);
	
	public List<ModelOrderOwnerZone> getOrderOwnerZoneInOrder();
	
	public List<ModelOrderOwnerZone> getOrderOwnerZoneListByCraiteria(String zoneName, String shortCode, String remarks, int  status);

}
