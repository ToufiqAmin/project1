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
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MachineTypeRepository;

@Controller
public class MachineTypeController {
	
	@Autowired
	private DaoMachineTypeImp daoMachine;
	
	@Autowired 
	private MachineTypeRepository machineRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@RequestMapping(path = "/machine/init/{userId}", method = RequestMethod.GET)
    public String createMachineType(@PathVariable Long userId,Model model) {
		
		
		
		
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
		
		
		 
		 ModelMachineType newMachine=new ModelMachineType();
		 model.addAttribute("machineType", newMachine);
		 
		 String msg=" ";
	     model.addAttribute("message", " ");
	
        
         return "machine_type";

        }
	
	 @RequestMapping(path = "/machine/save", method = RequestMethod.POST) 
 	  public  String saveMachine(ModelMachineType machine,Model model) {
		 
		 
		 
		 
		 Long userId=systemUserId;
		
		 
		 
		 
 	
 	if(machine.getMachineTypeId()==null)
 			{
 			java.util.Date date = new java.util.Date();
 			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
 				machine.setEntryTimestamp(entryTime);
 				
 				 ModelUser user=new ModelUser();
 				 user.setUserId(userId);
 				
 				machine.setEnteredBy(user);
 				daoMachine.saveMachine(machine);
 			}
 			else
 			{
 				java.util.Date date = new java.util.Date();
 	 			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
 				
 				Optional<ModelMachineType> existsmachine=machineRepository.findById(machine.getMachineTypeId());
 				
 				 ModelUser user=new ModelUser();
 				 user.setUserId(userId);
 				
 				machine.setUpdatedBy(user);
 				machine.setUpdateTimestamp(entryTime);
 				
 				
 				machine.setEnteredBy(existsmachine.get().getEnteredBy());
 				machine.setEntryTimestamp(existsmachine.get().getEntryTimestamp());
 				
 				daoMachine.saveMachine(machine);
 			}
 	
 	 return "redirect:/machinecontroller/editmachine/"+machine.getMachineTypeId();
 	
     }
	 
	 @RequestMapping(path = "/machinecontroller/editmachine/{id}", method = RequestMethod.GET)
	    public String editmachine(@PathVariable Long id, Model model) {
		 
		 
		 
		 
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
		 
		 
		 
		 
		 
	    
	     Optional<ModelMachineType> machineById;
	     machineById=machineRepository.findById(id);
		 if( machineById.get().getActiveStatus()==1)
			 machineById.get().setActive(true); 
			else
				machineById.get().setActive(false);	
	            model.addAttribute("machineType", machineById);
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "machine_type";
	}
	 
	 @RequestMapping(path = "/machineType/update/{id}", method = RequestMethod.GET)
	    public String updateMachineType(@PathVariable Long id, Model model) {
		 
		 
		 
		 
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
		 
		 
	    	
	    	 Optional<ModelMachineType> machineTypeById;
	         machineTypeById=machineRepository.findById(id);
	    	 if( machineTypeById.get().getActiveStatus()==1)
	    		 machineTypeById.get().setActive(true); 
	    		else
	    			machineTypeById.get().setActive(false);	
	            model.addAttribute("machineType", machineTypeById);
	            if(id!=0) {
	            	String msg=" ";
	            	 model.addAttribute("message",msg );
	            }
	            else
	            {
	            	String msg="Some Error Occured";
	            	 model.addAttribute("message",msg);
	            }
	    	
	        return "machine_type";

	    } 	
 
}
