package com.biziitech.mlfm.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoLogon;
import com.biziitech.mlfm.repository.LogonRepository;


@Service
public class DaoLogonImp implements DaoLogon {
	
	
	@Autowired
	private LogonRepository logonRepository;

	@Override
	public int validateUserPassword(String userName, String password) {
		// TODO Auto-generated method stub
		
		
		int returnValue=0;
		
		
		try {
		ModelUser modelUser =new ModelUser();
		modelUser= logonRepository.getValidUserPassword(userName, password);
		
		System.out.println("modelUser  :"+modelUser);
		System.out.println("parameter username :"+userName);
		System.out.println("parameter password :"+password);
		
		System.out.println("name :"+modelUser.getUserName());
		System.out.println("password :"+modelUser.getSecurityCode());
		
		//boolean s=userName.equals(modelUser.getUserName());
		//boolean s1=password.equals(modelUser.getUserName());
		
		//if(modelUser.getUserId()==userId && password.compareTo(modelUser.getSecurityCode()))
		if(userName.equals(modelUser.getUserName()) &&  password.equals(modelUser.getSecurityCode()))
		{
			
			System.out.println("Id and Password is correct:");
			
			System.out.println("Id :"+modelUser.getUserId());
			System.out.println("name :"+modelUser.getUserName());
			System.out.println("password :"+modelUser.getSecurityCode());
			
			
			returnValue= 1;
			
			return returnValue;
		}
		
		if(userName.equals(modelUser.getUserName()))
		{
			System.out.println("Id is Incorrect");
			
			returnValue= 2;
			
			return returnValue;
		}
		
		//if(modelUser.getSecurityCode()!=password)
		if(password.equals(modelUser.getUserName()))
		{
			
			System.out.println("Password is Incorrect");
			returnValue= 3;
			
			return returnValue;
		}
		
		
		return returnValue;
		}
		catch(Exception e)
		{
			
			System.out.println(e.getMessage());
			//return null;
			return returnValue;
		}
		//return returnValue;
	
	
	}

	

	

	//created by sohel rana on 16/04/2019
	
	@Override
	public List<ModelUser> userValidate(String userName, String password) {
		
		return logonRepository.getUserValidatiion(userName, password);
	}



	@Override
	public ModelUser getLogonUserName(Long id) {
		// TODO Auto-generated method stub
		return logonRepository.getLogonUserName(id);
	}
	

}
