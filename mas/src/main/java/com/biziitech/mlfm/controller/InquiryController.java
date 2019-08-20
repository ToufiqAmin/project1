package com.biziitech.mlfm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelOwnerType;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelWashingCustom;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUserClusterImp;
import com.biziitech.mlfm.model.ModelCluster;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.UOMRepository;
import com.biziitech.mlfm.repository.UserRepository;

@Controller

public class InquiryController {  //Controller for Intial inquery page. inquery_table.html
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UOMRepository uomRepository;
	
	@Autowired
	private OrderOwnerRepository ownerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DaoUserImp daoUser;
	
	@Autowired
	private DaoOrderImp order;
	
	@Autowired
	private DaoOrderImp daoOrder;
	
	@Autowired
	private DaoUserClusterImp userCluster;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoUserClusterImp daoUserCluster;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Autowired
	private DaoOrderOwnerTypeImp ownerType;
	
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;
	
	@Autowired
	private DaoUserImp user;
	
	private SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(path="/owners/orders/{userId}")
	public String getOwnerOrders(@PathVariable Long userId,Model model) {
		
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
			
		
		ModelOrder modelOrder= new ModelOrder();
		model.addAttribute("modelOrder",modelOrder);
		
		//model.addAttribute("userList",userRepository.findAll());
		model.addAttribute("ownerList",orderOwner.getAllOwner());
		
		model.addAttribute("uomList",uomRepository.findUOM());
		
		List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        String msg=" ";
   	 	model.addAttribute("message",msg );
        
        
		
		return "inquiry_worklist";
	}
	
	@RequestMapping(path="/owners/")
	public String getOrderOwner(Model model,@RequestParam("owner_name")String OwnerName,@RequestParam("user")String user) {
		Query query=entityManager.createNativeQuery("?");
		List <Object[]> ownerList=query.setParameter(1, 0).getResultList();
		model.addAttribute("ownerList", ownerRepository.findAll());
		model.addAttribute("userList",userRepository.findAll());
		return "inquiry_worklist";
	}
	
	
	@RequestMapping(path="/userList")
	public String getUserList(Model model) {
		List<ModelUser> userList=daoUser.getAllUSerName();
		model.addAttribute("userList", userList);
		return "inquiry_worklist";
	}
	
	
	@RequestMapping(path="/user/clusterList")
	@ResponseBody
	public List<ModelUserCluster> getUserClusterList( @RequestParam("user") Long userId) {
		System.out.println("user id" + userId);
		List<ModelUserCluster> resultList=daoUserCluster.getClusterByUser(userId);
		return resultList;
	}
	
	/*
	 * url /inquirycontroller/search searches inquiry data based on some parameters.
	 * 
	 */
	
