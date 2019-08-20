package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.dao.DaoUser;
import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.daoimp.DaoPhoneImp;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelUserObject;




@Controller
public class BgUserListController {
	
	@Autowired
	private DaoCountryImp daoCountryImp;
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoPhoneImp daoPhoneImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@RequestMapping(path="/bgusercontroller/list/{userId}")
	public String List(@PathVariable Long userId,Model model) {
		
		
		System.out.println("user ID :" +userId);
		 ModelUser modelUser=new ModelUser();
		 modelUser.setUserId(userId);
		 this.systemUserId=userId;
		 ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
		 String userName=logonUser.getUserName();
		 System.out.println("logon user name is :" + userName);
		 String name=userName;
		 model.addAttribute("name",name );	  
			
		 // left panel list
		        //  for setup
				List<ModelUserObject> listModelUserObjectSetup= new ArrayList<ModelUserObject>();
				listModelUserObjectSetup=daoUserObject.getUserObjectByObjectGroup(userId,"S");
				System.out.println("listModelUserObjectSetup : " + listModelUserObjectSetup);
				System.out.println("size " + listModelUserObjectSetup.size() );
				model.addAttribute("listModelUserObjectSetup", listModelUserObjectSetup);
				
				//for Transactions
				List<ModelUserObject> listModelUserObjectTransaction= new ArrayList<ModelUserObject>();
				listModelUserObjectTransaction=daoUserObject.getUserObjectByObjectGroup(userId,"T");
				System.out.println("listModelUserObjectTransaction : " + listModelUserObjectTransaction);
				System.out.println("size " + listModelUserObjectTransaction.size() );
				model.addAttribute("listModelUserObjectTransaction", listModelUserObjectTransaction);
				
		        //for Tools
				List<ModelUserObject> listModelUserObjectTool= new ArrayList<ModelUserObject>();
				listModelUserObjectTool=daoUserObject.getUserObjectByObjectGroup(userId,"U");
				System.out.println("listModelUserObjectTool : " + listModelUserObjectTool);
				System.out.println("listModelUserObjectTool size " + listModelUserObjectTool.size() );
				model.addAttribute("listModelUserObjectTool", listModelUserObjectTool);
				
			    //for reports
				List<ModelUserObject> listModelUserObjectReport= new ArrayList<ModelUserObject>();
				listModelUserObjectReport=daoUserObject.getUserObjectByObjectGroup(userId,"R");
				System.out.println("listModelUserObjectReport : " + listModelUserObjectReport);
				System.out.println("listModelUserObjectReport size " + listModelUserObjectReport.size());
				model.addAttribute("listModelUserObjectReport", listModelUserObjectReport);
			    
				//left panel list
		
		List <ModelCountry> countryList= daoCountryImp.getCountryName()	;        
        model.addAttribute("countryList",countryList);
		
		return "userList";
	}
	
	@RequestMapping(path="/bgusercontroller/getalluser")
	 public String getAllUser(Model model,
			 @RequestParam("userName")String userName,
			 @RequestParam("titleName")String titleName,
			 //@RequestParam("phoneNumber")String phoneNumber,
			 @RequestParam("passportNo")String passportNo,
			 @RequestParam(value = "active", required = false) String active
			 ) {
		
		

		System.out.println("system user :" + systemUserId);
	    Long userId=systemUserId;
	    
	     ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
		 String userName1=logonUser.getUserName();
		 System.out.println("logon user name is :" + userName1);
		 String name=userName1;
		 model.addAttribute("name",name );
		 model.addAttribute("userId",userId);
	    // left panel list
	   
		        //  for setup
			    List<ModelUserObject> listModelUserObjectSetup= new ArrayList<ModelUserObject>();
				listModelUserObjectSetup=daoUserObject.getUserObjectByObjectGroup(userId,"S");
				System.out.println("listModelUserObjectSetup : " + listModelUserObjectSetup);
				System.out.println("size " + listModelUserObjectSetup.size() );
				model.addAttribute("listModelUserObjectSetup", listModelUserObjectSetup);
				
				//for Transactions
				List<ModelUserObject> listModelUserObjectTransaction= new ArrayList<ModelUserObject>();
				listModelUserObjectTransaction=daoUserObject.getUserObjectByObjectGroup(userId,"T");
				System.out.println("listModelUserObjectTransaction : " + listModelUserObjectTransaction);
				System.out.println("size " + listModelUserObjectTransaction.size() );
				model.addAttribute("listModelUserObjectTransaction", listModelUserObjectTransaction);
				
		        //for Tools
				List<ModelUserObject> listModelUserObjectTool= new ArrayList<ModelUserObject>();
				listModelUserObjectTool=daoUserObject.getUserObjectByObjectGroup(userId,"U");
				System.out.println("listModelUserObjectTool : " + listModelUserObjectTool);
				System.out.println("listModelUserObjectTool size " + listModelUserObjectTool.size() );
				model.addAttribute("listModelUserObjectTool", listModelUserObjectTool);
				
				//for reports
				List<ModelUserObject> listModelUserObjectReport= new ArrayList<ModelUserObject>();
				listModelUserObjectReport=daoUserObject.getUserObjectByObjectGroup(userId,"R");
				System.out.println("listModelUserObjectReport : " + listModelUserObjectReport);
				System.out.println("listModelUserObjectReport size " + listModelUserObjectReport.size());
				model.addAttribute("listModelUserObjectReport", listModelUserObjectReport);
			//left panel list
		
		int status=1;
		// the code needs to be modified. cak-Nov 4, 2018
		if(active!=null)
			status=1;
		else
			status=0;
		System.out.println("userName:"+userName+"\n titleName: "+titleName+"\n passportNo: "+passportNo+"\n status: "+status);
	
	        List <ModelCountry> countryList= daoCountryImp.getCountryName();  
	        model.addAttribute("countryList",countryList);

		
	        List<ModelUser> userList=daoUser.getUserListByCraiteria(userName, titleName, passportNo, status);
	        System.out.println("Size: "+userList.size());
		 model.addAttribute("userList",userList);
		 
	        return "userList";
	}
	
	
	@ResponseBody
	@RequestMapping(path="/bgusercontroller/findownerphone/",method = RequestMethod.GET)
	public List<ModelPhone> findOwnerPhone(Model  model,
			@RequestParam("id") Long id) {
		System.out.println("in findownerphone method, id: "+id);
		
		List<ModelPhone> modelPhone= daoPhoneImp.getUserPhoneDataByUserId(id);
		model.addAttribute("phoneList",modelPhone);
		System.out.println("Size: "+modelPhone.size());
		return modelPhone;
		
	}

}
