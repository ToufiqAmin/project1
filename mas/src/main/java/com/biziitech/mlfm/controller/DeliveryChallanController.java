package com.biziitech.mlfm.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelDeliverChallanCustom;
import com.biziitech.mlfm.custom.model.ModelGatePassCustom;
import com.biziitech.mlfm.custom.model.ModelPIReceiveCustom;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.daoimpl.DaoDeliveryChallanImp;
import com.biziitech.mlfm.daoimpl.DaoGatePassImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelGatePassMst;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.repository.DeliveryChallanRepository;




@Controller
public class DeliveryChallanController {
	 
	@Autowired
	private DaoDeliveryChallanImp daoDeliveryChallanImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoownerType;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	
	@Autowired
	private DeliveryChallanRepository deliverChallanRepository;
  
	@RequestMapping(path="/deliveryChallanController/init")
	public String deliveryChallan(Model model) {
		
		List<ModelOrderOwnerType> ownerTypeList= daoownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
       
        List<ModelUser> userList= user.getAllUSerName();
		model.addAttribute("userList",userList);	
		
		return "delivery_challan";

   }
	
	
	@RequestMapping(path="delivery/challan/inquery/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwnersByType(@RequestParam("ownerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return orderOwner.getOwnerByType(typeId);
	}
	