	//@ResponseBody
	@RequestMapping(path="/inquirycontroller/search") 
		public String getInquirySearch(Model model,  @RequestParam("initialBuyer")String initialBuyer, 
			@RequestParam("ultimateBuyer")String ultimateBuyer, 
			@RequestParam("user")String user,
			@RequestParam("stDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date stDate,
			@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
			) {
		
		try {
			
			System.out.println("user id form" + user);
			System.out.println("initial buyer :" + initialBuyer + " ultimate buyer"+ ultimateBuyer +" user " + user.trim().length() + " st date " + stDate + " end date " + endDate);
		
		if (stDate !=null && endDate!=null) {
		System.out.println(" user id " + user + " inq. start date " + sdf.format(stDate) + " inq. end date " + endDate);	
		}
		
		//List<ModelOrder> orderListOnCriteria= daoOrder.getOrderData(initialBuyer, userId,inqStartDate, inqEndDate);
		List<ModelOrder> orderListOnCriteria= daoOrder.getOrderData(initialBuyer,ultimateBuyer, user,stDate, endDate);
		
		
		
		//System.out.println(" user id " + userId + " inq. start date " + inqStartDate + " inq. end date " + inqEndDate);
		System.out.println("count " + orderListOnCriteria.size());
		model.addAttribute("orderListOnCriteria", orderListOnCriteria);
		
		// to show user list
        //List<ModelUser> userList=daoUser.getAllUSerName();
		
		model.addAttribute("userList", daoUser.getAllUSerName());
		
		return "inquiry_worklist";
		}
		
		catch(Exception e) {
			//model.addAttribute("userList", daoUser.getAllUSerName());
			//return "inquiry_worklist";
		 e.printStackTrace();
			return "inquiry_worklist";
		}
		
		//return "inquiry_worklist";
		
	}
	
	
	
	/*   test javascript ajax */ 
	
	    @ResponseBody
	    @RequestMapping(value = "/test")

	    public String getSearchResultViaAjax(@RequestParam(value = "id") int id) {
	        System.out.println("come to ajax"+ id);
	        return "hello";

	    }
	    
	    
	    @ResponseBody
		@RequestMapping(value="/inquiry_controller/api/inquiry_search",method = RequestMethod.GET)
		
	 	  public List<ModelOrder> getInquirySearch(@RequestParam("initialBuyer")String initialBuyer, 
					@RequestParam("ultimateBuyer")String ultimateBuyer, 
					@RequestParam("user")String user,
					@RequestParam("stDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date stDate,
					@RequestParam("endDate")@DateTimeFormat(pattern="yyyy-MM-dd")Date endDate
					) {
				
			
			try {
				
			System.out.println("initial buyer :" + initialBuyer + " ultimate buyer"+ ultimateBuyer +" user " + user.trim().length() + " st date " + stDate + " end date " + endDate);
		
			//user="Aritra";
			//List<ModelOrder> orderListOnCriteria= orderRepository.findOrderOnCriteria(initialBuyer,ultimateBuyer,user, stDate, endDate);
			List<ModelOrder> orderListOnCriteria= daoOrder.getOrderData(initialBuyer,ultimateBuyer, user.trim(), stDate, endDate);
			
			
			//System.out.println("count " + orderListOnCriteria.size());
			
			
			// to show user list
	        //List<ModelUser> userList=daoUser.getAllUSerName();
			
		
			
			return orderListOnCriteria;
			}
			
			catch(Exception e) {
				//model.addAttribute("userList", daoUser.getAllUSerName());
				//return "inquiry_worklist";
			 e.printStackTrace();
			 System.out.println("error");
				return null;
			}
			
			//return "inquiry_worklist";
			
		}
	    
	  
		
		
		
		

		
	
	    
	    /*
	     * 
	     * This section create by sohel ran on 04-03-2019
	     */
	    
	    
	    @RequestMapping(path="/inquirycontroller/inquiry/owner")
		@ResponseBody
		public List<ModelOrderOwner> findOwnersByTypeId(@RequestParam("ownerType")Long typeId){
			
			System.out.println("" + typeId);
			
			return orderOwner.getOwnerByType(typeId);
		}
	    
	    @RequestMapping(path="/inquirycontroller/user/cluster")
		@ResponseBody
		public List <ModelUserCluster> findClusters(@RequestParam("user")Long userId){			
			return userCluster.getClusterByUser(userId);
		}
	    
	    
	    @RequestMapping(path="/inquirycontroller/owner/person")
		@ResponseBody
		public Optional <ModelOrderOwner> findContactPersons(@RequestParam("owner")Long ownerId){	
			
			return ownerRepository.findById(ownerId);
		}
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/inquirycontroller/inquiry/search", method=RequestMethod.GET)
	    public List<ModelInquiryList> getInquiryDetailsData(@RequestParam("ownerType") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("edate") Date endDate,@RequestParam("user") Long user,@RequestParam("active") int active) {
		   
     	System.out.println("ownerType :" + ownerType);
     	
     	List<ModelInquiryList> orderData=daoOrder.getOrderDeatailsData(ownerType, owner, ultimateOwner, startDate, endDate, user, active);
     		
     	
     	System.out.println("List size" + orderData.size());
		   
	     return orderData;
     	  
		}
	    
	    
	    @RequestMapping(path="/inquirycontroller/inquiry/save", method = RequestMethod.POST)

		public String saveInquery(ModelOrder order,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("order-date")Date orderDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("sent-date")Date sentDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("view-date")Date viewDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("delivery-date")Date deliveryDate
				) throws ParseException {
			//System.out.println("=================================here============================"+orderDate);
	    	
	    	
	    	
	    	
			if(order.getOrderId()==null) {
				
				java.util.Date date = new java.util.Date();
				java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				order.setEntryTimestamp(entryTime);
			
				
				
				order.setOrderDate(orderDate);
				
				if(sentDate==null){
					order.setMailSentDate(null);
				}
				
				else
				order.setMailSentDate(sentDate);
				
				if(viewDate==null) {
					order.setMailReceiveDate(null);
				}
				
				else
				order.setMailReceiveDate(viewDate);
				
			
				
				if(deliveryDate==null) {
					order.setExpectedDeliveryDate(null);
				}
				
				else {
					order.setExpectedDeliveryDate(deliveryDate);
				}
			
				//System.out.println("=================================here============================"+order.getsActive());
				ModelUser enteredBy= new ModelUser();
				enteredBy.setUserId(1l);
				order.setEnteredBy(enteredBy);
				this.order.saveOrder(order);
				
			}
			
			
			
			
	
			return  "redirect:/owners/save/orders";
		}
	    
	    
	    
	    @RequestMapping(path="/owners/save/orders")
		public String getOwnerOrderAfterSave(Model model) {
			
			ModelOrder modelOrder= new ModelOrder();
			model.addAttribute("modelOrder",modelOrder);
			
			model.addAttribute("userList",userRepository.findAll());
			model.addAttribute("ownerList",orderOwner.getAllOwner());
			
			
			
			List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        String msg="Successfully Saved";
       	 	model.addAttribute("message",msg );
			
			return "inquiry_worklist";
		}
	    
	    
	    @RequestMapping(path="/inquirycontroller/buyer/ultimatebuyer")
		@ResponseBody
		public List <ModelOrderOwner> findUltimateBuyer(){			
				    	
	    	return orderOwner.getAllOwner();
		}
	    
	    
	    
	    @ResponseBody
	    @RequestMapping(path="/inquirycontroller/newinquiry/save", method = RequestMethod.POST)		
    	
	    public List<ModelInquiryList> saveNewInquery(ModelOrder order,@RequestParam("ownerType") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
             @RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
            
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("sentDate") Date sentDate,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("receiveDate") Date receiveDate,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("deliveryDate") Date deliveryDate,
             @RequestParam("person") String person,@RequestParam("mail") String mail,
             @RequestParam("refid") Long refid,@RequestParam("qty") int qty,@RequestParam("remarks") String remarks,@RequestParam("active") int active,@RequestParam("uom") Long uomId
            ) {
				
	    	
	    	
	    	System.out.println("owner id" + owner);
	    	System.out.println("date is" + orderDate);
		    	
	    	System.out.println("uom id" + uomId);
	    	System.out.println("active Id:" + active);
	    	
	    	
	    	List<ModelOrder> checkData=daoOrder.checkInquiry(owner, orderDate);
	    	
	    	List<ModelInquiryList> blankList=new ArrayList<ModelInquiryList>();
	    	
	    	
	    	System.out.println("datasize :" +checkData.size());
	    	
	    	if(checkData.size()!=0) {
	    		
	    		System.out.println("This data is already saved");
	    		
	    		
	    	}
	    	
	    	
	    	else {
	    		
	    		
	    	
	    	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				    
	    	ModelOrderOwnerType modelType= new ModelOrderOwnerType();
	    	ModelOrderOwner modelOwner= new ModelOrderOwner();
	    	ModelOrderOwner modelUltimateOwner= new ModelOrderOwner();
	    	ModelUser modelUser=new ModelUser();
	    	ModelCluster modelCluster=new ModelCluster();
	    	
	    	modelCluster.setClusterId(cluster);
	    	
            ModelUOM modelUOM=new ModelUOM();
	    	
	    	modelUOM.setUomId(uomId);
	    	
	    	
	    	if(cluster==0) {
	    		order.setModelCluster(null);
	    	}
	    	
	    	else {
	    		order.setModelCluster(modelCluster);
	    	}
	    	
	    	modelType.setOrderOwnerTypeId(ownerType);
	    	modelOwner.setOrderOwnerId(owner);
	    	modelUser.setUserId(user);
	    	modelUltimateOwner.setOrderOwnerId(ultimateOwner);
	    	
	    	order.setModelOrderOwnerType(modelType);
	    	order.setModelOrderOwner(modelOwner);
	    	
	    	if(ultimateOwner==0) {
	    		
	    		order.setUltimateOwner(null);
	    	}
	    	
	    	else {
	    		order.setUltimateOwner(modelUltimateOwner);
	    	}
	    	
	    	order.setEnteredBy(modelUser);
	    	order.setOrderDate(orderDate);
	    	order.setMailSentDate(sentDate);
	    	order.setMailReceiveDate(receiveDate);
	    	if(deliveryDate==null) {
				order.setExpectedDeliveryDate(null);
			}
			
			else {
				order.setExpectedDeliveryDate(deliveryDate);
			}
	    	order.setEntryTimestamp(entryTime);
	    	
	    	order.setContactPersonName(person);
	    	order.setRefMailId(mail);
	    	
	    	order.setRefId(refid);
	    	order.setAprxOrderQty(qty);
	    	order.setOrderRemarks(remarks);
	    	order.setActiveStatus(active);
	    	
	    	if(uomId==null){
		    	   order.setModelUom(null);
		    	}
		    	else {
		    		order.setModelUom(modelUOM);
		    	}
	    	    	
	    	daoOrder.saveNewOrder(order);
	    	
	    	Long inquiryId=order.getOrderId();
	    	
	    	System.out.println("Saved Inquiry Id :" + inquiryId);
	    	
	    	List<ModelInquiryList> data=daoOrder.getNewOrderData(inquiryId);
	    		
	    	return data;
	    	
	    	}
	    	
	    	
	    	return blankList;
		}
	    
	    
	    //created by sohel rana on 18/04/2019
	    
	    @ResponseBody
	    @RequestMapping(value = "/inquirycontroller/audit/view/byid", method=RequestMethod.GET)
	    public List<ModelOrder> getSpecificInquiryDataForAudit(@RequestParam("inquiryId") Long id) {
		   	
			
	    	System.out.println("inquiryId :" + id);
	    	
	    	List<ModelOrder> SearchListData=this.order.newInquiry(id);
	    	
	    	System.out.println("audit view size :" +SearchListData.size());
	    	
	    	
	        return SearchListData;
	    	
	    }
	    
}
