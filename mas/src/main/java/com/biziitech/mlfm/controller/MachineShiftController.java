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
import com.biziitech.mlfm.dao.DaoMachine;
import com.biziitech.mlfm.dao.DaoMachineShift;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelShift;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.MachineShiftRepository;

@Controller
public class MachineShiftController {
	
	@Autowired
	private DaoMachineShift daoMachineShift;
	
	@Autowired
	private MachineShiftRepository machineShiftRepository;
	
	@Autowired
	private DaoMachine daoMachine;
	
	@Autowired
	private DaoUserObject daoUserObject;
	

	@Autowired
	private DaoLogonImp daoLogonImp;


     private Long systemUserId;
	
	@RequestMapping(path="/machine_shift/{userId}")
	public String machineShiftEntry(@PathVariable Long userId,Model model,ModelMachineShift modelMachineShift) {
		
	
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
		
		modelMachineShift.setEnteredBy(modelUser);		
		model.addAttribute("machineShift", modelMachineShift);
		List<ModelShift> modelShift=daoMachineShift.getShiftList();
		model.addAttribute("shiftList",modelShift);
		System.out.println("Size: "+modelShift.size());
		//model.addAttribute("machineList",daoMachineShift.getMachineList());
		
		List<ModelMachine> machineList= daoMachine.getMachineList();
		model.addAttribute("machineList",machineList);
		
		return "machine_shift";
	
	}
	
	
	
	
	 @RequestMapping(path="/machineShift/save", method =RequestMethod.POST)
		public String saveShift(ModelMachineShift machineShift,Model model,ModelUser modelUser) {
		 
		 
		    Long userId=systemUserId;
		 
	    	
	    	if(machineShift.getMachineShiftId()==null )
			{
	    		

	  		  java.util.Date date = new java.util.Date();
	  		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    		
	    		modelUser.setUserId(userId);
	    		machineShift.setEnteredBy(modelUser);
			    machineShift.setEntryTimestamp(entryTime);
			    
			    ModelUser user=new ModelUser();
				 user.setUserId(userId);
			    
			    machineShift.setEnteredBy(user);
				daoMachineShift.saveMachineShift(machineShift);
				
				
			}
			else
			{

				  java.util.Date date = new java.util.Date();
				    java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
				
				
				Optional<ModelMachineShift> existsuom=machineShiftRepository.findById(machineShift.getMachineShiftId());
				
				
				
				machineShift.setEnteredBy(existsuom.get().getEnteredBy());
				machineShift.setEntryTimestamp(existsuom.get().getEntryTimestamp());
				
				 ModelUser user=new ModelUser();
				 user.setUserId(userId);
				
				machineShift.setUpdatedBy(user);
				machineShift.setUpdateTimestamp(updateTime);
				daoMachineShift.saveMachineShift(machineShift);
				
			//	return "redirect:/machine_shiftcontroller/"+machineShift.getMachineShiftId()+"/"+machineShift.getUpdatedBy().getUserId();
			}
			
			return "redirect:/machine_shiftcontroller/"+machineShift.getMachineShiftId();
	    	
		}
	 
	 
	 
	 @RequestMapping(path = "/machine_shiftcontroller/{id}", method = RequestMethod.GET)
	    public String editShift(@PathVariable Long id, Model model) {
		 
		 
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
		 
	    
	     Optional<ModelMachineShift> machineShiftById;
	     machineShiftById=machineShiftRepository.findById(id);
		
	     //System.out.println(" id " + id + machineShiftById.get().getMachineShiftId() );
	     
	     //System.out.println(" id " + id + machineShiftById.get().getActiveStatus() );
	     System.out.println(" string " + machineShiftById.toString());
	     
	     if( machineShiftById.get().getActiveStatus()==1)
			    machineShiftById.get().setActive(true); 
			else
				machineShiftById.get().setActive(false);	
	        
	     
	     model.addAttribute("machineShift", machineShiftById);
	        
	     if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	     
	     
	     
	     model.addAttribute("shiftList",daoMachineShift.getShiftList());
			//model.addAttribute("machineList",daoMachineShift.getMachineList());
			
			List<ModelMachine> machineList= daoMachine.getMachineList();
			model.addAttribute("machineList",machineList);
	               
	        return "machine_shift";
	}
	
	

}
