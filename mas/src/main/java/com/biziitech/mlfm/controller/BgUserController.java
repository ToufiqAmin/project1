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
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.dao.DaoUser;
import com.biziitech.mlfm.bg.daoimp.DaoCountryImp;
import com.biziitech.mlfm.bg.daoimp.DaoPhoneImp;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelOrgBranch;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.BgOrgBranchRepository;
import com.biziitech.mlfm.repository.BgOrgRepository;
import com.biziitech.mlfm.repository.PhoneRepository;
import com.biziitech.mlfm.repository.UserRepository;


@Controller
public class BgUserController {
	
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoCountryImp daoCountryImp;
	
	@Autowired
	private DaoPhoneImp daoPhoneImp;
	
	@Autowired
	private BgOrgRepository bgOrgRepository;
	
	@Autowired
	private BgOrgBranchRepository bgOrgBranchRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhoneRepository phoneRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;  // used to pass userid in different urls. Need to modify this for good programming practice as there may have chance to show errors in future.
	
	@RequestMapping(path="/bgusercontroller/init/{id}")
	public String Init(@PathVariable Long id,Model model) {
		
		Long userId=id;
		
		System.out.println("user ID :" +userId);
		 ModelUser modelUser=new ModelUser();
		 modelUser.setUserId(userId);
		 this.systemUserId=userId;
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
		
		List <ModelCountry> countryList= daoCountryImp.getCountryName();
        
        model.addAttribute("countryList",countryList);
        model.addAttribute("orgList",bgOrgRepository.findOrg());
        
        System.out.println("Size" + countryList.size());
        
        ModelUser user=new ModelUser();
        model.addAttribute("user", user);
        
        List<ModelPhone> numberList= new ArrayList<>();
        
        model.addAttribute("phoneList",numberList);
        
        ModelPhone modelPhone= new ModelPhone();	
        model.addAttribute("modelPhone",modelPhone);
		
		return "user";
	}
	
	
	@RequestMapping(path="/bgusercontroller/organization/branch")
	@ResponseBody
	public List <ModelOrgBranch> findBranch(@RequestParam("id")Long id){	
		
		return bgOrgBranchRepository.findBranch(id);
	}
	
	@RequestMapping(path = "/bgusercontroller/saveuser", method = RequestMethod.POST) 
 	public  String saveUser(ModelUser modelUser,Model model) {
		
		
		
		 Long userId=systemUserId;
   	     
		
		
		if(modelUser.getUserId()==null )
		{
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		
		modelUser.setEntryTimestamp(entryTime);
//		modelUser.setGender(1);
	 modelUser.setEnteredBy(userId);
		modelUser.setBranchUnitId(1L);
		
		daoUser.save(modelUser);
		}
		else
		{
			Optional<ModelUser> existUser= userRepository.findById(modelUser.getUserId());
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
			
			modelUser.setEnteredBy(existUser.get().getEnteredBy());
			modelUser.setEntryTimestamp(existUser.get().getEntryTimestamp());
			
			
			// ModelUser user=new ModelUser();
			//	user.setUserId(userId);
			modelUser.setUpdatedBy(userId);
			modelUser.setUpdateTimestap(updateTime);
			modelUser.setBranchUnitId(1L);
			daoUser.save(modelUser);
		}
		
		 
	        	String msg="Successfully Saved";
	        	model.addAttribute("message",msg );
	        	
	        	List <ModelCountry> countryList= daoCountryImp.getCountryName();
	            
	            model.addAttribute("countryList",countryList);
	            model.addAttribute("orgList",bgOrgRepository.findOrg());
	            
	            System.out.println("Size" + countryList.size());
	            
	            ModelUser user=new ModelUser();
	            model.addAttribute("user", user);
	            
	            List<ModelPhone> numberList= new ArrayList<>();
	            
	            model.addAttribute("phoneList",numberList);
	            
	            ModelPhone modelPhone= new ModelPhone();	
	            model.addAttribute("modelPhone",modelPhone);
	            
	            
	            
	            
	           
	            
	            
	    
		
		return "redirect:/bgusercontroller/add/"+modelUser.getUserId();
		 }
	
