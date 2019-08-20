package com.biziitech.mlfm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.biziitech.mlfm.dao.DaoOrderOwnerZone;

import com.biziitech.mlfm.model.ModelOrderOwnerZone;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.repository.OrderOwnerZoneRepository;

@Controller
public class OrderOwnerZoneController {
	
	@Autowired
	private DaoOrderOwnerZone daoOrderOwnerZone;
	
	@Autowired
	private OrderOwnerZoneRepository orderOwnerZoneRepository;
	
	
	 @RequestMapping(path = "/orderownerzonecontroller/init", method = RequestMethod.GET)
	    public String init(Model model) {
		 
		 ModelOrderOwnerZone modelOrderOwnerZone=new ModelOrderOwnerZone();
		 model.addAttribute("modelOrderOwnerZone", modelOrderOwnerZone);
		 
		 String msg=" ";
	     model.addAttribute("message", msg);
	        
	     return "buyerZone";

	}
	 
	 
	 
	 @RequestMapping(path = "/orderownerzonecontroller/saveorderownerzone", method = RequestMethod.POST) 
	 	public  String saveOrderOwnerZone(ModelOrderOwnerZone modelOrderOwnerZone, Model model) {
	 	
	 	
			 if(modelOrderOwnerZone.getOrderOwnerZoneId()==null )
				{
				 
				
			 
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());

	 			
			modelOrderOwnerZone.setEntryTimestamp(entryTime);
			
			modelOrderOwnerZone.setEnteredBy(1L);
			daoOrderOwnerZone.saveOrderOwnerZone(modelOrderOwnerZone);
			
			String msg="Successfully Saved";
     	model.addAttribute("message",msg );
			
			
				}
				else
				{
					java.util.Date date = new java.util.Date();
					java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
					Optional<ModelOrderOwnerZone> existsOrderOwnerZone=orderOwnerZoneRepository.findById(modelOrderOwnerZone.getOrderOwnerZoneId());
					modelOrderOwnerZone.setEnteredBy(existsOrderOwnerZone.get().getEnteredBy());
					modelOrderOwnerZone.setEntryTimestamp(existsOrderOwnerZone.get().getEntryTimestamp());
					modelOrderOwnerZone.setUpdatedBy(existsOrderOwnerZone.get().getEnteredBy());
					modelOrderOwnerZone.setUpdateTimestamp(entryTime);
					
					daoOrderOwnerZone.saveOrderOwnerZone(modelOrderOwnerZone);
					String msg="Successfully Saved";
		        	model.addAttribute("message",msg );
				}
			 
			 
	 
	 	 return "buyerZone";
	 	
	 }
	 
	 
	 @RequestMapping(path = "/orderownerzonecontroller/update/{id}", method = RequestMethod.GET)
	    public String updateOrderOwnerZone(@PathVariable Long id, Model model) {
	    	
	    	 Optional<ModelOrderOwnerZone> zoneById;
	    	 zoneById=orderOwnerZoneRepository.findById(id);
	    	 if( zoneById.get().getActiveStatus()==1)
	    		 zoneById.get().setActive(true); 
	    		else
	    			zoneById.get().setActive(false);	
	            model.addAttribute("modelOrderOwnerZone", zoneById);
	            if(id!=0) {
	            	String msg=" ";
	            	 model.addAttribute("message",msg );
	            }
	            else
	            {
	            	String msg="Some Error Occured";
	            	 model.addAttribute("message",msg);
	            }
	    	
	        return "buyerZone";

	    } 	

}
