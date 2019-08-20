package com.biziitech.mlfm.bg.dao;


import java.util.List;
import java.util.Optional;

import com.biziitech.mlfm.bg.model.ModelSystem;

public interface DaoSystem {
	
	public void saveSystem(ModelSystem system);
	public List<ModelSystem> getSystemListByCraiteria(String systemName, String remarks, int  status);
	public List<ModelSystem> getSystemList();
	public Optional<ModelSystem> getSystemById(Long systemId);
}