	@RequestMapping(path = "/bgusercontroller/add/{id}", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
		
		
		
		
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
		
		
		
		
	 Optional<ModelUser> userById;
	 userById= userRepository.findById(id);
	if( userById.get().getActiveStatus()==1)
		userById.get().setActive(true);
		else
			userById.get().setActive(false);
        model.addAttribute("user", userById);
        if(id!=0) {
        	String msg="Successfully Saved";
        	 model.addAttribute("message",msg );
        }
        else
        {
        	String msg="Some Error Occured";
        	 model.addAttribute("message",msg);
        }
        List <ModelCountry> countryList= daoCountryImp.getCountryName();
        
        model.addAttribute("countryList",countryList);
        model.addAttribute("orgList",bgOrgRepository.findOrg());
        
        System.out.println("Size" + countryList.size());
        
        ModelUser modelUser = new ModelUser();
        modelUser.setUserId(id);
        
        ModelPhone modelPhone = new ModelPhone();
        modelPhone.setModelUser(modelUser);
        
       ModelCountry modelCountry=userById.get().getCountryId();
       modelPhone.setModelCountry(modelCountry);
       model.addAttribute("modelPhone",modelPhone);
       
       List<ModelPhone> numberList= daoPhoneImp.getUserPhoneDataByUserId(id);
        
       model.addAttribute("phoneList",numberList);
        
        return "user";
}
	
	// @RequestMapping(path = "/bgusercontroller/update/{id}/{userId}", method = RequestMethod.GET)
	 @RequestMapping(path = "/bgusercontroller/update/{id}", method = RequestMethod.GET)
	   // public String updateUser(@PathVariable Long id,@PathVariable Long userId, Model model) {
		 public String updateUser(@PathVariable Long id,Model model) {
		 
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
		 
		 
	    	
	    	 Optional<ModelUser> userById=userRepository.findById(id);
	    	 if( userById.get().getActiveStatus()==1)
	    		 userById.get().setActive(true); 
	    	else
	    		userById.get().setActive(false);	
	            model.addAttribute("user", userById);
	           
	            
	        List <ModelCountry> countryList= daoCountryImp.getCountryName();
	            
	        model.addAttribute("countryList",countryList);
	        model.addAttribute("orgList",bgOrgRepository.findOrg());
	        
	        
	        ModelUser modelUser1 = new ModelUser();
	        modelUser1.setUserId(id);
	        
	        ModelPhone modelPhone = new ModelPhone();
	        modelPhone.setModelUser(modelUser1);
	        
	       ModelCountry modelCountry=userById.get().getCountryId();
	       modelPhone.setModelCountry(modelCountry);
	       model.addAttribute("modelPhone",modelPhone);
	       
	       List<ModelPhone> numberList= daoPhoneImp.getUserPhoneDataByUserId(id);
	        
	       model.addAttribute("phoneList",numberList);
	    	
	        return "user";

	    }
	 
	 @RequestMapping(path = "/bgusercontroller/savephone", method = RequestMethod.POST)
	    public String savePhone(ModelPhone phone, Model model) {
		 java.util.Date date = new java.util.Date();
			java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());
			if(phone.getPhoneId()==null )
			{
				phone.setEntryTimestap(sqlDate);
				phone.setEntered_By(1);
				 this.daoPhoneImp.savePhone(phone);
			}
			else {
				
				Optional<ModelPhone> exitsPhone= this.daoPhoneImp.getPhoneById(phone.getPhoneId());
				
				phone.setUpdatedBy(1);
				phone.setUpdateTimestap(sqlDate);
		    	phone.setEntered_By(exitsPhone.get().getEntered_By());
		    	phone.setEntryTimestap(exitsPhone.get().getEntryTimestap());
		    	this.daoPhoneImp.savePhone(phone);
			}
			
			String msg="Successfully Saved";
        	model.addAttribute("message",msg );
        	
        	List <ModelCountry> countryList= daoCountryImp.getCountryName();
            
            model.addAttribute("countryList",countryList);
            model.addAttribute("orgList",bgOrgRepository.findOrg());
            
            System.out.println("Size" + countryList.size());
            
            ModelUser user=new ModelUser();
            model.addAttribute("user", user);
			
	        return "redirect:/bgusercontroller/add/"+phone.getModelUser().getUserId();
	}
	
	 @ResponseBody
	    @RequestMapping(value = "/bgusercontroller/phone/{id}", method = RequestMethod.GET)
	    public Optional<ModelPhone> loadEntity(@PathVariable("id") Long id) {
	//	 System.out.println(phone.getPhoneById(id));
	        return daoPhoneImp.getPhoneById(id);
	}
	

}
