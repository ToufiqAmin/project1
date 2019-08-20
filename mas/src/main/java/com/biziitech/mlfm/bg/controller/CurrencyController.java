package com.biziitech.mlfm.bg.controller;

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

import com.biziitech.mlfm.bg.daoimp.DaoCurrencyImp;
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.CurrencyRepository;
import com.biziitech.mlfm.repository.LogonRepository;
import com.biziitech.mlfm.repository.UOMRepository;

@Controller
public class CurrencyController {

	@Autowired
	private DaoCurrencyImp daoCurrencyImp;
	
	@Autowired 
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;
	
	
	@Autowired
	private LogonRepository logonRepository;
	  
	 private Long systemUserId;
	  
	 @RequestMapping(path = "/currency/init/{userId}", method = RequestMethod.GET)
	    public String createCurrency(@PathVariable Long userId,Model model) {
		 
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
			
		 
		 
		 ModelCurrency currency=new ModelCurrency();
		 currency.setEnteredBy(modelUser);
		 model.addAttribute("currency", currency);
		 
		 String msg=" ";
	     model.addAttribute("message", " ");
	        
	     return "currency";

	}
	 
	 
	 
	 @RequestMapping(path = "/currency/save", method = RequestMethod.POST) 
 	public  String saveCurrency(ModelCurrency currency,@RequestParam(value = "defalutFlag", required = false) String defalutFlag) {
 	    
		
		 
		 
		// ModelCurrency modelCurrency= new ModelCurrency();
		 
		 System.out.println("system user :" + systemUserId);
		   
		   Long userId=systemUserId;
		   
		   java.util.Date dates = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(dates.getTime());
  
		 
		 if(defalutFlag==null) {
			 currency.setDefaultFlag(0);
		 }
		 else {
			 currency.setDefaultFlag(1);
		 }
		
	
 	if(currency.getCurrencyId()==null )
 			{
 			java.util.Date date = new java.util.Date();
 			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
 			currency.setEntryTimestamp(entryTime);
 			//currency.setEnteredBy(enteredBy);
 			
 			daoCurrencyImp.saveCurrency(currency);
 			}
 			else
 			{
 				Optional<ModelCurrency> existscurrency=currencyRepository.findById(currency.getCurrencyId());
 				currency.setEnteredBy(existscurrency.get().getEnteredBy());
 				currency.setEntryTimestamp(existscurrency.get().getEntryTimestamp());				
 				
 				ModelUser user=new ModelUser();
 				user.setUserId(userId);
 				currency.setUpdatedBy(user); 				
 				currency.setUpdateTimestap(updateTime);
 				daoCurrencyImp.saveCurrency(currency);
				
 			}
 
 	 return "redirect:/currency/inits/"+currency.getCurrencyId();
 	
   }
	 
	   @RequestMapping(path = "/currency/inits/{id}", method = RequestMethod.GET)
	    public String editCurrency(@PathVariable Long id, Model model) {
	    
		  
		   
		   System.out.println("system user :" + systemUserId);
		   
		   Long userId=systemUserId;
		   
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
					
					model.addAttribute("userId",userId);
		   
		   
		   
	     Optional<ModelCurrency> currencyById;
	     
	     currencyById=currencyRepository.findById(id);
	          
	     if( currencyById.get().getActiveStatus()==1)
	    	 currencyById.get().setActive(true); 
	      else
	    	  currencyById.get().setActive(false);	
	          model.addAttribute("currency", currencyById);
				
		 
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
	               
	        return "currency";
	}
	   
	   
	   @RequestMapping(path = "/currency/update/{id}", method = RequestMethod.GET)
	    public String updateCurrency(@PathVariable Long id, Model model) {
	    	
		   
System.out.println("system user :" + systemUserId);
		   
		   Long userId=systemUserId;
		   
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
					
					model.addAttribute("userId",userId);
		   
		   
		   
		   Optional<ModelCurrency> currencyById;
		   		   
		     currencyById=currencyRepository.findById(id);
		          
		     if( currencyById.get().getActiveStatus()==1)
		    	 currencyById.get().setActive(true); 
		      else
		    	  currencyById.get().setActive(false);	
		          model.addAttribute("currency", currencyById);
					
			 
		        if(id!=0) {
		        	String msg="";
		        	 model.addAttribute("message",msg );
		        }
		        else
		        {
		        	String msg="Some Error Occured";
		        	 model.addAttribute("message",msg);
		        }
		               
		        return "currency";
	    } 	
	 
	 
	   
	   //created by sohel rana on 15/04/2019
	   
	    
}
