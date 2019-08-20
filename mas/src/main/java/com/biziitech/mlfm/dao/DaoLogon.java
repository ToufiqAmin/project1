package com.biziitech.mlfm.dao;

import java.util.List;

import com.biziitech.mlfm.bg.model.ModelUser;

public interface DaoLogon {
	
	public int validateUserPassword(String userId,String password);
	
	
	//created by sohel rana on 16/04/2019
	
	public List<ModelUser> userValidate(String userName,String password);
	
	public ModelUser getLogonUserName(Long id);
	

}
