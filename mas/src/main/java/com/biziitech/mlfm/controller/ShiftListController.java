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

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoShift;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelUserObject;

@Controller
public class ShiftListController {
	
	@Autowired 
	private DaoShift daoShift;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;

	
	@RequestMapping(path = "/shift/list/{userId}", method = RequestMethod.GET)
    public String getShiftList(@PathVariable Long userId,Model model) {
		
		
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
	 
	// ModelUOM newUOM=new ModelUOM();
	 //model.addAttribute("uomList", newUOM);
	   
        return "shift_list";
   }
	
	
	@RequestMapping(path="/shift/search")
	public String getShiftList(Model model,@RequestParam("Shift_Name")String Shift_Name,@RequestParam("shortCode")String shortCode
			,@RequestParam("remarks")String remarks, @RequestParam(value="active", required = false) String active) {
		
		
		  System.out.println("system user :" + systemUserId);
Long userId=systemUserId;

ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
String userName=logonUser.getUserName();
System.out.println("logon user name is :" + userName);
String name=userName;
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
		
		if(active!=null)
			status=1;
		else
			status=0;
		
		model.addAttribute("shiftList",daoShift.getShiftListByCraiteria(Shift_Name, shortCode, remarks, status));	
		return "shift_list";
	}
	
	
	

}
