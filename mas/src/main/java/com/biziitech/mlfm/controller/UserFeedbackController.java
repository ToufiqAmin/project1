package com.biziitech.mlfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoItemTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtyImageImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtyImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtySpecImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerFeedbackImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoSpecImp;
import com.biziitech.mlfm.daoimpl.DaoUserClusterImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerFeedback;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.OrderItemQtyRepository;
import com.biziitech.mlfm.repository.OrderItemQtySpecRepository;
import com.biziitech.mlfm.repository.OrderItemRepository;
import com.biziitech.mlfm.repository.OrderOwnerFeedbackRepository;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.OrderRepository;
import com.biziitech.mlfm.repository.UserClusterRepository;

@Controller
public class UserFeedbackController {
	
	
	@Autowired
	private DaoSpecImp daoSpec;
	@Autowired
	private DaoOrderItemQtySpecImp itemQtySpec;
	@Autowired
	private DaoOrderItemQtyImp itemQty;
	@Autowired
	private DaoItemTypeImp itemType;
	@Autowired
	private DaoOrderImp order;
	@Autowired
	private DaoOrderItemImp item;
	@Autowired
	private DaoOrderOwnerTypeImp daoOwnerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoOrderOwnerFeedbackImp daoOrderOwnerFeedbackImp;
	
	@Autowired
	private OrderOwnerFeedbackRepository orderOwnerFeedbackRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderOwnerRepository ownerRepository;
	@Autowired
	private DaoOrderItemQtyImageImp imageImp;
	@Autowired
	private DaoItemImp modelItem;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderItemQtyRepository orderItemQtyRepository;
	
	
	@Autowired
	private OrderItemQtySpecRepository orderItemQtySpecRepository;
	
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@RequestMapping(path="/user/feedback/{userId}")
	public String userFeedback(@PathVariable Long userId,Model model) {
		
		
		
		
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
		
		
		
		
		   
		//ModelOrder newOrder= new ModelOrder();
		//newOrder.setActive(true);			
		//model.addAttribute("order",newOrder);
		
		ModelOrderOwnerFeedback userFeedback= new ModelOrderOwnerFeedback();
		userFeedback.setActive(true);
		model.addAttribute("userFeedback", userFeedback);
		
		List<ModelOrderOwnerType> ownerTypeList= daoOwnerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);

        
		List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
		model.addAttribute("ownerList",ownerList);
		
		List<ModelUser> userList= user.getAllUSerName();
		model.addAttribute("userList",userList);
		
		//List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		//model.addAttribute("clusterList",clusterList);
		
		//model.addAttribute("orderItemData",this.item.getItemListForOrderId());
		
		//List<ModelItem> itemList=modelItem.findItems();
       // model.addAttribute("modelItem",itemList);
        
        //List<ModelItemType>itemTypeList=itemType.findItems();
       // model.addAttribute("itemType",itemTypeList);
        
       // ModelOrderItem item= new ModelOrderItem();
		//item.setActive(true);	
		//model.addAttribute("item",item);
		
		//ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
		//orderItemQty.setActive(true);
		//model.addAttribute("orderItemQtyList",orderItemQty);
		
		//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
		//model.addAttribute("itemQtyList",itemQty.getItemQtyList());
		
		
		// modified on 22/03/2019 by sohel
		
		List<ModelOrderOwner> ultimateBuyerList= orderOwner.getUltimateBuyerList();
		System.out.println("ultimate buyer list size " + ultimateBuyerList.size());
		model.addAttribute("ultimateBuyerList",ultimateBuyerList);
		
