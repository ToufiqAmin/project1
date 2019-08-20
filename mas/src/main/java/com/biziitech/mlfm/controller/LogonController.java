package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biziitech.mlfm.bg.dao.DaoUser;
import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoLogon;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelUserObject;



@Controller
@Scope("session")
public class LogonController {
	
	
	@Autowired
	private DaoLogon daoLogon;
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoUserImp user1;
	
	@Autowired
	private DaoLogonImp daoLogonImp;

	@RequestMapping(path="/logoncontroller/init")
	public String init(Model model) {
		ModelUser modelUser = new  ModelUser();
		
		model.addAttribute("user",modelUser);
		
		return "logon";
	}
	

   /*
	@RequestMapping(path="/logoncontroller/validateuserpassword")
	//@ResponseBody
	public String validateUserPassword(Model model,HttpServletRequest request,
			   
			  @RequestParam("username") String pUserName,
	          @RequestParam("password") String pPassword) {
		
		        // declare status variable
		        // initialize variable with dao method. Dao method returns 1 if user name and passwod match. Otherwise returns 0
				// return status variable
		
		
		      
		System.out.println("userName :"+pUserName);
		System.out.println("password :"+pPassword);
		
		List<ModelUser> user=daoUser.getUserIdByName(pUserName);
		//Long userId=daoUser.getUserIdByName(pUserName);
		Long userId=user.get(0).getUserId();
		
		System.out.println("user id " +userId);
		
		// ModelUser sessionUser = (ModelUser)request.getSession().setAttribute("user",user);
		
		//System.out.println("user id " +userId.getUserId());
		
		int sd = daoLogon.validateUserPassword(pUserName, pPassword);
		
		System.out.println("sd " +sd);
		
		ModelUser modelUser=new ModelUser();
		
		modelUser.setUserId(userId);
		
		model.addAttribute("modelUser", modelUser);	
		
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
		
		
		
		for(ModelUserObject a:listModelUserObjectSetup) {
			
			System.out.println("object name " + a.getModelObject().getObjectName());
		}
		
		// object list for a user
		
		if(sd==1)
		{
			
			System.out.println("home");
			//return "home_page";
			
		
			
			return "home_page_left_panel";
			
			//return "redirect:/homepagecontroller/init";
			//return "redirect:/production_plan";
			
		}
		
//		if(sd==2)
//		{
//			
//			String msg="Name is incorrect";
//       	    model.addAttribute("message",msg );
//			
//			System.out.println("logon");
//			return"logon";
//			
//		}
//		if(sd==3)
//		{
//			
//			String msg="Password is incorrect";
//       	    model.addAttribute("message",msg );
//			
//			System.out.println("logon");
//			return"logon";
//			
//		}
		else 
		{
			
		String msg="Information are incorrect";
   	    model.addAttribute("message",msg );
		System.out.println("logon");
		return"logon";
			
		}
		
		
	}
	*/
	
	
	
	
	//CREATED BY SOHEL RANA ON 16/04/2019
	
	@RequestMapping(path="/logoncontroller/validateuserpassword")
	//@ResponseBody
	public String validateUserPassword(Model model,HttpServletRequest request,
			   
			  @RequestParam("username") String pUserName,
	          @RequestParam("password") String pPassword) {
		
		        // declare status variable
		        // initialize variable with dao method. Dao method returns 1 if user name and passwod match. Otherwise returns 0
				// return status variable
		
		 
		      
		System.out.println("userName :"+pUserName);
		System.out.println("password :"+pPassword);
		
		List<ModelUser> user=daoLogonImp.userValidate(pUserName, pPassword);
			
			
		System.out.println("data size"  + user.size());
			 
		
		int checkSize=user.size();
		
		
		
		if(checkSize==1) {
			System.out.println("wellcome to mas");
			
			Long userId=user.get(0).getUserId();
			
			System.out.println("access user id " +userId);
			
			
			  ModelUser logonUser=daoLogonImp.getLogonUserName(userId);
			  
			  String userName=logonUser.getUserName();
			  
			  System.out.println("logon user name is :" + userName);
			  
			  String name="User Name : " + userName;
			  model.addAttribute("name",name );	
			
			
			ModelUser modelUser=new ModelUser();
			
			modelUser.setUserId(userId);
			
			model.addAttribute("modelUser", modelUser);	
			
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
			
			
			
			for(ModelUserObject a:listModelUserObjectSetup) {
				
				System.out.println("object name " + a.getModelObject().getObjectName());
			}
			
			
			return "home_page_left_panel";
		}
		
		if(checkSize==0) {
			
			String msg="User Name Or Password Is Incorrect .!";
       	    model.addAttribute("message",msg );
			System.out.println("user name or password is incorrect");
			
			String uname=pUserName;
			String password=pPassword;
			
			model.addAttribute("uname", uname);
			model.addAttribute("password",password);
			
		}
		
		
			
		return"logon";
		
		
	}
}
