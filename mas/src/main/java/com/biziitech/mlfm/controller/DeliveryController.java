package com.biziitech.mlfm.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.daoimpl.DaoDeliveryChallanImp;
import com.biziitech.mlfm.daoimpl.DaoDeliveryImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelDelivery;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelUOM;


@Controller
public class DeliveryController {
	
	
	@Autowired
	private DaoOrderOwnerTypeImp daoownerType;
	
	@Autowired
	private DaoDeliveryImp daoDeliveryImp;
	
	@Autowired
	private DaoDeliveryChallanImp daoDeliveryChallanImp;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@RequestMapping(path="/deliveryController/init")
	public String delivery(Model model) {
		
		List<ModelOrderOwnerType> ownerTypeList= daoownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
       
        List<ModelUser> userList= user.getAllUSerName();
		model.addAttribute("userList",userList);
		
		
		return "delivery";
	}
	
	
	@RequestMapping(path="/delivery/inquery/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwnersDataByType(@RequestParam("ownerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return orderOwner.getOwnerByType(typeId);
	}
	
	
	    @ResponseBody
	    @RequestMapping(value = "/deliveryController/challan/search", method=RequestMethod.GET)
	    public List<ModelDeliverChallanCustom> deliveryChallanWorkOrderData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("pOId") Long pOId
	    		,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,@RequestParam("challanNo") String challanNo) {
		   
	      System.out.println("ownerType Test :" + ownerType);
	
	      List<ModelDeliverChallanCustom> searchData=daoDeliveryImp.getSearchData(ownerType, owner, pOId,challanNo,startDate,endDate);
		   
	        return searchData;
		}
	    
	
	    @RequestMapping(path = "/deliverycontroller/deliver/save", method = RequestMethod.POST) 
    	public  String saveDelivery(ModelDelivery delivery,ModelDeliveryChallan challan,ModelItem item,@RequestParam("itemId") Long itemId,@RequestParam("challanId") Long challanId,@RequestParam("challanNo") String challanNo,@RequestParam("qty") Double qty)
                   {
    	   	 
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
        
            
			challan.setDeliverChallanId(challanId);
			item.setItemId(itemId);
			
			delivery.setModelDeliveryChallan(challan);
			delivery.setModelItem(item);
			delivery.setDeliverQty(qty);
			delivery.setDeliverDate(date);
			delivery.setEntryTimestamp(entryTime);
			delivery.setEnteredBy(1);
			delivery.setActiveStatus(1);
			
			daoDeliveryImp.saveDelivery(delivery);
			
			
			
    	   return "delivery";
    	
    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/deliveryController/challan/search/by/id", method=RequestMethod.GET)
	    public List<ModelDeliverChallanCustom> deliveryChallanDataBYId(@RequestParam("id") Long id) {
		   
	
	      List<ModelDeliverChallanCustom> searchData=daoDeliveryChallanImp.getDeliverChallanDataById(id);
		   
	        return searchData;
		}

}
