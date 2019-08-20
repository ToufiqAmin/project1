package com.biziitech.mlfm.bg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.bg.daoimp.DaoCurrencyImp;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.CurrencyRepository;

@Controller
public class CurrencyListController {

	@Autowired 
	private CurrencyRepository currencyRepository;
	
	@Autowired 
	private DaoCurrencyImp daoCurrencyImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	
	private Long systemUserId;
	
	@RequestMapping(path = "/currency/list/{userId}", method = RequestMethod.GET)
    public String getCurrencyList(@PathVariable Long userId,Model model) {
	    
		 System.out.println("user ID :" +userId);
			
	        ModelUser modelUser=new ModelUser();
			
			modelUser.setUserId(userId);
			
			this.systemUserId=userId;
			
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
	   
        return "currency_list";
   }
	
	@RequestMapping(path="/currency/search")
	public String getCurrencyList(Model model,@RequestParam("currencyName")String currencyName,@RequestParam("shortCode")String shortCode
			,@RequestParam("remarks")String remarks, @RequestParam(value = "active", required = false) String active
				
											
			) {
		
        int status=1;
		
		if(active!=null)
			status=1;
		else
			status=0;
		
		model.addAttribute("currencyList", daoCurrencyImp.getCurrencyListByCraiteria(currencyName, shortCode, remarks, status));
		
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
		
		return "currency_list";
	}
}
