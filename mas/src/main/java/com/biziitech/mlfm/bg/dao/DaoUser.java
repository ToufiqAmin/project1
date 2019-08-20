package com.biziitech.mlfm.bg.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelUser;

public interface DaoUser {
	
	public void save(ModelUser modelUser);
	
	public List<ModelUser> getAllUSerName();
	public List<ModelUser> getAllUSerNameInOrder();
	
	public List<ModelUser> getUserListByCraiteria(String userName, String titleName, String passportNo, int  status);
	
	public List<ModelUser> getUserIdByName(String pUserName);
	//public Long getUserIdByName(String pUserName);
	
	public List<ModelUser>getUser(Long userId);
	
	public List<ModelUser>checkFlag(Long userId);
	
}