		return "user_feedback";
	}
	
	
	
	 
	/* @RequestMapping(path="/feedback/search")
	    public String getAllOwnerFeedback(Model model,@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_ID") Long inquery_ID,@RequestParam("mail_Id")String mail_Id,@RequestParam("user_inquery_no")Long user_inquery_no,@RequestParam("remarks")String remarks,@RequestParam("user")String user,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_start_date")Date inq_st,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_end_date")Date inq_ed,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("mail_start_date")Date mail_st,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("mail_end_date")Date mail_ed ) throws ParseException {
	    	
	    	//	order.getAllOwner(initial_Buyer, ultimate_Buyer,inquery_ID , mail_Id, user_inquery_no, remarks, user,inq_st,inq_ed,mail_st,mail_ed);
	    	
	    	
	    	return "redirect:/feedback/details/search";
	    			
	    }
	    */
	
	
	/* search inquiry data with ajax 
	 */
	
	
	@ResponseBody
    @RequestMapping(value = "/userfeedbackcontroller/search/inquiry", method=RequestMethod.GET)
    public List<ModelOrder> getInquiryData(@RequestParam("ownerType") Long typeId,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
    		@RequestParam("inquiryId") Long inquiryId,@RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,
    		@RequestParam("mail") String mail,@RequestParam("inquiryNo") Long inquiryNo,@RequestParam("remarks") String remarks,@RequestParam("status") int status) {
	   
		
		//@RequestParam("specName") String specName,
		//@RequestParam("itemName") String itemName
		
    	System.out.println("inquiryId :" + inquiryId);
    	
    	List<ModelOrder> SearchListData=orderRepository.findAllOwnerDetailsByCraiteria(owner, ultimateOwner, inquiryId, user, cluster, mail, inquiryNo, remarks, startDate, endDate, typeId, status);
    	
    	System.out.println("inquiry list size"  + SearchListData.size());
    	
	   
        return SearchListData;
	}
	
	
	
	/* search item data with ajax by inquiry id 
	 */
	
	
	
	@ResponseBody
    @RequestMapping(value = "/userfeedbackcontroller/search/item/byorderid", method=RequestMethod.GET)
    public List<ModelOrderItem> getItemData(@RequestParam("orderId") Long id) {
	   	
		
    	System.out.println("inquiryId :" + id);
    	
    	List<ModelOrderItem> SearchListData=orderItemRepository.findItemDetails(id);
    	
    	System.out.println("item list size"  + SearchListData.size());
    	
	   
        return SearchListData;
    	
	}
	
	
	/* search qty data with ajax by itemOrder id 
	 */
	
	
	@ResponseBody
    @RequestMapping(value = "/userfeedbackcontroller/search/qty/byitemorderid", method=RequestMethod.GET)
    public List<ModelOrderItemQty> getQtyData(@RequestParam("itemOrderId") Long id) {
	   	
		
    	System.out.println("itemOrderId :" + id);
    	
    	List<ModelOrderItemQty> SearchListData=orderItemQtyRepository.findItemQtyDetails(id);
    	
    	System.out.println("qty list size"  + SearchListData.size());
    	
	   
        return SearchListData;
    	
	}
	
	/* search spec data with ajax by orderItemQty id 
	 */
	
	@ResponseBody
    @RequestMapping(value = "/userfeedbackcontroller/search/specification/byitemQtyid", method=RequestMethod.GET)
    public List<ModelOrderItemQtySpec> getSpecificationData(@RequestParam("itemQtyId") Long id) {
	   	
		
    	System.out.println("itemOrderId :" + id);
    	
    	List<ModelOrderItemQtySpec> SearchListData=orderItemQtySpecRepository.findItemSpecDetailsActive(id);
    	
    	System.out.println("qty spec list size"  + SearchListData.size());
    	
	   
        return SearchListData;
    	
	}
	
	
	/* search feedback data with ajax by orderItemQty id 
	 */
	
	
	@ResponseBody
    @RequestMapping(value = "/userfeedbackcontroller/search/feedback/byitemQtyid", method=RequestMethod.GET)
    public List<ModelOrderOwnerFeedback> getFeedbackData(@RequestParam("itemQtyId") Long id) {
	   	
		
    	System.out.println("itemOrderQtyId :" + id);
    	
    	List<ModelOrderOwnerFeedback> SearchListData=orderOwnerFeedbackRepository.findFeddbackById(id);
    	
    	System.out.println("feedback list size"  + SearchListData.size());
    	
	   
        return SearchListData;
    	
	}
	
	
	/*
	 
	 @RequestMapping(path="/feedback/details/search")
		public String getDetailsInqueryWithFeedbackSearch(Model model) {
			ModelOrder newOrder= new ModelOrder();
			newOrder.setActive(true);			
			model.addAttribute("order",newOrder);
			
			ModelOrderOwnerFeedback userFeedback= new ModelOrderOwnerFeedback();
			userFeedback.setActive(true);
			model.addAttribute("userFeedback", userFeedback);
			
			List<ModelOrderOwnerType> ownerTypeList= daoOwnerType.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);

	        
			List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
			model.addAttribute("ownerList",ownerList);
			
			List<ModelUser> userList= user.getAllUSerName();
			model.addAttribute("userList",userList);
			
			List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
			model.addAttribute("clusterList",clusterList);
			
			
			List<ModelItem> itemList=modelItem.findItems();
	        model.addAttribute("modelItem",itemList);
	        
	        List<ModelItemType>itemTypeList=itemType.findItems();
	        model.addAttribute("itemType",itemTypeList);
	        
	        ModelOrderItem item= new ModelOrderItem();
			item.setActive(true);	
			model.addAttribute("item",item);
			
			ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
			orderItemQty.setActive(true);
			model.addAttribute("orderItemQtyList",orderItemQty);
			
			//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
			model.addAttribute("itemQtyList",itemQty.getItemQtyList());
			model.addAttribute("searchList",order.getOwnerList());
			
			return "user_feedback";
		}
	   
	     @RequestMapping(path="/feedback/item/search/{id}", method=RequestMethod.GET)
	    public String getOrderItemWithFeedback(@PathVariable("id") Long inquery_Id ) {
			
			
			this.item.getOrderItemById(inquery_Id);
			
			return "redirect:/feedback/details/searchItem";
	    }
	 
	     
	     @RequestMapping(path="/feedback/details/searchItem")
			public String getDetailsInqueryWithFeedbackSearchItem(Model model) {
				ModelOrder newOrder= new ModelOrder();
				newOrder.setActive(true);			
				model.addAttribute("order",newOrder);
				
				ModelOrderOwnerFeedback userFeedback= new ModelOrderOwnerFeedback();
				userFeedback.setActive(true);
				model.addAttribute("userFeedback", userFeedback);
				
				List<ModelOrderOwnerType> ownerTypeList= daoOwnerType.getTypeName();			
		        model.addAttribute("ownerTypeList",ownerTypeList);

		        
				List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
				model.addAttribute("ownerList",ownerList);
				
				List<ModelUser> userList= user.getAllUSerName();
				model.addAttribute("userList",userList);
				
				List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
				model.addAttribute("clusterList",clusterList);
				
				model.addAttribute("orderItemData",this.item.getItemListForOrderId());
				
				List<ModelItem> itemList=modelItem.findItems();
		        model.addAttribute("modelItem",itemList);
		        
		        List<ModelItemType>itemTypeList=itemType.findItems();
		        model.addAttribute("itemType",itemTypeList);
		        
		        ModelOrderItem item= new ModelOrderItem();
		        item.setActiveStatus(1);
				item.setActive(true);	
				model.addAttribute("item",item);
				
				ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
				orderItemQty.setActive(true);
				model.addAttribute("orderItemQtyList",orderItemQty);
				
				//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
				model.addAttribute("itemQtyList",itemQty.getItemQtyList());
				model.addAttribute("searchList",order.getOwnerList());
				
				
				return "user_feedback";
			}
	     
	     
	        @RequestMapping(path="/feedback/itemQty/search/{id}", method=RequestMethod.GET)
		    public String getOrderItemQtyWithFeedback(@PathVariable("id") Long item_Id ) {
				
				
				this.itemQty.getOrderItemQtyById(item_Id);
				
				
		    	
				return "redirect:/feedback/details/searchItem/Qty";
		    }
	        
	        @RequestMapping(path="/feedback/details/searchItem/Qty")
			public String getDetailsInqueryWithFeedbackSearchItemQty(Model model) {
				ModelOrder newOrder= new ModelOrder();
				newOrder.setActive(true);			
				model.addAttribute("order",newOrder);
				
				ModelOrderOwnerFeedback userFeedback= new ModelOrderOwnerFeedback();
				userFeedback.setActive(true);
				model.addAttribute("userFeedback", userFeedback);
				
				List<ModelOrderOwnerType> ownerTypeList= daoOwnerType.getTypeName();			
		        model.addAttribute("ownerTypeList",ownerTypeList);

		        
				List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
				model.addAttribute("ownerList",ownerList);
				
				List<ModelUser> userList= user.getAllUSerName();
				model.addAttribute("userList",userList);
				
				List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
				model.addAttribute("clusterList",clusterList);
				
				model.addAttribute("orderItemData",this.item.getItemListForOrderId());
				
				List<ModelItem> itemList=modelItem.findItems();
		        model.addAttribute("modelItem",itemList);
		        
		        List<ModelItemType>itemTypeList=itemType.findItems();
		        model.addAttribute("itemType",itemTypeList);
		        
		        List<ModelSpec> specList=daoSpec.findAll();
		        model.addAttribute("specList",specList);
		        
		        ModelOrderItem item= new ModelOrderItem();
				item.setActive(true);	
				model.addAttribute("item",item);
				
				ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
				orderItemQty.setActive(true);
				model.addAttribute("orderItemQtyList",orderItemQty);
				
				model.addAttribute("itemQtyList",itemQty.getItemQtyList());
				model.addAttribute("searchList",order.getOwnerList());
				model.addAttribute("imageList",imageImp.retriveData());
				
				return "user_feedback"; 
			}
	 
	        @RequestMapping(path="/feedback/itemSpec/search/{id}", method=RequestMethod.GET)
		    public String getItemQtySpecWithFeedbackSearch(Model model, @PathVariable("id") Long id ) {
				
	        	
	        	daoOrderOwnerFeedbackImp.getFeedbackList(id);
				
				this.itemQtySpec.findByQtyId(id);
				
                
				
				return "redirect:/feedback/details/searchItemQty/Spec";
		    }
	        
	        @RequestMapping(path="/feedback/details/searchItemQty/Spec")
			public String getDetailsInqueryWithFeedbackSearchItemQtySpec(Model model) {
				ModelOrder newOrder= new ModelOrder();
				newOrder.setActive(true);			
				model.addAttribute("order",newOrder);
				
				ModelOrderOwnerFeedback userFeedback= new ModelOrderOwnerFeedback();
				userFeedback.setActive(true);
				model.addAttribute("userFeedback", userFeedback);
				
				List<ModelOrderOwnerType> ownerTypeList= daoOwnerType.getTypeName();			
		        model.addAttribute("ownerTypeList",ownerTypeList);

		        
				List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
				model.addAttribute("ownerList",ownerList);
				
				List<ModelUser> userList= user.getAllUSerName();
				model.addAttribute("userList",userList);
				
				List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
				model.addAttribute("clusterList",clusterList);
				
				model.addAttribute("orderItemData",this.item.getItemListForOrderId());
				
				List<ModelItem> itemList=modelItem.findItems();
		        model.addAttribute("modelItem",itemList);
		        
		        List<ModelItemType>itemTypeList=itemType.findItems();
		        model.addAttribute("itemType",itemTypeList);
		        
		        List<ModelSpec> specList=daoSpec.findAll();
		        model.addAttribute("specList",specList);
		        
		        ModelOrderItem item= new ModelOrderItem();
				item.setActive(true);	
				model.addAttribute("item",item);
				
				ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
				orderItemQty.setActive(true);
				model.addAttribute("orderItemQtyList",orderItemQty);
				
				model.addAttribute("itemQtyList",itemQty.getItemQtyList());
				model.addAttribute("searchList",order.getOwnerList());
				model.addAttribute("imageList",imageImp.retriveData());
				model.addAttribute("itemSpecList",itemQtySpec.getOrderItemQtySpec());
				model.addAttribute("feedbackList", daoOrderOwnerFeedbackImp.getFeedbackList());
				return "user_feedback";
			}
	        
	        /*save new feedback data
	         * 
	         */
	        
	        @ResponseBody
	        @RequestMapping(path = "/feedback/save", method = RequestMethod.POST) 
	        public List<ModelOrderOwnerFeedback> saveFeedback(ModelOrderOwnerFeedback feedback,ModelOrderItemQty qty,@RequestParam("qtyId") Long qtyId,@RequestParam("orderStatus") int orderStatus,@RequestParam("reason") String reason,@RequestParam("remarks") String remarks,@RequestParam("active") int active,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="feedbackdate")Date feedbackdate) {
	    	
	    	
	    			java.util.Date date = new java.util.Date();
	    			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    			
	    			qty.setOrderItemQtyId(qtyId);
	    			feedback.setModelOrderItemQty(qty);
	    			feedback.setOrderStatus(orderStatus);
	    			feedback.setCancelReason(reason);
	    			feedback.setActiveStatus(active);
	    			feedback.setRemarks(remarks);
	    			feedback.setEntryTimestamp(entryTime);
	    			feedback.setEnteredBy(1);
	    			feedback.setFeedbackDate(feedbackdate);
	    			
	    			daoOrderOwnerFeedbackImp.saveFeedback(feedback);
	    			
	    	        Long id=feedback.getOrderOwnerFeedbackId();
	    	        System.out.println("Saveing feedback id :" + id);
	    	        
	    	        List<ModelOrderOwnerFeedback> SearchListData=orderOwnerFeedbackRepository.getFeddbackById(id);
	    	    	
	    	    	System.out.println("feedback save list size"  + SearchListData.size());

	    	 return SearchListData;
	    	
	    }
	        
	        /*search feedback data by id
	         * 
	         */
	        
	        @ResponseBody
		    @RequestMapping(value = "/feedback/edit", method=RequestMethod.GET)
		    public List<ModelOrderOwnerFeedback> GetFeedbackData(@RequestParam("id") Long id) {
			   
	        	System.out.println("feddbackId :" + id);
	        	
	        	List<ModelOrderOwnerFeedback> feedbackData=orderOwnerFeedbackRepository.getFeddbackById(id);
			   
		        return feedbackData;
			}
	        
	       /*edit feedback data   
	        *  
	        */
	        
	        @ResponseBody
	        @RequestMapping(path = "/editfeedback/save", method = RequestMethod.POST) 
	        public List<ModelOrderOwnerFeedback> saveEditFeedback(ModelOrderOwnerFeedback feedback, ModelOrderItemQty qty,@RequestParam("feedbackId") Long feedbackId, @RequestParam("qtyId") Long qtyId,@RequestParam("orderStatus") int orderStatus,@RequestParam("reason") String reason,@RequestParam("remarks") String remarks,@RequestParam("active") int active,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="feedbackdate")Date feedbackdate) {
	    	   
	        	java.util.Date date = new java.util.Date();
    			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
    			
    			
    			System.out.println("edit feedback date :" +feedbackdate );
    			
    			
	        	feedback.setOrderOwnerFeedbackId(feedbackId);
	        	feedback.setOrderStatus(orderStatus);
	        	feedback.setCancelReason(reason);
	        	feedback.setActiveStatus(active);
	        	
	        	if(feedbackdate==null) {
    				System.out.println("date is null");
    				Optional<ModelOrderOwnerFeedback> exists=orderOwnerFeedbackRepository.findById(feedbackId);
    				feedback.setFeedbackDate(exists.get().getFeedbackDate());
    			}
	        	
	        	else {
	        		feedback.setFeedbackDate(feedbackdate);	
	        	}
	        	  
	        	        	
	        	feedback.setRemarks(remarks);
	        	feedback.setUpdateTimestamp(updateTime);
	        	feedback.setUpdatedBy(1L);
	        	System.out.println(updateTime);
	        	daoOrderOwnerFeedbackImp.updateFeedback(feedback);
	        	
	        	Long id=feedbackId;
    	        System.out.println(" feedback id :" + id);
    	        
    	        List<ModelOrderOwnerFeedback> SearchListData=orderOwnerFeedbackRepository.getFeddbackById(id);
    	    	
    	    	System.out.println("feedback save Edit list size"  + SearchListData.size());

	        	

	    	 return SearchListData;
	    	
	    }
	   
}
