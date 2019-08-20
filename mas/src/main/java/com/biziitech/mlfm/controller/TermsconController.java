package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoTermsconImp;
import com.biziitech.mlfm.model.ModelTermsCon;
import com.biziitech.mlfm.model.ModelTermsConType;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.TermsConRepository;
import com.biziitech.mlfm.repository.TermsconTypeRepository;


@Controller
public class TermsconController {
   
	@Autowired
	private TermsconTypeRepository termsconTypeRepository;
	
	@Autowired
	private TermsConRepository termsconRepository;
	
	@Autowired
	private DaoTermsconImp daoTermsconImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;

	 @RequestMapping(path = "/termscon/init/{userId}", method = RequestMethod.GET)
	    public String createTermscon(@PathVariable Long userId,Model model) {
		 
		 
		 
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
		 
		 
		 
		 ModelTermsCon newTermscon=new ModelTermsCon();
		 model.addAttribute("termscon", newTermscon);
		 
		 model.addAttribute("typeList", termsconTypeRepository.findTermsConType());
		 
		 String msg=" ";
	     model.addAttribute("message", " ");
	        
	     return "termscon";

	}
	 
	 @RequestMapping(path = "/termscon/save", method = RequestMethod.POST) 
	 	public  String saveTermscon(ModelTermsCon termscon) {
		 
		  Long userId=systemUserId;
	 	
	 	  if(termscon.getTermsConId()==null)
	 			{
	 			java.util.Date date = new java.util.Date();
	 			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	 			
	 			 ModelUser user=new ModelUser();
				 user.setUserId(userId);
	 			termscon.setEntryTimestamp(entryTime);
	 			termscon.setEnteredBy(user);
	 				
	 			daoTermsconImp.saveTermscon(termscon);
	 			}
	 			else
	 			{
	 				Optional<ModelTermsCon> existsTermscon=termsconRepository.findById(termscon.getTermsConId());
	 				
	 				java.util.Date date = new java.util.Date();
		 			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
	 				
	 				termscon.setEnteredBy(existsTermscon.get().getEnteredBy());
	 				termscon.setEntryTimestamp(existsTermscon.get().getEntryTimestamp());
	 				
	 				 ModelUser user=new ModelUser();
	 				 user.setUserId(userId);
	 				 
	 				 termscon.setUpdatedBy(user);
	 				 termscon.setUpdateTimestamp(updateTime);
	 				
	 				daoTermsconImp.saveTermscon(termscon);
	 				
	 			}
	 	
	 	        return "redirect:/termsconcontroller/editTermsconType/"+termscon.getTermsConId();
	 	
	 }
	 
	 @RequestMapping(path = "/termsconcontroller/editTermsconType/{id}", method = RequestMethod.GET)
	    public String editTermsconType(@PathVariable Long id, Model model) {
		 
		 
		 
		 
		 
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
		 
		 
		 
		 
		 
	    
		 
		 model.addAttribute("typeList", termsconTypeRepository.findTermsConType());
		 
	     Optional<ModelTermsCon> termconById;
	     termconById=termsconRepository.findById(id);
		 if( termconById.get().getActiveStatus()==1) {
			 termconById.get().setActive(true); 
		     model.addAttribute("termscon", termconById);
		 }
	     else {
	    	    
	    	 termconById.get().setActive(false);	
	         model.addAttribute("termscon", termconById);
	     }
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "termscon";
	}
	 
	 @RequestMapping(path = "/termscon/update/{id}", method = RequestMethod.GET)
	    public String updateTermsconType(@PathVariable Long id, Model model) {
		 
		 
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
	    
		 
		 model.addAttribute("typeList", termsconTypeRepository.findTermsConType());
		 
	     Optional<ModelTermsCon> termconById;
	     termconById=termsconRepository.findById(id);
		 if( termconById.get().getActiveStatus()==1) {
			 termconById.get().setActive(true); 
		     model.addAttribute("termscon", termconById);
		 }
	     else {
	    	    
	    	 termconById.get().setActive(false);	
	         model.addAttribute("termscon", termconById);
	     }
	        if(id!=0) {
	        	String msg="";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "termscon";
	}
}
