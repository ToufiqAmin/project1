package com.biziitech.mlfm.controller;


import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.biziitech.mlfm.bg.daoimp.DaoPhoneImp;
import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.dao.DaoOrderItem;
import com.biziitech.mlfm.dao.DaoOrderOwner;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoDesignImp;
import com.biziitech.mlfm.daoimpl.DaoDesignSpecImp;
import com.biziitech.mlfm.daoimpl.DaoDessignConsumItemImp;
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
import com.biziitech.mlfm.model.ModelCluster;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderItemQtyImage;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.repository.ItemRepository;
import com.biziitech.mlfm.repository.ItemTypeRepository;
import com.biziitech.mlfm.repository.OrderItemQtyRepository;
import com.biziitech.mlfm.repository.OrderItemQtySpecRepository;
import com.biziitech.mlfm.repository.OrderItemRepository;
import com.biziitech.mlfm.repository.OrderOwnerRepository;
import com.biziitech.mlfm.repository.OrderRepository;
import com.biziitech.mlfm.repository.PhoneRepository;
import com.biziitech.mlfm.repository.SpecRepository;
import com.biziitech.mlfm.repository.UOMRepository;
import com.biziitech.mlfm.repository.UserClusterRepository;


@Controller
public class InqueryMsDetailController {
	
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
	private DaoOrderItem daoOrderItem;
	
	
	@Autowired
	private DaoDesignImp daoDesignImp;
	
	@Autowired
	private DaoDesignSpecImp daoDesignSpecImp;
	
	@Autowired
	private DaoDessignConsumItemImp daoDesignConsumItemImp;
	
	@Autowired
	private DaoUserImp user;
	
	
	@Autowired
	private DaoOrderImp daoOrderImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	

	@Autowired
	private OrderItemQtyRepository orderItemQtyRepository;
	
	@Autowired
	private OrderItemQtySpecRepository orderItemQtySpecRepository;
	
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
	
	@Autowired
	private UOMRepository uomRepository;
	
	@Autowired
	private DaoItemTypeImp modelItemType;
	
	@Autowired
	private SpecRepository specRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ItemTypeRepository itemTypeRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private PhoneRepository phoneRepository; 
	
	@Autowired
	private DaoPhoneImp daoPhoneImp;
	

	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;

	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	// For Inquiry MS detail inquery_ms_detail.html
	
		@RequestMapping(path="/inquery/details/{userId}")
		public String getDetailsInquery(@PathVariable Long userId,Model model) {
			
			
			
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
			
			
			
			
			ModelOrder newOrder= new ModelOrder();
			newOrder.setActive(true);			
			model.addAttribute("order",newOrder);
			
			ModelOrder modelOrder= new ModelOrder();
			model.addAttribute("modelOrder",modelOrder);
			
			List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);

	        
			List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
			model.addAttribute("ownerList",ownerList);
			
			List<ModelOrderOwner> ultimateBuyerList= orderOwner.getUltimateBuyerList();
			System.out.println("ultimate buyer list size " + ultimateBuyerList.size());
			model.addAttribute("ultimateBuyerList",ultimateBuyerList);
			
			
			
			//get access user //created on 10/04/2019
		
			
			List<ModelUser> userSelect=user.checkFlag(userId);
			
			int checkFlag=userSelect.get(0).getAllInquiryFlagStatus();
			
			if(checkFlag==1) {
				
				List<ModelUser> userList= user.getAllUSerName();
				model.addAttribute("userList",userList);
				
				//model.addAttribute("user",user.getUser(userId));
				
				//set default user on entered by option
				
				List<ModelUser> accessUser=user.getUser(userId);
				model.addAttribute("accessUserList",accessUser);
				
				
			}
			
			else {
				
				List<ModelUser> userList=user.getUser(userId);
				model.addAttribute("userList",userList);
			}
			
			
		
			
			List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
			model.addAttribute("clusterList",clusterList);
			
			model.addAttribute("orderItemData",this.item.getItemListForOrderId());
			
			List<ModelItem> itemList=modelItem.findItems();
	        model.addAttribute("modelItem",itemList);
	        
	        List<ModelItemType>itemTypeList=itemType.findItemCategory();
	        model.addAttribute("itemType",itemTypeList);
	        
	        ModelOrderItem item= new ModelOrderItem();
			item.setActive(true);	
			model.addAttribute("item",item);
			
			ModelOrderItemQty orderItemQty=new ModelOrderItemQty();
			orderItemQty.setActive(true);
			model.addAttribute("orderItemQtyList",orderItemQty);
			
			model.addAttribute("uomList",uomRepository.findUOM());
			
			//List<ModelOrderItemQty>itemQtyList=itemQty.findItemsQty();
			model.addAttribute("itemQtyList",itemQty.getItemQtyList());
			//model.addAttribute("searchList",order.getOwnerList());
			
			List<ModelSpec> specList=specRepository.findAllActive();
	        model.addAttribute("specList",specList);
			
