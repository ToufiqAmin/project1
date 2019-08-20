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
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoShift;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelShift;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.ShiftRepository;

@Controller
public class ShiftController {
	
	@Autowired
	private DaoShift daoShift;
	
	@Autowired
	private ShiftRepository shiftRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;

	
	
	@RequestMapping(path="/shift/{userId}", method = RequestMethod.GET)
	public String init(@PathVariable Long userId,Model model) {
		
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
		
		ModelShift modelShift= new ModelShift();
		modelShift.setEnteredBy(modelUser);
		model.addAttribute("shift", modelShift);
		return "shift";
	}
	
	
    @RequestMapping(path="/shift/save", method =RequestMethod.POST)
	public String saveShift(ModelShift shift) {
    	
    	 Long userId=systemUserId;
    	 
    	 java.util.Date dates = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(dates.getTime());
    	
    	if(shift.getShiftId()==null )
		{
    		java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			shift.setEntryTimestamp(entryTime);
			//shift.setEnteredBy(1L);
			daoShift.saveShift(shift);
		}
		else
		{
			
			//System.out.println("updated by " +enteredBy);
			
			java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			Optional<ModelShift> existsShift= shiftRepository.findById(shift.getShiftId());
			
			shift.setEnteredBy(existsShift.get().getEnteredBy());
			shift.setEntryTimestamp(existsShift.get().getEntryTimestamp());
			
			    ModelUser user=new ModelUser();
				user.setUserId(userId);
			// ModelUser modelUser=new ModelUser();
			// modelUser.setUserId(enteredBy);
			shift.setUpdatedBy(user);
			//shift.setUpdatedBy(2L);
			shift.setUpdateTimestap(updateTime);
			
			daoShift.saveShift(shift);
		}
		
		return "redirect:/shiftcontroller/"+shift.getShiftId();
	}
    
    @RequestMapping(path = "/shiftcontroller/{id}", method = RequestMethod.GET)
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
				
				
				
     Optional<ModelShift> shiftById;
     shiftById=shiftRepository.findById(id);
	 if( shiftById.get().getActiveStatus()==1)
		 shiftById.get().setActive(true); 
		else
		shiftById.get().setActive(false);	
	 
        model.addAttribute("shift", shiftById);
        
        if(id!=0) {
        	String msg="Successfully Saved";
        	 model.addAttribute("message",msg );
        }
        else
        {
        	String msg="Some Error Occured";
        	 model.addAttribute("message",msg);
        }
               
        return "shift";
}
	


    
}
