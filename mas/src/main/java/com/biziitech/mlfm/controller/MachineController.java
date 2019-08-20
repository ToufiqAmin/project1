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
import com.biziitech.mlfm.daoimpl.DaoMachineImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MachineRepository;

@Controller
public class MachineController {
	
	@Autowired
	private DaoMachineTypeImp machine;
	
	@Autowired
	private DaoUOMImp uom;
	
	@Autowired 
	private MachineRepository machineRepository;
	
	@Autowired
	private DaoMachineImp daoMachine;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@RequestMapping(path = "/machinedata/init/{userId}", method = RequestMethod.GET)
    public String createMachineData(@PathVariable Long userId,Model model) {
		
		
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
		
		ModelMachine newmachine=new ModelMachine();
		model.addAttribute("newmachine", newmachine);
		
		List<ModelUOM> uomName=uom.getUomName();
		model.addAttribute("uomList", uomName);
		
		
		List<ModelMachineType> machineName= machine.getMachineName();
		model.addAttribute("machineTypeList",machineName);
        
         return "Machine_data_upload";

        }
	
	 @RequestMapping(path = "/machinedata/save", method = RequestMethod.POST) 
	  public  String saveMachineData(ModelMachine machine) {
	
		 Long userId=systemUserId;
		 
	if(machine.getMachineId()==null)
			{

	    java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	 
		
		 ModelUser user=new ModelUser();
		 user.setUserId(userId);  
				machine.setEntryTimestamp(entryTime);
				machine.setEnteredBy(user);
				daoMachine.saveMachineData(machine);
			}
			else
			{
				Optional<ModelMachine> existsmachine=machineRepository.findById(machine.getMachineId());
				
				 java.util.Date date = new java.util.Date();
					java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
				
				machine.setEnteredBy(existsmachine.get().getEnteredBy());
				machine.setEntryTimestamp(existsmachine.get().getEntryTimestamp());
				
				
				 ModelUser user=new ModelUser();
				 user.setUserId(userId);
				machine.setUpdatedBy(user);
				machine.setUpdateTimestap(updateTime);
				
				
				daoMachine.saveMachineData(machine);
			}
	
	 return "redirect:/machinecontroller/editmachinedata/"+machine.getMachineId();
	
    }
	 
	 @RequestMapping(path = "/machinecontroller/editmachinedata/{id}", method = RequestMethod.GET)
	    public String editmachinedata(@PathVariable Long id, Model model) {
		 
		 
		 
		 
		 
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
		 
		 
		 
		 
	    
		 List<ModelMachineType> machineName= machine.getMachineName();
		 model.addAttribute("machineTypeList",machineName);
		
		List<ModelUOM> uomName=uom.getUomName();
		model.addAttribute("uomList", uomName);
	    	 
	     Optional<ModelMachine> machineById;
	     machineById=machineRepository.findById(id);
		 if( machineById.get().getActiveStatus()==1)
			 machineById.get().setActive(true); 
			else
				machineById.get().setActive(false);	
	            model.addAttribute("newmachine", machineById);
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "Machine_data_upload";
	}
	 
	 
	    @RequestMapping(path = "/machinedata/update/{id}", method = RequestMethod.GET)
	    public String updatemachine(@PathVariable Long id, Model model) {
	    	
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

	    	List<ModelMachineType> machineName= machine.getMachineName();
			model.addAttribute("machineTypeList",machineName);
			 
			 List<ModelUOM> uomName=uom.getUomName();
			 model.addAttribute("uomList", uomName);
	    	
	    	 Optional<ModelMachine> machineById;
	         machineById=machineRepository.findById(id);
	    	 if( machineById.get().getActiveStatus()==1)
	    		machineById.get().setActive(true); 
	    		else
	    		machineById.get().setActive(false);	
	            model.addAttribute("newmachine", machineById);
	            if(id!=0) {
	            	String msg=" ";
	            	 model.addAttribute("message",msg );
	            }
	            else
	            {
	            	String msg="Some Error Occured";
	            	 model.addAttribute("message",msg);
	            }
	    	
	        return "Machine_data_upload";

	    } 

}
