package com.biziitech.mlfm.controller;

import java.util.ArrayList;
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
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelPackingCustom;
import com.biziitech.mlfm.dao.DaoPacking;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelFinishing;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPacking;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.repository.PackingRepository;

@Controller
public class PackingController {
	
	@Autowired
	private DaoPacking daoPacking;
	
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;

	@Autowired
	private DaoItemImp daoItemImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private PackingRepository packingRepository;
	
	
	
	
	
	@RequestMapping(path="/packing/init")
	public String PackingCreate(Model model) {
		
		
		
		List<ModelOrderOwner> modelOrderOwnerList = daoOrderOwnerImp.getAllOwner();
		model.addAttribute("modelOrderOwnerList", modelOrderOwnerList);
		
		List<ModelItem> modelItemList= daoItemImp.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		
		List<ModelOrderOwnerType> modelOrderOwnerTypeList= daoOrderOwnerTypeImp.getTypeName();
		model.addAttribute("modelOrderOwnerTypeList", modelOrderOwnerTypeList);
		
		List<ModelUser> userList= daoUserImp.getAllUSerNameInOrder();
		model.addAttribute("userList",userList);
		
		return "Packing";
		}

	
	/*
	 * 
	 * Below code for sending buyerName through buyerTypeId 
	 * 
	 * */
	
	
	