			return "inquery_ms_details"; 
		}
		
		
		@RequestMapping(path="/inquery/owner")
		@ResponseBody
		public List<ModelOrderOwner> findOwner(@RequestParam("ownerType")Long typeId){
			
			
			return orderOwner.getOwnerByType(typeId);
		}
		
		@RequestMapping(path="/inquery/itemName")
		@ResponseBody
		public List<ModelItem> findItemName(@RequestParam("updatecatagory")Long itemTypeId)
		{ 
			System.out.println(itemTypeId);
			return this.modelItem.findItemByType(itemTypeId);
		}
		
		
		@RequestMapping(path="/inquery/itemSubCategory")
		@ResponseBody
		public List<ModelItemType> findItemSubCategory(@RequestParam("updatecatagory")Long itemTypeId)
		{ 
			System.out.println(itemTypeId);
			//return this.modelItem.findItemByType(itemTypeId);
			return this.modelItemType.findItemSubcategory(itemTypeId);
		}
		
		@RequestMapping(path="/inquery/itemSubSubCategory")
		@ResponseBody
		public List<ModelItemType> findItemSubSubCategory(@RequestParam("updatecatagory")Long itemTypeId)
		{ 
			System.out.println(itemTypeId);
			//return this.modelItem.findItemByType(itemTypeId);
			return this.modelItemType.findItemSubsubcategory(itemTypeId);
		}
		
		
		@RequestMapping(path="/inquery/item")
		@ResponseBody
		public List<ModelItem> findItem(@RequestParam("updatecatagory")Long itemTypeId)
		{ 
			System.out.println(itemTypeId);
			return this.modelItem.findItemByType(itemTypeId);
			
			
		}
		
		// order item save 
		
		@RequestMapping(path="/inquirymsdetailscontroller/saveItem",method=RequestMethod.POST)
		@ResponseBody
		public List<ModelInquiryList> saveItem(Model model,ModelOrderItem modelOrderItem,
				  
				
			
				  
		          @RequestParam("orderId") Long orderId,
		          @RequestParam("categoryId") Long categoryId,
		          @RequestParam("subCategoryId") Long subCategoryId,
		          @RequestParam("yarnId") Long yarnId,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
		          
		          @RequestParam("itemId") Long itemId,	
		          @RequestParam("remarks") String remarks,
		          @RequestParam("active") int active,
		          @RequestParam("enteredBy") Long enteredBy) {
			
			System.out.println("saving item data...... ");
			
			
			
            List<ModelOrderItem> checkData=item.checkOrderItem(orderId, itemId);
	    	
	    	List<ModelInquiryList> blankList=new ArrayList<ModelInquiryList>();
	    	
	    	
	    	System.out.println("datasize :" +checkData.size());
	    	
	    	if(checkData.size()!=0) {
	    		
	    		System.out.println("This data is already saved");
	    		
	    		
	    	}
	    	
	    	else {
	    		
	    	
			
	
			java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			    
			  
			    ModelOrder modelOrder= new ModelOrder();
			    ModelItem modelItem= new ModelItem();
			    ModelUser modelUser=new ModelUser();
			    modelUser.setUserId(enteredBy);
			   
			    modelOrder.setOrderId(orderId);
			    modelOrderItem.setModelOrder(modelOrder);
			    
			    modelItem.setItemId(itemId);
			    
			    modelOrderItem.setListModelItem(modelItem);
			    
			    modelOrderItem.setRemarks(remarks);
			    modelOrderItem.setEntryTimestamp(entryTime);
			    modelOrderItem.setModelEnteredBy(modelUser);
			    modelOrderItem.setOrderDate(orderDate);
			    modelOrderItem.setActiveStatus(active);
			    
			    daoOrderItem.saveOrderItem(modelOrderItem);
			    
			    
			    
			    Long id=modelOrderItem.getItemOrderId();
			    
			    System.out.println("orderItem id :" + id);
			 
			    List<ModelInquiryList> modelOrderItemList= new ArrayList<ModelInquiryList>();
			  			
			    modelOrderItemList=daoOrderItem.getOrderItemDetailsById(id);
			 
			    
			     System.out.println("list size  : " + modelOrderItemList.size());
			    
			    return modelOrderItemList;
			    
	    	}
			
			
			return blankList;
		}
		
		
		
		@RequestMapping(path="/inquery/user/cluster")
		@ResponseBody
		public List <ModelUserCluster> findCluster(@RequestParam("user")Long userId){			
			return userCluster.getClusterByUser(userId);
		}
		
		@RequestMapping(path="/inquery/owner/person")
		@ResponseBody
		public Optional <ModelOrderOwner> findContactPerson(@RequestParam("owner")Long ownerId){	
			
			return ownerRepository.findById(ownerId);
		}
		
	
		
		
	    @RequestMapping(value = "/inquery/details/save/image", method = RequestMethod.POST)
	    public String storeImage( @RequestParam("image") MultipartFile image){
			
			 imageImp.storeImage(image);
			
			 return "redirect:/inquery/details/searchItem/Qty";
		}
	    
	    
	    
	    @RequestMapping(value="/inquery/details/delete/image/{id}", method=RequestMethod.GET)
	    public String deleteImage(@PathVariable("id")Long id) {
	    	imageImp.deleteImage(id);
	    	return "redirect:/inquery/details/searchItem/Qty";
	    }
	    
	     	    
	    // save order item qty
	    
	    @RequestMapping(path="/inquirymsdetailscontroller/saveQty",method=RequestMethod.POST)
		@ResponseBody
		public List<ModelInquiryList> saveQty(Model model,ModelOrderItemQty modelQty,
				  
			      @RequestParam("active") int active,
			      
			      
			
				  
		          @RequestParam("qty")Double qty,
		          @RequestParam("typeId") Long typeId,
		          
		          @RequestParam("sampleTypeId") int sampleTypeId,
		         
		          @RequestParam("remarks") String remarks,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
		          
		          @RequestParam("itemOrderId") Long itemOrderId,@RequestParam("enteredBy") Long enteredBy		        
		          ) {
			
			System.out.println("saving Qtys data...... " + active);
			
	
			java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			    
		    
			    ModelOrderItem orderItem=new ModelOrderItem();
			    ModelUOM modelUOM=new ModelUOM();
			    ModelUser modelUser=new ModelUser();
			    modelUser.setUserId(enteredBy);
			    
			    orderItem.setItemOrderId(itemOrderId);
			    modelUOM.setUomId(typeId);
			    
			    modelQty.setModelOrderItem(orderItem);
			    modelQty.setModelUOM(modelUOM);
			    modelQty.setModelEnteredBy(modelUser);
			    modelQty.setEntryTimestamp(entryTime);
			    modelQty.setItemQty(qty);
			     modelQty.setActiveStatus(active);
			     modelQty.setOrderDate(orderDate);
			     modelQty.setRemarks(remarks);
			     modelQty.setSampleType(sampleTypeId);
			    
			    itemQty.saveOrderItemQty(modelQty);
			    
			    
			    Long id=modelQty.getOrderItemQtyId();
			    
			    System.out.println("orderItemQty id :" + id);
			    
			     List<ModelInquiryList> modelOrderItemQtyList= new ArrayList<ModelInquiryList>();
	  			
				 modelOrderItemQtyList=itemQty.getOrderItemQtyDetailsById(id);
			    
			    
			    
			    
			    
			
			
		return modelOrderItemQtyList;
		}
	    
	    
	    
	    /*created by sohel rana on 24/03/2019
	     * 
	     */
	    
	    
	    /* search inquiry data with ajax 
		 */
		
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/inquiry", method=RequestMethod.GET)
	    public List<ModelOrder> getInquirysData(@RequestParam("ownerType") Long typeId,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
	    		@RequestParam("inquiryId") Long inquiryId,@RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
	    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,
	    		@RequestParam("mail") String mail,@RequestParam("inquiryNo") Long inquiryNo,@RequestParam("remarks") String remarks,@RequestParam("status") int status) {
		 
	    	
	    	//List<ModelOrder> SearchListData=orderRepository.findAllOwnerDetailsByCraiterias(typeId,owner,ultimateOwner,inquiryId,user,startDate,endDate,status);
	    	// getAllInquiryData searches all inquiry data based on search parameters at initial.
	    	List<ModelOrder> SearchListData=order.getAllInquiryData(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
	    	
	    	System.out.println("inquiry ms details list size"  + SearchListData.size());
	    	
		   
	        return SearchListData;
		}
		
	    
	    /*save new inquiry data.....
	     * 
	     */
	    
		@ResponseBody
	    @RequestMapping(path="/inquirymsdetailscontroller/newinquiry/save", method = RequestMethod.POST)		
    	
	    public List<ModelInquiryList> saveNewInquery(ModelOrder order,@RequestParam("ownerType") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
             @RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
            
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("sentDate") Date sentDate,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("receiveDate") Date receiveDate,
             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("deliveryDate") Date deliveryDate,
             @RequestParam("person") String person,@RequestParam("mail") String mail,
             @RequestParam("refid") Long refid,@RequestParam("qty") int qty,@RequestParam("remarks") String remarks,@RequestParam("active") int active
            ,@RequestParam("uom") Long uomId) {
				
		    	
	    	
			System.out.println("owner id" + owner);
	    	System.out.println("date is" + orderDate);
		    	
	    	System.out.println("uom id" + uomId);
	    	System.out.println("active Id:" + active);
	    	
	    	
	    	List<ModelOrder> checkData=daoOrderImp.checkInquiry(owner, orderDate);
	    	
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
	    	
	    	ModelUOM modelUOM=new ModelUOM();
	    	
	    	modelUOM.setUomId(uomId);
	    	
	    	modelCluster.setClusterId(cluster);
	    	
	    	
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
	    	
	    	    	
	    	daoOrderImp.saveNewOrder(order);
	    	
	    	Long inquiryId=order.getOrderId();
	    	
	    	System.out.println("Saved Inquiry Id :" + inquiryId);
	    	
	    	//List<ModelOrder> data=daoOrderImp.newInquiry(inquiryId);
	    	
	    	List<ModelInquiryList> data=daoOrderImp.getNewOrderData(inquiryId);
	    	
	    	System.out.print("data size " + data.size());
	    	
	    	return data;
	    	
	    	
	    	}
	    	
	    	return blankList;
		}
		
		
		/* search item data with ajax by inquiry id 
		 */
		
		
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/item/byorderid", method=RequestMethod.GET)
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
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/qty/byitemorderid", method=RequestMethod.GET)
	    public List<ModelInquiryList> getQtyData(@RequestParam("itemOrderId") Long id) {
		   	
			
	    	System.out.println("itemOrderId :" + id);
	    	
	    	//List<ModelOrderItemQty> SearchListData=orderItemQtyRepository.findItemQtyDetails(id);
	    	
	    	//List<ModelOrderItemQty> SearchListData=itemQty.getQuantityDataByOrderItemId(id);
	    	
	    	List<ModelInquiryList>SearchListData=itemQty.getOrderItemQtyDetailsByOrderItemId(id);
	    	
	    	System.out.println("qty list size"  + SearchListData.size());
	    	
		   
	        return SearchListData;
	    	
		}
		
		
		
		/*created by sohel rana on 25/03/2019
		 * specification save 
		 */
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/specification/save", method=RequestMethod.POST)
	    public List<ModelWOChdListCustom> saveSpecificationData(@RequestParam("qtyId") Long qtyId,@RequestParam("specId") Long specId,@RequestParam("specValue") String specValue,@RequestParam("remarks") String remarks,@RequestParam("enteredBy") Long enteredBy) {
		   	
			
	    	System.out.println("itemOrderId :" + qtyId);
	    	
	    	
	    	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    	
	    	ModelOrderItemQtySpec modelOrderItemQtySpec=new ModelOrderItemQtySpec();
	    	
	    	ModelSpec modelSpec=new ModelSpec();
	    	ModelOrderItemQty modelOrderItemQty=new ModelOrderItemQty();
	    	
	    	ModelUser modelUser=new ModelUser();
	    	modelUser.setUserId(enteredBy);
	    	
	    	modelSpec.setSpecId(specId);
	    	modelOrderItemQty.setOrderItemQtyId(qtyId);
	    	
	    	modelOrderItemQtySpec.setModelOrderItemQty(modelOrderItemQty);
	    	modelOrderItemQtySpec.setModelSpec(modelSpec);
	    	modelOrderItemQtySpec.setActiveStatus(1);
	    	modelOrderItemQtySpec.setModelEnteredBy(modelUser);
	    	modelOrderItemQtySpec.setSpecValue(specValue);
	    	modelOrderItemQtySpec.setRemarks(remarks);
	    	modelOrderItemQtySpec.setEntryTimestamp(entryTime);
	    		    	
	    	
	    	itemQtySpec.saveNewSpecification(modelOrderItemQtySpec);
	    	
	    	
	    	Long id=modelOrderItemQtySpec.getOrderItemQtySpecId();
	    	
	    	//List<ModelOrderItemQtySpec> data=orderItemQtySpecRepository.findSpecification(id);
	    	
	    	List<ModelWOChdListCustom> data=itemQtySpec.findSpecificationById(id);
	    	
	    	System.out.println("qty list size"  + data.size());
	    	
	    	
	    	
		   
	        return data;
	    	
	    	
		}
		
		
		
		/* search specification data with ajax by itemOrderQty id 
		 */
		
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/specification/orderItemQtyId", method=RequestMethod.GET)
	    public List<ModelOrderItemQtySpec> getSpecificationData(@RequestParam("itemOrderQtyId") Long id) {
		   	
			
	    	System.out.println("itemOrderQtyId :" + id);
	    	
	    	//List<ModelOrderItemQtySpec> SearchListData=orderItemQtySpecRepository.findItemSpecDetails(id);
	    	
	    	
	    	List<ModelOrderItemQtySpec> SearchListData=itemQtySpec.findAllSpecByOrderItemQtyId(id);
	    	System.out.println("qty specification list size"  + SearchListData.size());
	    	
		   
	        return SearchListData;
	    	
		}
		
		
		/*get specific inquiry data by inquiry id
		 
		* 
		 */
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/specific/inquiry/byid", method=RequestMethod.GET)
	    public List<ModelOrder> getSpecificInquiryData(@RequestParam("inquiryId") Long id) {
		   	
			
	    	System.out.println("inquiryId :" + id);
	    	
	    	List<ModelOrder> SearchListData=this.order.newInquiry(id);
	    	
	    	System.out.println("inquiry  size :"  + SearchListData.size());
	    	System.out.println(SearchListData.get(0).getActiveStatus());
	    	System.out.println(SearchListData.get(0).getOrderId());
	    	
		   
	        return SearchListData;
	    	
		}
		
		
		
		/*created by sohel rana on 27/03/2019
		 * 
		 */
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/edit/item/byitemorderid", method=RequestMethod.GET)
	    public List<ModelOrderItem> getOrderItemDataForEdit(@RequestParam("itemOrderId") Long id) {
		   	
			
	    	System.out.println("itemOrderId :" + id);
	    	
	    	List<ModelOrderItem> SearchListData=orderItemRepository.findOrderItem(id);
	    	
	    	//List<ModelOrderItem> SearchListData=item.getOrderItemDetails(id);
	    	System.out.println("order item size :"  + SearchListData.size());
	    	
		   
	        return SearchListData;
	    	
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/inquirymsdetailscontroller/get/itemtypeList/bytypeid", method=RequestMethod.GET)
	    public List<ModelItemType> getSUbCategoryForEdit(Model model,@RequestParam("subCategory") Long id) {
		   	
			
	    	System.out.println("typeId :" + id);
	    	
	    	
	    	
	    	
	    	List<ModelItemType>itemTypeList=itemTypeRepository.findItemSubcategoryForEdit(id);
	        model.addAttribute("subCategoryDataList",itemTypeList);
	    	
	    	System.out.println("type size :"  + itemTypeList.size());
	    	
	    	
	    	
		   
	        return itemTypeList;
	    	
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/inquirymsdetailscontroller/get/itemSubcategoryList", method=RequestMethod.GET)
	    public List<ModelItemType> getSubCategoryForEdit(Model model,@RequestParam("subCategory") Long id) {
		   	
			
	    	System.out.println("typeId :" + id);
	    	
	    	Optional<ModelItemType>modelItemCategory=itemTypeRepository.findById(id);
	    	Long itemCategoryId=modelItemCategory.get().getParentItemTypeId();
	    	
	    	List<ModelItemType>itemSubcatetgoryList=itemTypeRepository.findItemSubcategoryForEdit(itemCategoryId);
	        //model.addAttribute("subCategoryDataList",itemSubcatetgoryList);
	    	
	    	System.out.println("type size :"  + itemSubcatetgoryList.size());
	    	
	    	
	    	
		   
	        return itemSubcatetgoryList;
	    	
		}
		
		/*created by sohel rana on 28/03/2019
		 * 
		 */
		
		/*get qty data for edit
		 * 
		 */
		
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/edit/qty/byid", method=RequestMethod.GET)
	    public List<ModelOrderItemQty> getOrderItemQtyDataForEdit(@RequestParam("itemOrderQtyId") Long id) {
		   	
			
	    	System.out.println("itemOrderQtId :" + id);
	    	
	    	List<ModelOrderItemQty> SearchListData=itemQty.findOrderItemQtyForEdit(id);
	    	
	    	System.out.println("order item qty size :"  + SearchListData.size());
	    	
		   
	        return SearchListData;
	    	
	    	
		}
		
		
		/*created by sohel rana on 31/03/2019
		 * 
		 * Edit qty data save
		 */
		
		@RequestMapping(path="/inquirymsdetailscontroller/edit/saveQty",method=RequestMethod.POST)
		@ResponseBody
		public List<ModelInquiryList> saveEditQty(Model model,ModelOrderItemQty modelQty,
				  
			      @RequestParam("active") int active,
			      
			      
			
				  
		          @RequestParam("qty")Double qty,
		          @RequestParam("typeId") Long typeId,
		          
		          @RequestParam("sampleTypeId") int sampleTypeId,
		         
		          @RequestParam("remarks") String remarks,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
		          
		          @RequestParam("itemOrderId") Long itemOrderQtyId,@RequestParam("updatedBy") Long updatedBy	        
		          ) {
			
			System.out.println("saving edit Qtys data...... " + active);
			
	
			    java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			    
		  
			    ModelUOM modelUOM=new ModelUOM();
			    modelUOM.setUomId(typeId);
			    
			    ModelUser modelUser=new ModelUser();
			    modelUser.setUserId(updatedBy);
			 			   
			    modelQty.setModelUOM(modelUOM);
			    modelQty.setModelUpdatedBy(modelUser);
			    modelQty.setUpdateTimestap(entryTime);
			    modelQty.setItemQty(qty);
			    modelQty.setActiveStatus(active);
			    
			    if(orderDate==null) {
			    	
			    	Optional<ModelOrderItemQty>exists=orderItemQtyRepository.findById(itemOrderQtyId);
			    	modelQty.setOrderDate(exists.get().getOrderDate());
			    }
			    else {
			    	modelQty.setOrderDate(orderDate);
			    }
			    
			    
			    
			    modelQty.setRemarks(remarks);
			    modelQty.setSampleType(sampleTypeId);
			    modelQty.setOrderItemQtyId(itemOrderQtyId);
			   
			    
			  //  itemQty.saveOrderItemQty(modelQty);
			    
			    itemQty.updateOrderQtyData(modelQty);
			    
			    
			    Long id=itemOrderQtyId;
			    
			    System.out.println("orderItemQty id :" + id);
			    
			     List<ModelInquiryList> modelOrderItemQtyList= new ArrayList<ModelInquiryList>();
	  			
				// modelOrderItemQtyList=itemQty.getOrderItemQtyDetailsById(id);
				 modelOrderItemQtyList=itemQty.getOrderItemQtyDetailsById(id);
			    
                 System.out.println("qty edit list size :" + modelOrderItemQtyList.size());			
		         
                 
                 return modelOrderItemQtyList;
		}
	    
		
		/*get specification data with specification id for edit
		 * 
		 */
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/edi/specification/byid", method=RequestMethod.GET)
	    public List<ModelOrderItemQtySpec> getSpecificationDataForEdit(@RequestParam("specificationId") Long id) {
		   	
			
	    	System.out.println("itemOrderQtyId :" + id);
	    		    	
	    	List<ModelOrderItemQtySpec> SearchListData=itemQtySpec.findAllSpecification(id);
	    	System.out.println("qty edit specification list size"  + SearchListData.size());
	    	
		   
	        return SearchListData;
	    	
		}
		
		
		/*save edited specification data
		 * 
		 */
	    
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/edit/specification/save", method=RequestMethod.POST)
	    public List<ModelOrderItemQtySpec> saveSpecificationEditData(@RequestParam("specificationId") Long specificationId,@RequestParam("value") String specValue,@RequestParam("remarks") String remarks,@RequestParam("active") int active,@RequestParam("updatedBy") Long updatedBy) {
		   	
			
			System.out.println("saving edit Qtys data...... " + active);
	    	
	    	
	    	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    	
	    	ModelOrderItemQtySpec modelOrderItemQtySpec=new ModelOrderItemQtySpec();
	    	
	    	ModelUser modelUser=new ModelUser();
	    	modelUser.setUserId(updatedBy);
	   
	    	modelOrderItemQtySpec.setActiveStatus(active);
	    	modelOrderItemQtySpec.setModelUpdatedBy(modelUser);
	    	modelOrderItemQtySpec.setSpecValue(specValue);
	    	modelOrderItemQtySpec.setRemarks(remarks);
	    	modelOrderItemQtySpec.setUpdateTimestap(entryTime);
	    	modelOrderItemQtySpec.setOrderItemQtySpecId(specificationId);
	    		    	
	    	
	    	itemQtySpec.updateOrderSpecificationData(modelOrderItemQtySpec);
	    	
	    	
	    	Long id=specificationId;
	    	
	    	List<ModelOrderItemQtySpec> data=itemQtySpec.findAllSpecification(id);
	    	
	    		   
	        return data;
	    	
	    	
		}
		
		
		/*created by sohel rana on 01/04/2019
		 * 
		 */
		
		/*get subsubcatageroy list
		 * 
		 */
		
		@ResponseBody
		@RequestMapping(value = "/inquirymsdetailscontroller/get/itemSubSubcategoryList", method=RequestMethod.GET)
	    public List<ModelItemType> getSubSubCategoryForEdit(Model model,@RequestParam("subSubCategory") Long id) {
		   	
			
	    	System.out.println("subCategoryId :" + id);
	    
	    	List<ModelItemType>itemSubcatetgoryList=itemTypeRepository.findItemSubsubcategory(id);
	      
	    	
	    	System.out.println("sub sub category size :"  + itemSubcatetgoryList.size());
	    	
	    	
	    	
		   
	        return itemSubcatetgoryList;
	    	
		}
		
		
		//get itemList
		
		
		@ResponseBody
		@RequestMapping(value = "/inquirymsdetailscontroller/get/itemList", method=RequestMethod.GET)
	    public List<ModelItem> getItemForEdit(Model model,@RequestParam("parentId") Long id) {
		   	
			
	    	System.out.println("parentId :" + id);
	    
	    	List<ModelItem>itemList=itemRepository.findItemByTypeId(id);
	      
	    	
	    	System.out.println("item List size :"  + itemList.size());
	    	
		   
	        return itemList;
	    	
		}
		
		//select category form select box
		
		
		@ResponseBody
		@RequestMapping(value = "/inquirymsdetailscontroller/select/category", method=RequestMethod.GET)
	    public List<ModelItemType> selectCategoryForEdit(Model model,@RequestParam("parentId") Long id) {
		   	
			
	    	System.out.println("parentId :" + id);
	    
	    	List<ModelItemType>selcetList=itemTypeRepository.findCatagoryForEdit(id);
	      
	    	
	    	System.out.println("select data size :"  + selcetList.size());
	    	   
	        return selcetList;
	    	
		}
		
		//edit item save function
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/edit/item/save", method=RequestMethod.POST)
	    public List<ModelInquiryList> saveItemEditData(@RequestParam("itemOrderId") Long itemOrderId,@RequestParam("itemId") Long itemId,@RequestParam("remarks") String remarks,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,@RequestParam("active") int active,@RequestParam("updatedBy")Long updatedBy) {
		   	
			
			System.out.println("saving edit Item data...... " + active);
	    	
	    	
	    	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    	
	    	
	        ModelOrderItem orderItem=new ModelOrderItem();
	        ModelItem item=new ModelItem();
	        ModelUser modelUser=new ModelUser();
	        modelUser.setUserId(updatedBy);
	        
	        item.setItemId(itemId);
	        
	        orderItem.setListModelItem(item);
	        orderItem.setRemarks(remarks);
	        orderItem.setModelUpdatedBy(modelUser);
	        orderItem.setUpdateTimestap(entryTime);
	        orderItem.setActiveStatus(active);
	        
	        if(orderDate==null) {
	        	System.out.println("order date is null");
	        	Optional<ModelOrderItem> exists=orderItemRepository.findById(itemOrderId);
	        	orderItem.setOrderDate(exists.get().getOrderDate());
	        }
	        
	        else {
	        	orderItem.setOrderDate(orderDate);
	        }
	        
	        orderItem.setItemOrderId(itemOrderId);
	        
	        
	    	this.item.updateOrderItem(orderItem);
	    	
	    	
	    	Long id=itemOrderId;
		    
		    System.out.println("orderItem id :" + id);
		 
		    List<ModelInquiryList> modelOrderItemList= new ArrayList<ModelInquiryList>();
		  			
		    modelOrderItemList=daoOrderItem.getOrderItemDetailsById(id);
	    	
	    	return modelOrderItemList;
	    	
		}
		
		//find cluster
		
		
		@RequestMapping(path="/inquirymsdetailscontroller/cluster/byuserid")
		@ResponseBody
		public List<ModelUserCluster> findUserCluster(@RequestParam("userId")Long id)
		{ 
			System.out.println("user id " + id);
			
			return userClusterRepository.getClusterByUser(id);
		}
		
		
		
		//save inquiry edit data
		
				@ResponseBody
			    @RequestMapping(path="/inquirymsdetailscontroller/edit/inquiry/save", method = RequestMethod.POST)				    	
			    public List<ModelOrder> saveEditInquiryData(ModelOrder order,@RequestParam("ownerType") Long ownerType,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
		             @RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
		             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderDate") Date orderDate,
		            
		             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("sentDate") Date sentDate,
		             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("receiveDate") Date receiveDate,
		             @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("deliveryDate") Date deliveryDate,
		             @RequestParam("person") String person,@RequestParam("mail") String mail,
		             @RequestParam("refid") Long refid,@RequestParam("qty") int qty,@RequestParam("remarks") String remarks,@RequestParam("active") int active
		             ,@RequestParam("orderId") Long orderId,@RequestParam("uom") Long uomId,@RequestParam("updatedBy") Long updatedBy) {
						
				    	
			    	
			    	System.out.println("active Id:" + active);
			    	
			    	System.out.println("buyer email " + mail);
			    	
			    	java.util.Date date = new java.util.Date();
					java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
					
					
					
					ModelOrderOwnerType modelType= new ModelOrderOwnerType();
			    	ModelOrderOwner modelOwner= new ModelOrderOwner();
			    	ModelOrderOwner modelUltimateOwner= new ModelOrderOwner();
			    	ModelUser modelUser=new ModelUser();
			    	ModelCluster modelCluster=new ModelCluster();
			    	ModelUOM modelUOM=new ModelUOM();
			    	ModelUser updated=new ModelUser();			    	
			    	updated.setUserId(updatedBy);
			    	
			    	modelType.setOrderOwnerTypeId(ownerType);
			    	modelOwner.setOrderOwnerId(owner);
			    	
			    	order.setModelOrderOwnerType(modelType);
			    	order.setModelOrderOwner(modelOwner);
			    	
			    	
                    if(ultimateOwner==0) {
			    		
			    		order.setUltimateOwner(null);
			    	}
			    	
			    	else {
			    		modelUltimateOwner.setOrderOwnerId(ultimateOwner);
			    		order.setUltimateOwner(modelUltimateOwner);
			    	}
			    	
                    modelUser.setUserId(user);
                    order.setEnteredBy(modelUser);
                    
                    
                    if(cluster==0) {
			    		order.setModelCluster(null);
			    	}
			    	
			    	else {
			    		modelCluster.setClusterId(cluster);
			    		order.setModelCluster(modelCluster);
			    	}
                   
                    
                    order.setContactPersonName(person);
                    order.setRefId(refid);
                    
                    if(orderDate==null) {
			    		System.out.println("order date is null");
			        	Optional<ModelOrder> exists=orderRepository.findById(orderId);
			        	order.setOrderDate(exists.get().getOrderDate());
			        	
			    	}
			    	else {
			    	order.setOrderDate(orderDate);
			    	}
                    
                    if(sentDate==null) {
			    		System.out.println("sent date is null");
			        	Optional<ModelOrder> exists=orderRepository.findById(orderId);
			        	order.setMailSentDate(exists.get().getMailSentDate());
			    	}
			    	else {
			    		order.setMailSentDate(sentDate);
			    	}
                    
                    if(receiveDate==null) {
			    		System.out.println("receive date is null");
			        	Optional<ModelOrder> exists=orderRepository.findById(orderId);
			        	order.setMailReceiveDate(exists.get().getMailReceiveDate());
			    	}
			    	else {
			    		order.setMailReceiveDate(receiveDate);
			    	}
			    	
                    order.setAprxOrderQty(qty);
                    
                    if(uomId==null) {
                    	order.setModelUom(null);
                    }
                    
                    else {
                    	modelUOM.setUomId(uomId);
                    	order.setModelUom(modelUOM);
                    }
                    
                    if(deliveryDate==null) {
			    		System.out.println("delivery date is null");
			        	Optional<ModelOrder> exists=orderRepository.findById(orderId);
			       	    order.setExpectedDeliveryDate(exists.get().getExpectedDeliveryDate());
					}
					
					else {
						System.out.println("delivery date : " + deliveryDate);
						order.setExpectedDeliveryDate(deliveryDate);
					}
                    
                    order.setRefMailId(mail);
                    
                    
                    System.out.println("remarks :" + remarks);
                   
                    order.setOrderRemarks(remarks);
			    	
			    	order.setActiveStatus(active);
			    	
			    	order.setUpdatedBy(updated);
			    	
			    	order.setUpdateTimestap(entryTime);
			    	
			    	Optional<ModelOrder> exists=orderRepository.findById(orderId);
			    	order.setEntryTimestamp(exists.get().getEntryTimestamp());
			    	
			    	
			    	order.setOrderId(orderId);
			    	
			    	
			    	daoOrderImp.saveNewOrder(order);
			    	
			    	//daoOrderImp.updateInquiry(order);
						    	
	    	      //  List<ModelOrder> data=this.order.newInquiry(orderId);
			    	
			    	List<ModelOrder> data=this.order.newInquiry(orderId);
			    	
			    	return data;
			    	
				}
		
		//find contact person by owner 
		
		
		@ResponseBody
		@RequestMapping(path="/inquirymsdetailscontroller/owner/contactperson")		
		public List<ModelPhone> findOwnerContactPerson(@RequestParam("ownerId")Long id)
		{ 
			System.out.println("owner id " + id);
			
		//	List<ModelPhone> data=phoneRepository.findContactPerson(id);
			
			List<ModelPhone> data=daoPhoneImp.getContactPersonName(id);
			
			return data;
		}
		
		
		//saving your image on 
		
		
		//get design data by inquiry id               ///created by sohel rana on 04/04/2019
		
		
		@ResponseBody
		@RequestMapping(path="/inquirymsdetailscontroller/get/designdata/byinquiryid")		
		public List<ModelDesign> findDesignByInquiry(@RequestParam("inquiryId")Long inquiryId)
		{ 
			System.out.println("inquiryId " + inquiryId);
			
			List<ModelDesign> data=daoDesignImp.getDesignListForInquiryMs(inquiryId);
			
			System.out.println("design list size : " + data.size());
			
			return data;
			
		}
		
		//add design in orderitemqty 
		
		
		@ResponseBody
		@RequestMapping(path="/inquirymsdetailscontroller/orderitemqty/design/add")		
		public List<ModelInquiryList> findDesignByInquiry(@RequestParam("orderItemQtyId")Long orderItemQtyId,@RequestParam("designId")Long designId,@RequestParam("updatedBy")Long updatedBy)
		{ 
			System.out.println("orderItemQtyId " + orderItemQtyId);
			System.out.println("designId " + designId);
			
			
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			 ModelOrderItemQty qty=new ModelOrderItemQty();
			
			 ModelDesign design=new ModelDesign();
			 
			 ModelUser modelUser=new ModelUser();
			 modelUser.setUserId(updatedBy);
			 
			 design.setDesignId(designId);
			
			 qty.setModelUpdatedBy(modelUser);
			 qty.setUpdateTimestap(entryTime);
			 
			 qty.setModelDesign(design);
			 
			 qty.setOrderItemQtyId(orderItemQtyId);
			 
			 itemQty.addedDesignId(qty);
			
			  
			
			 List<ModelInquiryList> modelOrderItemQtyList= new ArrayList<ModelInquiryList>();
  			
		     modelOrderItemQtyList=itemQty.getOrderItemQtyDetailsById(orderItemQtyId);
		     
		     System.out.println("data size qty :" +modelOrderItemQtyList.size() );
			
			return modelOrderItemQtyList;
			
		}
		
		///get specification details by design id
		
		
		@ResponseBody
		@RequestMapping(path="/inquirymsdetailscontroller/design/specification/view")		
		public List<ModelDesignSpec> findDesignByDesignId(@RequestParam("designId")Long designId)
		{ 
			System.out.println("designId " + designId);
			
			List<ModelDesignSpec> data=daoDesignSpecImp.getSpecName(designId);
			
			System.out.println("spec  list size : " + data.size());
			
			return data;
			
		}
		
		
		//get consume item details by design id
		
		@ResponseBody
		@RequestMapping(path="/inquirymsdetailscontroller/design/consumeitem/view")		
		public List<ModelDesignConsumItem> findConsumeItemByDesignId(@RequestParam("designId")Long designId)
		{ 
			 System.out.println("designId " + designId);
			
			List<ModelDesignConsumItem> data=daoDesignConsumItemImp.getConsumItemNae(designId);
			
			System.out.println("consume item list size : " + data.size());
			
			return data;
			
		}
		
		
		
		//created by sohel rana on 08/04/2019
		
		//get design mapped inquiry data
		
		@ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/search/inquiry/mapped", method=RequestMethod.GET)
	    public List<ModelOrderItemQty> getInquiryMappedData(@RequestParam("ownerType") Long typeId,@RequestParam("owner") Long owner,@RequestParam("ultimateOwner") Long ultimateOwner,
	    		@RequestParam("inquiryId") Long inquiryId,@RequestParam("user") Long user,@RequestParam("cluster") Long cluster,
	    		@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("startDate") Date startDate,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("endDate") Date endDate,
	    		@RequestParam("mail") String mail,@RequestParam("inquiryNo") Long inquiryNo,@RequestParam("remarks") String remarks,@RequestParam("status") int status) {
		 
	    
	    	List<ModelOrderItemQty> SearchListData=itemQty.getAllInquiryMappedData(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
	    	
	    	System.out.println("inquiry ms details list size"  + SearchListData.size());
	    	
		   
	        return SearchListData;
		}
		
		
     //created by sohel rana on 18/04/2019
		
		//get audit details for order item
	    
	    @ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/audit/view/byorderitemid", method=RequestMethod.GET)
	    public List<ModelOrderItem> getOrderItemDataForAudit(@RequestParam("itemOrderId") Long id) {
		   	
			
	    	System.out.println("itemOrderId :" + id);
	    	
	    	List<ModelOrderItem> SearchListData=daoOrderItem.getAuditDetails(id);
	    	
	    	System.out.println("audit view size :" +SearchListData.size());
	    	
	    	
	        return SearchListData;
	    	
	    }
	    

		//get audit details for order item qty
	    
	    @ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/audit/view/byorderitemqtyid", method=RequestMethod.GET)
	    public List<ModelOrderItemQty> getOrderItemQtyDataForAudit(@RequestParam("itemOrderQtyId") Long id) {
		   	
			
	    	System.out.println("itemOrderQtyId :" + id);
	    	
	    	List<ModelOrderItemQty> SearchListData=itemQty.findOrderItemQtyForEdit(id);
	    	
	    	System.out.println("audit view size :" +SearchListData.size());
	    	
	    	
	        return SearchListData;
	    	
	    }
	    
    //get audit details for order item specification
	    
	    @ResponseBody
	    @RequestMapping(value = "/inquirymsdetailscontroller/audit/view/byspecificationid", method=RequestMethod.GET)
	    public List<ModelOrderItemQtySpec> getOrderItemQtySpecDataForAudit(@RequestParam("specificationId") Long id) {
		   	
			
	    	System.out.println("specificationId :" + id);
	    	
	    	List<ModelOrderItemQtySpec> SearchListData=itemQtySpec.findAllSpecification(id);
	    	
	    	System.out.println("audit view size :" +SearchListData.size());
	    	
	    	
	        return SearchListData;
	    	
	    }
		
		
}
