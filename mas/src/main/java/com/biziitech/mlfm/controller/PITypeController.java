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
import com.biziitech.mlfm.daoimpl.DaoPITypeImp;
import com.biziitech.mlfm.model.ModelPIType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.PITypeRepository;


@Controller
public class PITypeController {
	
	@Autowired 
	private PITypeRepository pITypeRepository;
	
	@Autowired 
	private DaoPITypeImp daoPITypeImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;

	@RequestMapping(path = "/pitype/init/{userId}", method = RequestMethod.GET)
    public String getPIType(@PathVariable Long userId,Model model) {
		
		try {
			
			
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
			
			
			
			 ModelPIType piType=new ModelPIType();
			 model.addAttribute("piType", piType);
			 
			 String msg=" ";
		     model.addAttribute("message", " ");
		        
			
			return "pi_type";
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	
		
	}
	
	 @RequestMapping(path = "/pitype/save", method = RequestMethod.POST) 
 	public  String savePIType(ModelPIType piType) {
		 
		 
		 Long userId=systemUserId;
 	
     
		try {
			
			if(piType.getPITypeId()==null )
 			{
 			java.util.Date date = new java.util.Date();
 			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
 			
 			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
 			
 			piType.setEntryTimestamp(entryTime);
 			piType.setEnteredBy(user);
 			daoPITypeImp.savePIReceiveMst(piType);
 			}
 			else
 			{
 				
 				java.util.Date date = new java.util.Date();
 	 			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
 				Optional<ModelPIType> existsType=pITypeRepository.findById(piType.getPITypeId()); 
 				piType.setEnteredBy(existsType.get().getEnteredBy());
 				piType.setEntryTimestamp(existsType.get().getEntryTimestamp());
 				
 				 ModelUser user=new ModelUser();
 				 user.setUserId(userId);
 				 
 				piType.setUpdatedBy(user);
 				piType.setUpdateTimestamp(updateTime);
 				
 				daoPITypeImp.savePIReceiveMst(piType);
 			}
 	
 
 	 return "redirect:/pitypecontroller/editpitype/"+piType.getPITypeId();
 	
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return null;
		}
		 
	
 }
	 
	 
	 @RequestMapping(path = "/pitypecontroller/editpitype/{id}", method = RequestMethod.GET)
	    public String editpitype(@PathVariable Long id, Model model) {
		 
		 
		 
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
		 
		 
	    
	     Optional<ModelPIType> pITypeById;
	     pITypeById=pITypeRepository.findById(id);
		 if( pITypeById.get().getActiveStatus()==1)
			 pITypeById.get().setActive(true); 
	     else
	    	 pITypeById.get().setActive(false);	
	         model.addAttribute("piType", pITypeById);
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "pi_type";
	}
	 
	 
	 
	 @RequestMapping(path = "/pitype/update/{id}", method = RequestMethod.GET)
	    public String updatepitype(@PathVariable Long id, Model model) {
		 
		 
		 
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
		 
		 
		 
		 
		 
	    
	     Optional<ModelPIType> pITypeById;
	     pITypeById=pITypeRepository.findById(id);
		 if( pITypeById.get().getActiveStatus()==1)
			 pITypeById.get().setActive(true); 
	     else
	    	 pITypeById.get().setActive(false);	
	         model.addAttribute("piType", pITypeById);
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "pi_type";
	}
	 
	 
	
}
