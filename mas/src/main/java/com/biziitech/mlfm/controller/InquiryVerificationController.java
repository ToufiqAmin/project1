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
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoItemTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtyImageImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtyImp;
import com.biziitech.mlfm.daoimpl.DaoOrderItemQtySpecImp;
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
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelUserVerification;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.OrderRepository;
import com.biziitech.mlfm.repository.UserClusterRepository;



@Controller
public class InquiryVerificationController {
	
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
	private DaoOrderOwnerTypeImp ownerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoUserClusterImp userCluster;
	
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
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	

	@RequestMapping(path="/inquiry/Verification/{userId}")
	public String getDetailsVerify(@PathVariable Long userId,Model model) {
		
		
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
				
				
				
				 List<ModelUser> userSelect=user.checkFlag(userId);
					
					int checkFlag=userSelect.get(0).getAllInquiryFlagStatus();
					
					if(checkFlag==1) {
						
						List<ModelUser> userList= user.getAllUSerName();
						userList.get(0).getUserName();
						System.out.println("user ID and Name :" +userList.get(0).getUserName());
						model.addAttribute("userList",userList);
					}
					
					else {
						
						List<ModelUser> userList=user.getUser(userId);
						model.addAttribute("userList",userList);
					}
					
	 

		ModelUserVerification verify= new ModelUserVerification();		
		model.addAttribute("newVerify",verify);
		
		ModelOrder newOrder= new ModelOrder();
		newOrder.setActive(true);			
		model.addAttribute("order",newOrder);
		
		List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);

        
		List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
		model.addAttribute("ownerList",ownerList);
		
		
		//List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		//model.addAttribute("clusterList",clusterList);

		 
		return "inquery_verification"; 
			 
		}
	
	
	@RequestMapping(path="/verification/details/search")
	public String getDetailsVerifyWithSearch(Model model) {
		
		
		
		
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
		
		
		
		
		
		ModelOrder newOrder= new ModelOrder();
		newOrder.setActive(true);			
		model.addAttribute("order",newOrder);
		
		List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
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
		//model.addAttribute("searchList",order.getOwnerList());
		
		model.addAttribute("searchList",order.getOwnerDataList());
		return "inquery_verification"; 
	}	
	
	
	
	
	 /* @RequestMapping(path="/verification/search")
	    public String getAllOwner(Model model,@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_ID") Long inquery_ID,@RequestParam("mail_Id")String mail_Id,@RequestParam("user_inquery_no")Long user_inquery_no,@RequestParam("remarks")String remarks,@RequestParam("user")String user,@RequestParam("inquery_start_date")String inq_start,@RequestParam("inquery_end_date")String inq_end,@RequestParam("mail_start_date")String mail_start,@RequestParam("mail_end_date")String mail_end ) throws ParseException {
	    	if(inq_start!=null && inq_end!=null && mail_start!=null && mail_end!=null) {
	    		Date inq_st = new SimpleDateFormat("yyyy-MM-dd").parse(inq_start);
		    	Date inq_ed=new SimpleDateFormat("yyyy-MM-dd").parse(inq_end);
		    	Date mail_st=new SimpleDateFormat("yyyy-MM-dd").parse(mail_start);
		    	Date mail_ed=new SimpleDateFormat("yyyy-MM-dd").parse(mail_end);
	    		order.getAllOwner(initial_Buyer, ultimate_Buyer,inquery_ID , mail_Id, user_inquery_no, remarks, user,inq_st,inq_ed,mail_st,mail_ed);
	    	}
	    	
	    	
	    	  	
	    	return "redirect:/verification/details/search";
	    			
	    }
	    
	    */
	  
	  @RequestMapping(path="/verify/item/search/{id}", method=RequestMethod.GET)
	    public String getOrderItemVerify(@PathVariable("id") Long inquery_Id ) {
			
			
			this.item.getOrderItemById(inquery_Id);
			
			return "redirect:/verify/details/searchItem";
	    }
	  
	  @RequestMapping(path="/verify/details/searchItem")
		public String getDetailsVerifyWithSearchItem(Model model) {
		  
		  
		  
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
		  
		  
		  
		  
			ModelOrder newOrder= new ModelOrder();
			newOrder.setActive(true);			
			model.addAttribute("order",newOrder);
			
			List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
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
			//model.addAttribute("searchList",order.getOwnerList());
			
			model.addAttribute("searchList",order.getOwnerDataList());
			return "inquery_verification"; 
		}
	  
	     @RequestMapping(path="/verify/itemQty/search/{id}", method=RequestMethod.GET)
	     public String getOrderItemQtyVerify(@PathVariable("id") Long item_Id ) {
			
			
			this.itemQty.getOrderItemQtyById(item_Id);
			
	    	//return this.item.getOrderItemById(inquery_Id);
			return "redirect:/verify/details/searchItem/Qty";
	     }
	     
	     @RequestMapping(path="/verify/details/searchItem/Qty")
			public String getDetailsVerifyWithSearchItemQty(Model model) {
	    	 
	    	 
	    	  
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
	    	 
	    	 
	    	 
	    	 
				ModelOrder newOrder= new ModelOrder();
				newOrder.setActive(true);			
				model.addAttribute("order",newOrder);
				
				List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
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
				
				//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
				model.addAttribute("itemQtyList",itemQty.getItemQtyList());
			//	model.addAttribute("searchList",order.getOwnerList());
				model.addAttribute("searchList",order.getOwnerDataList());
				model.addAttribute("imageList",imageImp.retriveData());
				return "inquery_verification"; 
			}
	     
	     
	       @RequestMapping(path="/verify/itemSpec/search/{id}", method=RequestMethod.GET)
		    public String getItemQtySpecSearchVerify(@PathVariable("id") Long id ) {
				
				
				this.itemQtySpec.findByQtyId(id);
				
				return "redirect:/verify/details/searchItemQty/Spec";
		    }
	       
	       @RequestMapping(path="/verify/details/searchItemQty/Spec")
			public String getDetailsVerifyWithSearchItemQtySpec(Model model) {
	    	   
	    	   
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
	    	   
	    	   
				ModelOrder newOrder= new ModelOrder();
				newOrder.setActive(true);			
				model.addAttribute("order",newOrder);
				
				List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
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
				
				//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
				model.addAttribute("itemQtyList",itemQty.getItemQtyList());
				//model.addAttribute("searchList",order.getOwnerList());
				model.addAttribute("searchList",order.getOwnerDataList());
				model.addAttribute("imageList",imageImp.retriveData());
				model.addAttribute("itemSpecList",itemQtySpec.getOrderItemQtySpec());
				return "inquery_verification"; 
			}
	       
       
	        
	        @RequestMapping(path="/verify/save", method = RequestMethod.POST)

			public String saveInquery(@RequestParam("verifyRemarks")String verifyRemarks, @RequestParam(value = "verified",required=false) String verifyStatus, @RequestParam("orderId")Long id) throws ParseException {

	        	
	        	java.util.Date date = new java.util.Date();
				java.sql.Timestamp verifyTimestamp = new java.sql.Timestamp(date.getTime());
	        	
	        	ModelOrder modelOrder= new ModelOrder();
	        	modelOrder.setVerifyRemarks(verifyRemarks);
	        	
	        	ModelUser modelUser=new ModelUser();
	        	
	        	modelUser.setUserId(1L);
	        	 
	        	if(verifyStatus != null)
	        	  {
	        		modelOrder.setVerifyStatus(1);
	        	    System.out.println("checkbox is checked");
	        	  }
	        	  else
	        	  {
	        		modelOrder.setVerifyStatus(0);
	        	    System.out.println("checkbox is not checked");
	        	  }
	        	
	        	modelOrder.setModelVerified(modelUser);
	        	modelOrder.setVerifyTimestamp(verifyTimestamp);
	        	
	        	modelOrder.setOrderId(id);
	            this.order.updateVerifyOrderJdbc(modelOrder);
			
				return  "redirect:/verify/details/searchItem";
			}
	        
	        
	        
	        
	        
	        
			
	/*new search craiteria ,date: 28/02/2019, created by:sohel rana  */
	        
	        
	        
	        @RequestMapping(path="/inquiryverificationcontroller/inquiry/owner")
	    	@ResponseBody
	    	public List<ModelOrderOwner> findOwnersByType(@RequestParam("ownerType")Long typeId){
	    		
	    		System.out.println("" + typeId);
	    		
	    		return orderOwner.getOwnerByType(typeId);
	    	}
	        
	        
	        
	     /*   @RequestMapping(path="/verification/search")
		   // public String getAllOwner(Model model,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,@RequestParam("inquery_ID") Long inquery_ID,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("mail_Id")String mail_Id,@RequestParam("user_inquery_no")Long user_inquery_no,@RequestParam("remarks")String remarks,@RequestParam("user")String user,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_start_date")String inq_start,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_end_date")String inq_end,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("done_start_date")String mail_start,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("done_end_date")String mail_end ) throws ParseException {
		    	
	        public String getAllOwner(Model model,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,@RequestParam("inquery_ID") Long orderId,@RequestParam("user")Long user,@RequestParam("mail_Id")String mail,@RequestParam("user_inquery_no")Long user_inquery_no,@RequestParam("remarks")String remarks,
	        		
	        		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_start_date")Date inq_st,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inquery_end_date")Date inq_ed,@RequestParam("ownerType") Long typeId,@RequestParam(value = "active", required = false) String active,@RequestParam(value = "verifydone", required = false) String verifydone,
	        		
	        		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("done_start_date")Date done_st,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("done_end_date")Date done_ed,@RequestParam("verifiedBy")Long verifiedBy) throws ParseException {
		    	
	        	
	        	if(verifydone!=null) {
	        		System.out.println("you searched verified data");
	        		
	        		 int status;
	 	    		
	 	    		if(active!=null) {
	 	    			status=1;
	 	    		}
	 	    			
	 	    		else {
	 	    			
	 	    			status=0;
	 	    		}
	 	    		
	 	    		order.getAllVerifiedOwnerBySearch(owner, ultimateOwner, orderId, user, mail, user_inquery_no, remarks, inq_st, inq_ed, typeId, status, done_st, done_ed,verifiedBy);
	 	    		
	 	    		
	        	}
	        	
	        	
	        	else {
	        		
	        	
	        	
	        	
	           int status;
	    		
	    		if(active!=null) {
	    			status=1;
	    		}
	    			
	    		else {
	    			
	    			status=0;
	    		}
	    		  
	      
	        	order.getAllOwnerBySearch(owner,ultimateOwner,orderId,user,mail,user_inquery_no,remarks,inq_st,inq_ed,typeId,status);
	        	
	        	}
		    	  	
		    	return "redirect:/verification/details/search";
		    			
		    }    
	*/

	     //created by sohel rana on 24/04/2019
	        
	      //search inquiry data which is not verified
	        
	        @ResponseBody
		    @RequestMapping(value = "/inquiryverificationcontroller/search/inquiry", method=RequestMethod.GET)
		    public List<ModelOrder> getInquirysData(@RequestParam("ownerType") Long typeId,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
		    		@RequestParam("inquiryId") Long inquiryId,@RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
		    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,
		    		@RequestParam("mail") String mail,@RequestParam("inquiryNo") Long inquiryNo,@RequestParam("remarks") String remarks,@RequestParam("status") int status) {
			 
		    	
		    	//List<ModelOrder> SearchListData=order.getAllInquiryData(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
		    	
	        	System.out.println("user id" + user);
	        	
	        	List<ModelOrder> SearchListData=order.getAllOwnerBySearch(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
	        	
		    	System.out.println("inquiry verification list size"  + SearchListData.size());
		    	
			   
		        return SearchListData;
			}
	        
	        
	        //edit and view verify data
	        
	        @ResponseBody
		    @RequestMapping(value = "/verify/details/data/edit", method = RequestMethod.GET)
		    public List<ModelOrder> viewData(@RequestParam("inquiryId") Long id) {
			
	        	List<ModelOrder> SearchListData=order.newInquiry(id);
	        	
		        return SearchListData;
			}
	        
	        
	        
	        //created by sohel rana on 25/04/2019
	        
	      //search inquiry data which is verified
	        
	        
	        @ResponseBody
		    @RequestMapping(value = "/inquiryverificationcontroller/search/inquiry/verifydone", method=RequestMethod.GET)
		    public List<ModelOrder> getVerifyDoneData(@RequestParam("ownerType") Long typeId,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
		    		@RequestParam("inquiryId") Long inquiryId,@RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
		    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,
		    		@RequestParam("mail") String mail,@RequestParam("inquiryNo") Long inquiryNo,@RequestParam("remarks") String remarks,@RequestParam("status") int status,
		    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("doneStartDate")Date done_st,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("doneEndDate")Date done_ed,@RequestParam("verifiedBy")Long verifiedBy) {
			 
		    	
		    
	        	System.out.println("verifer user i :d" + verifiedBy);
	        	
	        	List<ModelOrder> SearchListData=order.getAllVerifiedInquiryData(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status, done_st, done_ed, verifiedBy);
	        			
	        	
		    	System.out.println("inquiry verification verify done list size"  + SearchListData.size());
		    	
			   
		        return SearchListData;
			}
	        
	        
	        //created by sohel rana on 25/04/2019
	        
	        @ResponseBody
		    @RequestMapping(value = "/verify/edit/data/save", method = RequestMethod.POST)
		    public List<ModelOrder> saveVerifyData(@RequestParam("inquiryId") Long inquiryId,@RequestParam("verifyRemarks")String verifyRemarks,@RequestParam("status") int status) {
			
	        	//List<ModelOrder> SearchListData=order.newInquiry(id);
	        	
	        	
	        	java.util.Date date = new java.util.Date();
				java.sql.Timestamp verifyTimestamp = new java.sql.Timestamp(date.getTime());
	        	
	        	System.out.println("active status :" + status);
	        	
	        	Long verifiedBy=systemUserId;
	        	
	        	System.out.println("verified By :" + systemUserId);
	        	
	        	ModelOrder modelOrder= new ModelOrder();
	        	
	        	modelOrder.setVerifyRemarks(verifyRemarks);
	        	
	        	ModelUser modelUser=new ModelUser();
	        	
	        	modelUser.setUserId(verifiedBy);
	        	
	        	modelOrder.setVerifyStatus(status);
	        	
	        	modelOrder.setModelVerified(modelUser);
	        	modelOrder.setVerifyTimestamp(verifyTimestamp);
	        	
	        	modelOrder.setUpdatedBy(modelUser);
	        	modelOrder.setUpdateTimestap(verifyTimestamp);
	        	
	        	modelOrder.setOrderId(inquiryId);
	            this.order.updateVerifyOrderJdbc(modelOrder);
	        	
	           	
	            List<ModelOrder> SearchListData=order.newInquiry(inquiryId);
	        	
		        return SearchListData;
	        	
	        	//return null;
			}
	        
	        
	        /* search item data with ajax by inquiry id 
			 */
	        
	        @ResponseBody
		    @RequestMapping(value = "/inquiryverificationcontroller/search/item/byorderid", method=RequestMethod.GET)
		    public List<ModelOrderItem> getItemData(@RequestParam("orderId") Long id) {
			   	
				
		    	System.out.println("inquiryId :" + id);
		    	
		    	//List<ModelOrderItem> SearchListData=orderItemRepository.findItemDetails(id);
		    	
		    	List<ModelOrderItem> SearchListData=item.getOrderItemDetails(id);
		    	
		    	System.out.println("item list size"  + SearchListData.size());
		    	
			   
		        return SearchListData;
		    	
			}
	        
	        
	        /* search qty data with ajax by itemOrder id 
			 */
			
			
			@ResponseBody
		    @RequestMapping(value = "/inquiryverificationcontroller/search/qty/byitemorderid", method=RequestMethod.GET)
		    public List<ModelInquiryList> getQtyData(@RequestParam("itemOrderId") Long id) {
			   	
				
		    	System.out.println("itemOrderId :" + id);
		    	
		    	//List<ModelOrderItemQty> SearchListData=orderItemQtyRepository.findItemQtyDetails(id);
		    	
		    	//List<ModelOrderItemQty> SearchListData=itemQty.getQuantityDataByOrderItemId(id);
		    	
		    	List<ModelInquiryList>SearchListData=itemQty.getOrderItemQtyDetailsByOrderItemId(id);
		    	
		    	System.out.println("qty list size"  + SearchListData.size());
		    	
			   
		        return SearchListData;
		    	
			}
			
			
			/* search specification data with ajax by itemOrderQty id 
			 */
			
			
			@ResponseBody
		    @RequestMapping(value = "/inquiryverificationcontroller/search/specification/orderItemQtyId", method=RequestMethod.GET)
		    public List<ModelOrderItemQtySpec> getSpecificationData(@RequestParam("itemOrderQtyId") Long id) {
			   	
				
		    	System.out.println("itemOrderQtyId :" + id);
		    	
		    	//List<ModelOrderItemQtySpec> SearchListData=orderItemQtySpecRepository.findItemSpecDetails(id);
		    	
		    	
		    	List<ModelOrderItemQtySpec> SearchListData=itemQtySpec.findAllSpecByOrderItemQtyId(id);
		    	System.out.println("qty specification list size"  + SearchListData.size());
		    	
			   
		        return SearchListData;
		    	
			}
	        
}
