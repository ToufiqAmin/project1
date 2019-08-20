package com.biziitech.mlfm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.dao.DaoOrderOwnerZone;

@Controller
public class OrderOwnerZoneListcontroller {
	
	@Autowired 
	private DaoOrderOwnerZone daoOrderOwnerZone;
	
	
	@RequestMapping(path = "/orderownerzonelistcontroller/list", method = RequestMethod.GET)
    public String getOrderOwnerZoneList(Model model) {
	 

	   
        return "buyerZoneList";
   }
	
	
	@RequestMapping(path="/orderownerzonelistcontroller/search")
	public String getOrderOwnerZoneList(Model model,@RequestParam("zoneName")String zoneName,@RequestParam("shortCode")String shortCode
			,@RequestParam("remarks")String remarks, @RequestParam(value = "active", required = false) String active
			
			
											
			) {
		
        int status=1;
		
		if(active!=null)
			status=1;
		else
			status=0;
		
		model.addAttribute("orderOwnerZoneList",daoOrderOwnerZone.getOrderOwnerZoneListByCraiteria(zoneName, shortCode, remarks, status));	
		return "buyerZoneList";
	}

}
