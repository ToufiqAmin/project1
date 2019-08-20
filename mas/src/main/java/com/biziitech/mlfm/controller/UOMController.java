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
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelBusinessType;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.UOMRepository;




@Controller
public class UOMController {

@Autowired
private DaoUOMImp daoUOM;

@Autowired
private DaoUOMImp uomName;

@Autowired 
private UOMRepository uomRepository;

@Autowired
private DaoUserObject daoUserObject;

@Autowired
private DaoLogonImp daoLogonImp;


private Long systemUserId;


	 @RequestMapping(path = "/uom/init/{userId}", method = RequestMethod.GET)
	    public String createUom(@PathVariable Long userId,Model model) {
		 
		 
		 
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
		 
		 
		 ModelUOM newUOM=new ModelUOM();
		 newUOM.setEnteredBy(modelUser);
		 model.addAttribute("uom", newUOM);
		 
		 String msg=" ";
	     model.addAttribute("message", " ");
	        
	     return "uom_upload";

	}
	 
    @RequestMapping(path = "/uom/save", method = RequestMethod.POST) 
    	public  String saveuom(ModelUOM uom,ModelUOM Uom) {
    	
    	
    	 Long userId=systemUserId;
		
    	if(uom.getUomId()==null )
    			{
    		
    		 
    		
    		java.util.Date date = new java.util.Date();
    		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
    			
    				uom.setEntryTimestamp(entryTime);
    				ModelUser modelUser = new ModelUser();
    				modelUser.setUserId(userId);
    				uom.setEnteredBy(modelUser);
    				daoUOM.saveUOM(uom);
    			}
    			else
    			{
    				
    				java.util.Date date = new java.util.Date();
    				java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
    				
    				
    				Optional<ModelUOM> existsuom=uomRepository.findById(uom.getUomId());
    				//uom.setEnteredBy(existsuom.get().getEnteredBy().getUserId());
    			
    				uom.setEntryTimestamp(existsuom.get().getEntryTimestamp());
    				
    				ModelUser modelUser = new ModelUser();
    				modelUser.setUserId(userId);
    				uom.setUpdatedBy(modelUser);
    				uom.setUpdateTimestamp(updateTime);
    				
    				daoUOM.saveUOM(uom);
    			}
    	
    
    	 return "redirect:/uomcontroller/edituom/"+uom.getUomId();
    	
    }
    
    
    @RequestMapping(path = "/uomcontroller/edituom/{id}", method = RequestMethod.GET)
    public String edituom(@PathVariable Long id, Model model) {
    	
    	 System.out.println("UOM ID :" +id);
    	 
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
    
     Optional<ModelUOM> uomById;
     uomById=uomRepository.findById(id);
	 if( uomById.get().getActiveStatus()==1)
		uomById.get().setActive(true); 
     else
        uomById.get().setActive(false);	
     model.addAttribute("uom", uomById);
        if(id!=0) {
        	String msg="Successfully Saved";
        	 model.addAttribute("message",msg );
        }
        else
        {
        	String msg="Some Error Occured";
        	 model.addAttribute("message",msg);
        }
               
        return "uom_upload";
}
    
    
    @RequestMapping(path = "/uom/update/{id}", method = RequestMethod.GET)
    public String updateuom(@PathVariable Long id, Model model) {
    	
    	
    	
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
				
    	
    	
    	
    	 Optional<ModelUOM> uomById;
         uomById=uomRepository.findById(id);
    	 if( uomById.get().getActiveStatus()==1)
    		uomById.get().setActive(true); 
    		else
    		uomById.get().setActive(false);	
            model.addAttribute("uom", uomById);
            if(id!=0) {
            	String msg=" ";
            	 model.addAttribute("message",msg );
            }
            else
            {
            	String msg="Some Error Occured";
            	 model.addAttribute("message",msg);
            }
    	
        return "uom_upload";

    } 	
    
    
    
   
}