	@RequestMapping(path="/packingSearchList/buyers")
	@ResponseBody
	public List<ModelOrderOwner> findOwners(@RequestParam("buyerType")Long typeId){
		
		System.out.println("" + typeId);
		
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
	
	/*
	 * 
	 * Below code for saving packing data 
	 * 
	 * */
	
	@RequestMapping(path="/packingEntry/save", method = RequestMethod.POST)
	public String savePackingEntry(ModelPacking modelPacking, Model model,ModelProduction modelProduction,
			@RequestParam("packingProductionId") Long productionId,
			@RequestParam("packedQty") Double packedQty,
			@RequestParam("packedRemarks") String packedRemarks
			
			) {
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelPacking.setEntryTimeStamp(entryTime);
		modelPacking.setPackingDate(entryTime);
		modelPacking.setEnteredBy(1);
		modelPacking.setActiveStatus(1);
		modelProduction.setProductionId(productionId);
		modelPacking.setModelProduction(modelProduction);
		modelPacking.setPackedQty(packedQty);
		modelPacking.setPackedRemarks(packedRemarks);
		this.daoPacking.savePacking(modelPacking);
		
		//model.addAttribute("modelFinishing",modelFinishing);	
		return "redirect:/packing/init";
		
	}
	
	@ResponseBody
    @RequestMapping(path="/packingEditSave/ajax", method=RequestMethod.POST)
	    public String packingUpdate(Model model,ModelPacking modelPacking,
	    		
		@RequestParam("packingId") Long packingId,
	    	@RequestParam("packedQty") Double packedQty,
	    	@RequestParam(value = "packingActive", required = false) boolean active,
	    	@RequestParam("packedRemarks") String packedRemarks
	 ) {
		         
		
		
		    System.out.println("Packing packingId : "+packingId);
		    System.out.println("Packing packedQty : "+packedQty);
		   
			Optional<ModelPacking> modelPackingOpt = packingRepository.findById(packingId);
			modelPacking=modelPackingOpt.get();
			System.out.println("findById & getId: "+modelPackingOpt);
		
			
			
		
			
			
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelPacking.setUpdateTimeStamp(entryTime);;
		modelPacking.setUpdatedBy(null);
		modelPacking.setPackedQty(packedQty);
		modelPacking.setActive(active);
		modelPacking.setPackedRemarks(packedRemarks);
		if(modelPacking.isActive()) {
			modelPacking.setActiveStatus(1);
		}	
		else {
			
			modelPacking.setActiveStatus(0);
		}	
		
		
		
		this.daoPacking.savePacking(modelPacking);	
		
			return "redirect:/packing/init";
			
	              }
	
	
	
	
	
	
	/*
	 * below code for showing packing Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/packingQuantity/", method = RequestMethod.GET)
	 public List<ModelPackingCustom> getPackingById(@RequestParam("id")Long id, Model model){
		 System.out.println("I am in packingQty Controller & id is " +id);
			List<ModelPackingCustom> modelPackingList =  daoPacking.getPackingById(id);
			model.addAttribute("modelPackingList", modelPackingList);	 
		 return modelPackingList;		 
	 }
	
	/*
	 * below code for showing packing Qty
	 * 
	 * */
	
	
	@ResponseBody
	@RequestMapping(path = "/packedQuantity/", method = RequestMethod.GET)
	 public List<ModelPackingCustom> getPackedDateById(Model model,
			 @RequestParam("id")Long id, 
			 @RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
			 @RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
			 ){
		 System.out.println("I am in packedQty Controller & id is " +id);
			List<ModelPackingCustom> modelPackingList =  daoPacking.getPackingDateById(id,startDate,endDate);
			model.addAttribute("modelPackingList", modelPackingList);	
			System.out.println("Size: "+modelPackingList.size());
		 return modelPackingList;		 
	 }
	
	/*
	 * 
	 * Below code for pending inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/pendingOrderSearch",method = RequestMethod.GET)
	public List<ModelPackingCustom> getPendingFinishingOrderSearch(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's pendingOrderSearch method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getPendingPackingOrderDetails(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for Completed inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/completedOrderSearch",method = RequestMethod.GET)
	public List<ModelPackingCustom> getCompletedPackingOrderSearch(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's completedOrderSearch method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getCompletedPackingOrderDetails(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for pendingWO inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/pendingWOSearch",method = RequestMethod.GET)
	public List<ModelPackingCustom> getPendingPackingWOSearch(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's pendingWOSearch method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getPendingPackingWODetails(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for completedWO inquiry search 
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/completedWOSearch",method = RequestMethod.GET)
	public List<ModelPackingCustom> getCompletedPackingWOSearch(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's completedWOSearch method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getCompletedPackingWODetails(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for pending inquiry search By Packing Date
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/pendingOrderSearchByPackingDate",method = RequestMethod.GET)
	public List<ModelPackingCustom> getPendingPackingOrderSearchByPackingDate(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's pendingOrderSearch By Packing Date method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getPendingPackingOrderDetailsByPackingDate(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for Completed inquiry search By Packing Date
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/completedOrderSearchByPackingDate",method = RequestMethod.GET)
	public List<ModelPackingCustom> getCompletedPackingOrderSearchByPackingDate(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's completedOrderSearch By Packing Date method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getCompletedPackingOrderDetailsByPackingDate(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	/*
	 * 
	 * Below code for pendingWO inquiry search By Packing Date
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/pendingWOSearchByPackingDate",method = RequestMethod.GET)
	public List<ModelPackingCustom> getPendingPackingWOSearchByPackingDate(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in PackingController's pendingWOSearch method By Packing Date");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getPendingPackingWODetailsByPackingDate(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
	
	/*
	 * 
	 * Below code for completedWO inquiry search By Packing Date
	 * 
	 * */
	
	@ResponseBody
	@RequestMapping(path="/packingController/completedWOSearchByPackingDate",method = RequestMethod.GET)
	public List<ModelPackingCustom> getCompletedPackingWOSearchByPackingDate(Model model,
				@RequestParam("buyerTypeId")Long buyerTypeId,
				@RequestParam("buyerId")Long buyerId,
				@RequestParam("inquiryId")Long inquiryId , 				 
				@RequestParam("packedById")Long packedById,
				@RequestParam("startDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date startDate,
				@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate,
				@RequestParam("itemId")Long itemId
				) {

		System.out.println("I am in packingController's completedWOSearch By Packing Date method");
	
		try {
			
		System.out.println("BuyerTypeId: "+buyerTypeId+"\n InquiryId :" + inquiryId + "\n BuyerId: "+ buyerId +"\n packedById: " + packedById + "\n startDate " + startDate + "\n endDate " + endDate+ "\n itemId: "+itemId);
		List<ModelPackingCustom> modelPackingCustomList = new ArrayList<ModelPackingCustom>();	
		modelPackingCustomList=daoPacking.getCompletedPackingWODetailsByPackingDate(buyerTypeId,buyerId,inquiryId,packedById,startDate,endDate,itemId);
		System.out.println("Size:"+modelPackingCustomList.size());
		 
		return modelPackingCustomList;
		}		
		catch(Exception e) {			
		 e.printStackTrace();
		 System.out.println("error");
			return null;
		}
	
	}
	
}