	@ResponseBody
    @RequestMapping(value = "/challan/inquiry/search", method=RequestMethod.GET)
    public List<ModelWashingCustom> getChallanSearchData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("orderId")Long orderId) {
	   
 	System.out.println("ownerType :" + ownerType);
 	
 	List<ModelWashingCustom> inquiryData=daoDeliveryChallanImp.getInquirySearchData(ownerType, owner,orderId);
	   
        return inquiryData;
	}
	
	
	    @ResponseBody
	    @RequestMapping(value = "/deliveryChallanController/workOrder/search", method=RequestMethod.GET)
	    public List<ModelWashingCustom> deliveryChallanWorkOrderData(@RequestParam("id") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("workOrderId") Long workOrderId,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,@RequestParam("pOId") Long pOId) {
		   
  	   System.out.println("ownerType :" + ownerType);
  	
  	   List<ModelWashingCustom> workOrderData=daoDeliveryChallanImp.getWorkOrderSearchData(ownerType, owner, workOrderId, startDate, endDate,pOId);
		   
	        return workOrderData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(path = "/deliverchallancontroller/challan/save", method = RequestMethod.POST) 
    	public  List<ModelDeliverChallanCustom> saveDeliveryChallan(ModelDeliveryChallan challan,ModelItem modelItem,ModelUser user, ModelUOM modelUOM,ModelPOMst pOMst,@RequestParam("itemId") Long itemId,@RequestParam("uOMId") Long uOMId,@RequestParam("challanQty") double challanQty
    			
    			,@RequestParam("challanNo") String challanNo,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="challanDate")Date challanDate,@RequestParam("size") String size,@RequestParam("grade") String grade,@RequestParam("remarks") String remarks,
    			@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="deliverDate")Date deliverDate,@RequestParam("deliveredBy") Long deliveredBy,@RequestParam("description") String description,@RequestParam("pOMstId") Long pOMstId,@RequestParam("activeStatus") int activeStatus
    			) {
    	   	 
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
        
        	user.setUserId(deliveredBy);
			pOMst.setpOMstId(pOMstId);
			modelItem.setItemId(itemId);
			modelUOM.setUomId(uOMId);
			challan.setUserChallanNo(challanNo);
			challan.setChallanDate(challanDate);
			challan.setActiveStatus(activeStatus);
			challan.setChallanQty(challanQty);
			challan.setDeliverDate(deliverDate);
			challan.setDescription(description);
			challan.setRemarks(remarks);
			challan.setEntryTimestamp(entryTime);
			challan.setSize(size);
			challan.setGrade(grade);
			challan.setEnteredBy(1);
			challan.setModelUser(user);
			challan.setModelPOMst(pOMst);
			challan.setModelItem(modelItem);
			challan.setModelUOM(modelUOM);
			
			
			daoDeliveryChallanImp.saveDeliveryChallan(challan);
			
			
			Long id=challan.getDeliverChallanId();
			
			System.out.println(id);
			
			List<ModelDeliverChallanCustom> dataList=daoDeliveryChallanImp.getDeliverChallanDataById(id);
			
			
			
			
    	 return dataList;
    	
    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/deliveryChallanController/production/search/by/id", method=RequestMethod.GET)
	    public List<ModelWashingCustom> deliveryChallanWorkOrderSpecificData(@RequestParam("id") Long id
		         ) {
		   
  	   System.out.println("Production id :" + id);
  	
  	  List<ModelWashingCustom> workOrderData=daoDeliveryChallanImp.getWorkOrderSearchDataById(id);
		   
	        return workOrderData;
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/deliveryChallanController/challan/search/by/id", method=RequestMethod.GET)
	    public List<ModelDeliverChallanCustom> deliveryChallSpecificData(@RequestParam("id") Long id
		         ) {
		   
  	      System.out.println("Challan id :" + id);
  	
  	     List<ModelDeliverChallanCustom> challanData=daoDeliveryChallanImp.getDeliverChallanDataById(id);
		   
	        return challanData;
		}
	    
	    
	   
    	/*public  List<ModelDeliverChallanCustom> saveEditChallan(ModelDeliveryChallan challan,@RequestParam("challanNo") String challanNo,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="challanDate")Date challanDate
    			
    			,@RequestParam("challanQty") Double challanQty,@RequestParam("size") String size,@RequestParam("grade") String grade,@RequestParam("remarks") String remarks,@RequestParam("description") String description
    			,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="deliverDate")Date deliverDate,ModelUser user,@RequestParam("challanId") Long challanId,@RequestParam("deliveredBy") Long deliveredBy,@RequestParam("active") int active) {
                 }
                 
                 */ 
	    
	    
	    @ResponseBody
	    @RequestMapping(path = "/challan/edit/save", method = RequestMethod.POST) 
	   public  List<ModelDeliverChallanCustom> saveEditChallan(ModelDeliveryChallan challan,@RequestParam("challanNo") String challanNo
		
		,@RequestParam("challanQty") Double challanQty,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="challanDate")Date challanDate,@RequestParam("description") String description,
		@RequestParam("size") String size,@RequestParam("grade") String grade,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="deliverDate")Date deliverDate,
		@RequestParam("deliveredBy") Long deliveredBy,@RequestParam("active") int active,@RequestParam("challanId") Long  challanId,ModelUser user,@RequestParam("remarks") String remarks){
	    
        	java.util.Date dates = new java.util.Date();
			java.sql.Timestamp entryTimestamp = new java.sql.Timestamp(dates.getTime());
			
			
			
			
			user.setUserId(deliveredBy);
		    
			challan.setUserChallanNo(challanNo);			
			challan.setChallanQty(challanQty);
			challan.setChallanDate(challanDate);
			challan.setDescription(description);
		    challan.setDeliverDate(deliverDate);
			challan.setModelUser(user);
			challan.setSize(size);
			challan.setGrade(grade);
			challan.setRemarks(remarks);
			
			challan.setUpdatedBy(1);
			challan.setUpdateTimestap(entryTimestamp);
			challan.setActiveStatus(active);
			challan.setDeliverChallanId(challanId);
					
			
			daoDeliveryChallanImp.updateChallan(challan);
			
			Long id=challanId;
			
			System.out.println("updated id : " +challanId);
			
			List<ModelDeliverChallanCustom> dataList= daoDeliveryChallanImp.getDeliverChallanDataById(id);
			
			      	

    	    return dataList;
    	
    }
	
}
