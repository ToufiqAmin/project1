package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

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
import com.biziitech.mlfm.bg.model.ModelCurrency;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoOrderItemQtySpec;
import com.biziitech.mlfm.dao.DaoProductionPlan;
import com.biziitech.mlfm.dao.DaoSpec;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.dao.DaoWOItemQtySpec;
import com.biziitech.mlfm.dao.DaoWorkOrder;
import com.biziitech.mlfm.dao.DaoWorkOrderChd;
import com.biziitech.mlfm.daoimpl.DaoDesignSpecImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoSpecImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.daoimpl.DaoUserClusterImp;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserCluster;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.model.ModelWOItemQtySpec;
import com.biziitech.mlfm.model.ModelWOMst;
import com.biziitech.mlfm.repository.CurrencyRepository;
import com.biziitech.mlfm.repository.UserClusterRepository;
import com.biziitech.mlfm.repository.WOItemQtySpecRepository;


@Controller
public class WorkOrderController {
	
	
	@Autowired
	private DaoWorkOrder daoWorkOrder;
	
	@Autowired
	private DaoWorkOrderChd daoWorkOrderChd;
	
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private DaoOrderOwnerTypeImp ownerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private UserClusterRepository userClusterRepository;
	
	@Autowired
	private DaoUserClusterImp userCluster;
	
	@Autowired
	private DaoSpecImp daoSpec;
	
	@Autowired
	private DaoSpec daoSpeca;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoDesignSpecImp specItem;
	
	@Autowired
	private DaoOrderItemQtySpec daoOrderItemQtySpec;
	
	@Autowired
	private DaoWOItemQtySpec daoWOItemQtySpec;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private WOItemQtySpecRepository wOItemQtySpecRepository;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoProductionPlan daoProductionPlan;
	
	@Autowired
	private DaoUserImp user;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	
	@RequestMapping(path="/work_order/{userId}")
	public String init(@PathVariable Long userId,Model model,ModelWOMst modelWOMst) {
		
		
		
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
			
			//int checkFlag=userSelect.get(0).getCheckFlag();
			
			int checkFlag=userSelect.get(0).getAllInquiryFlagStatus();
			
			if(checkFlag==1) {
				
				
				List<ModelUser> userList=daoProductionPlan.getUserList();
				//List<ModelUser> userList= user.getAllUSerName();
				model.addAttribute("userList",userList);
				
				//model.addAttribute("user",user.getUser(userId));
				
				
			}
			
			else {
				
				List<ModelUser> userList=user.getUser(userId);
				model.addAttribute("userList",userList);
			}
			
		
	//	List<ModelUser> userList= daoUserImp.getAllUSerName();
	//	model.addAttribute("userList",userList);
		
		List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelOrderOwner> allOwnerList= daoOrderOwnerImp.getAllOwner();
		model.addAttribute("allOwnerList",allOwnerList);
        
        List<ModelOrderOwner> ownerList= orderOwner.getActiveOrderOwnerList();
    	model.addAttribute("ownerList",ownerList);
		
		List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		model.addAttribute("clusterList",clusterList);
		
		List<ModelSpec> specList=daoSpec.findAll();
        model.addAttribute("specList",specList);
        
        List<ModelUOM> uomName=daoUOMImp.getUomName();
		model.addAttribute("uomList", uomName);
		
		model.addAttribute("modelWOMst",modelWOMst);
		
		
		List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
		currencyList=currencyRepository.findCurrency();
		model.addAttribute("currencyList",currencyList);
		
		
		return "work_order";
	}
	
	@RequestMapping(path="/workordercontroller/workordermstsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelWOInquiryData> workOrderMstSave(Model model,ModelWOMst modelWOMst,ModelOrderOwner modelOrderOwner,
			//  @RequestParam("wOMstId") Long wOMstId,
	          @RequestParam("userWONo") String userWONo,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("buyerAcceptedDate") Date buyerAcceptedDate,
	          @RequestParam("orderOwnerId") Long orderOwnerId,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("activeStatus") int activeStatus,
	          @RequestParam("enteredBy") Long enteredBy) {
		
		
		 System.out.println("userWONo :" +userWONo);
		 System.out.println("buyerAcceptedDate :" +buyerAcceptedDate);
		 System.out.println("orderOwnerId :" +orderOwnerId);
		 System.out.println("remarks :" +remarks);
		 System.out.println("activeStatus :" +activeStatus);
		 
		    java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    modelWOMst.setUserWONo(userWONo);
		    modelWOMst.setwODate(buyerAcceptedDate);
		    
		    modelOrderOwner.setOrderOwnerId(orderOwnerId);
		    modelWOMst.setModelOrderOwner(modelOrderOwner);
		    modelWOMst.setRemarks(remarks);
		    modelWOMst.setActiveStatus(activeStatus);
		   
		    modelWOMst.setEntryTimestamp(entryTime);
		    
		    ModelUser modelUser = new ModelUser();
		    modelUser.setUserId(enteredBy);
		    modelWOMst.setEnteredBy(modelUser);
		    
		    
		    daoWorkOrder.saveWOMst(modelWOMst);
		    
		    System.out.println("After Save :" +modelWOMst);
		    
		    List<ModelWOInquiryData> wOMstList= new ArrayList<ModelWOInquiryData>();
		    wOMstList=daoWorkOrder.getWOMstListFindbyWOMstId(modelWOMst.getwOMstId());
		    
		    System.out.println("List :" +wOMstList);
		 
		
		return wOMstList;
	}
	
	
	@RequestMapping(path="/workordercontroller/getWOMstData",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelWOInquiryData> getWOMstData(Model model,
			@RequestParam("wOMstId") Long wOMstId){
		
		
		 System.out.println(" controller method : getWOMstData :wOMstId: " + wOMstId);
		 
		 List<ModelWOInquiryData> wOMstList= new ArrayList<ModelWOInquiryData>();
		 wOMstList = daoWorkOrder.getWOMstListFindbyWOMstId(wOMstId);
		 System.out.println(" controller : wOMstList  find by Id : " + wOMstList);
		 System.out.println(" controller : wOMstList  list size : " + wOMstList.size());
		
		return wOMstList;
	}
	
	
	@RequestMapping(path="/workordercontroller/getWOChdData",method=RequestMethod.GET)
	@ResponseBody
	public List<ModelWOInquiryData> getWOChdData(Model model,
			@RequestParam("wOChdId") Long wOChdId){
		
		
		
		 System.out.println(" controller method : getWOChdData :wOChdId: " + wOChdId);
		 
		 List<ModelWOInquiryData> wOChdList= new ArrayList<ModelWOInquiryData>();
		 wOChdList = daoWorkOrderChd.getWOChdData(wOChdId);
		 System.out.println(" controller : wOChdList  find by wOChdId : " + wOChdList);
		 System.out.println(" controller : wOChdList  list size : " + wOChdList.size());
		
		return wOChdList;
		//return null;
	}
	
	
	
	@RequestMapping(path="/workordercontroller/workordermsteditsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelWOInquiryData> workOrderMstEditSave(Model model,ModelWOMst modelWOMst,ModelOrderOwner modelOrderOwner,
			  @RequestParam("wOMstId") Long wOMstId,
	          @RequestParam("userWONo") String userWONo,
	          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("buyerAcceptedDate") Date buyerAcceptedDate,
	          @RequestParam("orderOwnerId") Long orderOwnerId,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("activeStatus") int activeStatus) {
		
		
		 Long logonUserId=systemUserId;
		
		
		System.out.println("wOMstId :" +wOMstId);
		
		 System.out.println("userWONo :" +userWONo);
		 System.out.println("buyerAcceptedDate :" +buyerAcceptedDate);
		 System.out.println("orderOwnerId :" +orderOwnerId);
		 System.out.println("remarks :" +remarks);
		 System.out.println("activeStatus :" +activeStatus);
		 
		 
		
		 
		    Optional <ModelWOMst> wOMstDataByOpt = daoWorkOrder.findWOMst(wOMstId);
		    System.out.println("wOMstDataByOpt (optional type Object) : "+wOMstDataByOpt);
		    modelWOMst= wOMstDataByOpt.get();
		    
		    System.out.println("designData (ModelDesign type Object) : "+modelWOMst);
		    
		 
		    java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    modelWOMst.setUserWONo(userWONo);
		    modelWOMst.setwODate(buyerAcceptedDate);
		    
		    modelOrderOwner.setOrderOwnerId(orderOwnerId);
		    modelWOMst.setModelOrderOwner(modelOrderOwner);
		    modelWOMst.setRemarks(remarks);
		    modelWOMst.setActiveStatus(activeStatus);
		    
		    
		    
		    modelWOMst.setUpdateTimestap(entryTime);
		    
		    ModelUser modelUser = new ModelUser();
		    modelUser.setUserId(logonUserId);
		    modelWOMst.setUpdatedBy(modelUser);
		
            daoWorkOrder.saveWOMst(modelWOMst);
		    
		    System.out.println("After Save :" +modelWOMst);
		    
		    List<ModelWOInquiryData> wOMstList= new ArrayList<ModelWOInquiryData>();
		    wOMstList=daoWorkOrder.getWOMstListFindbyWOMstId(modelWOMst.getwOMstId());
		  
		    
		   // System.out.println("entered By :" +wOMstList.get(0).getEnteredBy());
		    
		    System.out.println("List :" +wOMstList);
		 
		
		return wOMstList; 
		
		
		
		/* 
		    List <ModelWOMst> modelWOMstList=daoWorkOrder.getInUsedWOMstList();
			  
			  //System.out.println("in used Mst data Details :" + modelWOMstList.toString());
		  	System.out.println("in used Mst data size :" + modelWOMstList.size());
		  	
				
				return modelWOMstList;
		*/
	//	return null;
	}
	
	
	
	@RequestMapping(path="/work_order/from", method =RequestMethod.POST)
	public String saveWOMst(Model model,ModelWOMst modelWOMst) {
    	
		
	
    	if(modelWOMst.getwOMstId()==null )
		{
		    java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    modelWOMst.setEntryTimestamp(entryTime);
		   // modelWOMst.setEnteredBy(1);
		    daoWorkOrder.saveWOMst(modelWOMst);
		}
		else
		{
			daoWorkOrder.saveWOMst(modelWOMst);
		}
    	
    	 
		return "redirect:/work_order/from/workOrderMst/"+modelWOMst.getwOMstId();
	}
	
	//creator : sas , date : 01/12/2019
 // @ResponseBody
  @RequestMapping(path="/work_order/from/workOrderMst/{id}", method=RequestMethod.GET)
  public String getWoMstList(Model model,@PathVariable("id") Long id, ModelWOMst modelWOMst) {
  
	try {  
	  System.out.println("id :" +id);
  	//List<ModelPIInquiryList> woMstList=daoWorkOrder.getWoMstList(id);
  	//model.addAttribute("woMstList",woMstList);
  	
  	
  	//System.out.println("idd " + modelWOMst.getwOMstId() );
	
	List<ModelUser> userList= daoUserImp.getAllUSerName();
	model.addAttribute("userList",userList);
	
	List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
    model.addAttribute("ownerTypeList",ownerTypeList);
    
    List<ModelOrderOwner> ownerList= orderOwner.getActiveOrderOwnerList();
	model.addAttribute("ownerList",ownerList);
	
	List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
	model.addAttribute("clusterList",clusterList);
	

	try {
	List <ModelWOMst> modelWOMstList= new ArrayList<ModelWOMst>();
	
	Optional <ModelWOMst> wOMstDataByIdOpt=daoWorkOrder.findWOMstById(id); // optional type declaration
	
	
	modelWOMst=wOMstDataByIdOpt.get();  // optional data retrieve
	
	modelWOMstList.add(modelWOMst);
	

	System.out.println("buyer id and  name: " + modelWOMst.getModelOrderOwner().getOrderOwnerId() + " ," + modelWOMst.getModelOrderOwner().getOwnerName() );
	model.addAttribute("woMstList",modelWOMstList);
	
	}
	
	catch(NoSuchElementException e) {
	 //e.printStackTrace();
		
		System.out.println("Data not found for Id : " + modelWOMst.getwOMstId());
	}
	
 }
 
 catch (Exception e) {
	 e.printStackTrace();
	 //System.out.println("Error at URL : //work_order//from" );
	 
 }
	
	finally {
	
	return "work_order";
	
	}
	
  	
  	      				
	//	return "redirect:/work_order";
  }
  
  @ResponseBody
  @RequestMapping(path="/wOMst/inUsed", method=RequestMethod.GET)
  public List<ModelWOMst> getInUsedWOMstDetails() {
  	
	  List <ModelWOMst> modelWOMstList=daoWorkOrder.getInUsedWOMstList();
	  
	  //System.out.println("in used Mst data Details :" + modelWOMstList.toString());
  	System.out.println("in used Mst data size :" + modelWOMstList.size());
  	
		
		return modelWOMstList;
     
  }
  
	/*
	@RequestMapping(path="/work_order_entry/search")
	public String getInquiryData(Model model,ModelWOMst modelWOMst,@RequestParam("user") Long user,
			  @RequestParam("user_cluster") Long user_cluster) {
		
		List<ModelUser> userList= daoUserImp.getAllUSerName();
		model.addAttribute("userList",userList);
		
		List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelOrderOwner> ownerList= orderOwner.getAllOwnerName();
		model.addAttribute("ownerList",ownerList);
		
		//List<ModelUserCluster> clusterList= userClusterRepository.findAll();						
		//model.addAttribute("clusterList",clusterList);
		
		model.addAttribute("modelWOMst",modelWOMst);
		
        System.out.println("user " + user + "user cluster " + user_cluster);
		List<ModelWOInquiryData> inquiryDataList=new ArrayList<ModelWOInquiryData>();
		
		//inquiryDataList=	daoWorkOrder.getInquiryDataList(user, user_cluster);
		model.addAttribute("inquiryDataList",inquiryDataList);
			
	  	return"work_order";
	  	
	  			
	  }
	*/
	
	
	
	/*
	 * created by:sas
	 * Date:01/17/2019
	 * 
	 * Purpose: this method execute when checked Work Order option
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(path="/workordercontroller/getinquirydatadoneworkorder")
	public List<ModelWOInquiryData> getInquiryDataDoneWorkOrder(Model model,
			  @RequestParam("userId") Long userId,
			  @RequestParam("userClusterId") Long userClusterId,
			  @RequestParam("orderOwnerId") Long orderOwnerId,
			  @RequestParam("ultimateOwnerId") Long ultimateOwnerId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderStratDate") Date orderStratDate,
			  @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("orderEndDate") Date orderEndDate)
			
	   {
		    System.out.println(" user Id : " + userId +
		    		"\n user cluster Id : " + userClusterId+
		    		"\n orderOwnerId : " + orderOwnerId +
		    		"\n ultimateOwnerId :" + ultimateOwnerId+ 
		    		"\n order Strat Date :" + orderStratDate+ 
		    		"\n order End Date : " + orderEndDate 
		    		);
		
            List<ModelWOInquiryData> inquiryDataList=new ArrayList<ModelWOInquiryData>();
		    inquiryDataList = daoWorkOrder.getInquiryDataListDoneWO(userId, userClusterId,orderOwnerId,ultimateOwnerId,orderStratDate,orderEndDate);
		    System.out.println("WO inquiry search data size: " +inquiryDataList.size());
		return inquiryDataList;
		
	  	}
	
	/*
	 * created by:sas
	 * Date:01/17/2019
	 * 
	 * Purpose: this method execute when Unchecked Work Order option
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping(path="/workordercontroller/getinquirydatawonotdone")
	public List<ModelWOInquiryData> getInquiryDataWONotDone(Model model,
			  @RequestParam("userId") Long userId,
			  @RequestParam("userClusterId") Long userClusterId,
			  @RequestParam("orderOwnerId") Long orderOwnerId,
			  @RequestParam("ultimateOwnerId") Long ultimateOwnerId,
			  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderStratDate") Date orderStratDate,
			  @DateTimeFormat(pattern="yyyy-MM-dd") @RequestParam("orderEndDate") Date orderEndDate)
			
	{
		   System.out.println(" user Id : " + userId +
				   "\n user cluster Id : " + userClusterId+
				   "\n orderOwnerId : " + orderOwnerId +
				   "\n ultimateOwnerId :" + ultimateOwnerId+ 
				   "\n order Strat Date :" + orderStratDate+ 
				   "\n order End Date : " + orderEndDate);
		   
		   List<ModelWOInquiryData> inquiryDataList=new ArrayList<ModelWOInquiryData>();
		   inquiryDataList = daoWorkOrder.getInquiryDataListNotDoneWO(userId, userClusterId,orderOwnerId,ultimateOwnerId,orderStratDate,orderEndDate);
		  
		  
		   System.out.println("WO inquiry search data size: " +inquiryDataList.size() );
		   
	   return inquiryDataList;
	}
	
	/* Last Working
	@ResponseBody
    @RequestMapping(path="/woChd/populate", method=RequestMethod.GET)
	    public List<ModelWOChdListCustom> getWOChdList(Model model,@RequestParam("inquiryItemQtyId") Long inquiryItemQtyId,
	    		@RequestParam("wOMstId") Long wOMstId
	    		) {
    	System.out.println("Inquiry Item Qty Id :" + inquiryItemQtyId);
    	List<ModelWOChdListCustom> modelWOChdListCustomList = new ArrayList<ModelWOChdListCustom>();
    	List<ModelWOChdListCustom> wOChdListCustomList = new ArrayList<ModelWOChdListCustom>();
    	modelWOChdListCustomList = daoWorkOrder.getWOChdList(inquiryItemQtyId,wOMstId);  // need to check one or more records.
    	System.out.println("WO Chd data List size :" + modelWOChdListCustomList.size()); 
    	System.out.println("Before Save Chd List :" + modelWOChdListCustomList.toString());
    	
    	List<ModelUOMCustom> modelUOMCustomList= new ArrayList<ModelUOMCustom>(); 
    	modelUOMCustomList=daoUOMImp.getUOMListCustom();
    	
    	System.out.println("uom list size " + modelUOMCustomList.size());
    	// UOM list added in List<ModelWOChdListCustom>  modelWOChdListCustom
    	
    	for (ModelWOChdListCustom wOChd : modelWOChdListCustomList ) {
    		wOChd.setModelUOMCustomList(modelUOMCustomList);
    		wOChdListCustomList.add(wOChd);
    		
    		//save 
    		
    		List<ModelOrderItemQtySpec> modelOrderItemQtySpecList = new ArrayList<ModelOrderItemQtySpec>();
    		modelOrderItemQtySpecList=	daoOrderItemQtySpec.getOrderItemQtySpecList(inquiryItemQtyId);
    		System.out.println("Order item Qty Spec list size " + modelOrderItemQtySpecList.size());
    		
    		// oder item qty spec list loop populate
    		for (ModelOrderItemQtySpec m :modelOrderItemQtySpecList ) {
    		daoWOItemQtySpec.saveWOItemQtySpec(m);
    		
    		}
    		
    		
    	}
    	return wOChdListCustomList;
    	//return null;
	    }
	 */
	
	@ResponseBody
    @RequestMapping(path="/wOItemQtySpecEditSave/ajax", method=RequestMethod.POST)
	    public String wOItemQtySpecUpdate(Model model,ModelWOItemQtySpec modelWOItemQtySpec,ModelSpec modelSpec,ModelUOM modelUOM,
	    		@RequestParam("wOItemQtySpecId") Long wOItemQtySpecId,
	    		@RequestParam("wOSpecId") Long wOSpecId,
	    		@RequestParam("wOSpecValue") String wOSpecValue,
	    		@RequestParam("wOUOMId") Long wOUOMId) {
		         
		//if(modelWOItemQtySpec.getwOItemQtySpecId()==wOItemQtySpecId)
		//{
		
	    	Long logonUserId=systemUserId;
		
		    System.out.println("WO Item Qty Spec Id : "+wOItemQtySpecId);
		    System.out.println("WO Spec Id : "+wOSpecId);
		    System.out.println("WO Spec Value : "+wOSpecValue);
		    System.out.println("WO UOM Id : "+wOUOMId);
			Optional<ModelWOItemQtySpec> modelWOItemQtySpecOpt = wOItemQtySpecRepository.findById(wOItemQtySpecId);
			modelWOItemQtySpec=modelWOItemQtySpecOpt.get();
		
			modelSpec.setSpecId(wOSpecId);
			modelWOItemQtySpec.setModelSpecWO(modelSpec);
			modelWOItemQtySpec.setwOSpecValue(wOSpecValue);
			modelUOM.setUomId(wOUOMId);
			modelWOItemQtySpec.setModelUOMWO(modelUOM);
			
			java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		    
		    ModelUser modelUser =new ModelUser();
		    modelUser.setUserId(logonUserId);
		    
		    modelWOItemQtySpec.setUpdatedBy(modelUser);
		    modelWOItemQtySpec.setUpdateTimestap(entryTime);
			
			
			daoWOItemQtySpec.saveWOItemQtySpecNew(modelWOItemQtySpec);
//		java.util.Date date = new java.util.Date();
//		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
//			pimst.setEntryTimestamp(entryTime);
//			pimst.setEnteredBy(1);
//			daoPiMstImp.savePIMst(pimst);
		
			return "redirect:/getWOItemQtySpec/list";
			//return "work_order";
	              }
	    
	@ResponseBody
    @RequestMapping(path="/woChd/populate", method=RequestMethod.GET)
	    public List<ModelWOChdListCustom> getWOChdList(Model model,
	    		@RequestParam("inquiryItemQtyId") Long inquiryItemQtyId,
	    		@RequestParam("wOMstId") Long wOMstId
	    		) {
    	
		
		System.out.println("Inquiry Item Qty Id :" + inquiryItemQtyId);
    	List<ModelWOChdListCustom> modelWOChdListCustomList = new ArrayList<ModelWOChdListCustom>();
    	List<ModelWOChdListCustom> wOChdListCustomList = new ArrayList<ModelWOChdListCustom>();
    	
    	try {
    	
    	
    	modelWOChdListCustomList = daoWorkOrder.getWOChdList(inquiryItemQtyId,wOMstId);  // need to check one or more records.
    	// this query generate duplicate value 
    	
    	
    	
    	System.out.println("WO Chd data List size :" + modelWOChdListCustomList.size()); 
    	System.out.println("Before Save Chd List :" + modelWOChdListCustomList.toString());
    	
    	List<ModelUOMCustom> modelUOMCustomList= new ArrayList<ModelUOMCustom>(); 
    	modelUOMCustomList=daoUOMImp.getUOMListCustom();
    	
    	System.out.println("uom list size " + modelUOMCustomList.size());
    	// UOM list added in List<ModelWOChdListCustom>  modelWOChdListCustom
    	
    	List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
		currencyList=currencyRepository.findCurrency();
		
		System.out.println("Currency List Size " + currencyList.size());
    	
    	for (ModelWOChdListCustom wOChd : modelWOChdListCustomList ) {
    		wOChd.setModelUOMCustomList(modelUOMCustomList);
    		wOChd.setModelCurrencyList(currencyList);
    		wOChdListCustomList.add(wOChd);
    		
    		//save 
    		
    		List<ModelOrderItemQtySpec> modelOrderItemQtySpecList = new ArrayList<ModelOrderItemQtySpec>();
    		// Only active order item qty specs shall be entered in wo item qty spec.
    		modelOrderItemQtySpecList=	daoOrderItemQtySpec.getOrderItemQtySpecActiveList(inquiryItemQtyId);
    		System.out.println("Order item Qty Spec list size " + modelOrderItemQtySpecList.size());
    		
    		// oder item qty spec list loop populate
    		for (ModelOrderItemQtySpec m :modelOrderItemQtySpecList ) {
    			// data will not be saved in mlfm_wo_item_qty_spec table if already saved for a order_item_qty_id 
    			if (daoWOItemQtySpec.chkAlreadySavedWOItemQtySpec(m.getModelOrderItemQty().getOrderItemQtyId(),m.getModelSpec().getSpecId())<=0) {   // data already exists for 
    		           daoWOItemQtySpec.saveWOItemQtySpec(m);
    			}
    		
    		}
    		
    		
    	}
    	return wOChdListCustomList;
    	
		}
		
		catch(Exception e) {
			e.printStackTrace();
			return wOChdListCustomList;
		}
		
		
    	
	    }
	
	
	 @ResponseBody
	 @RequestMapping(path = "/workorderController/savewochd", method = RequestMethod.POST) 
	 public  List<ModelWOInquiryData> saveWOChd(ModelWOChd wOChd,
	// public String saveWOChd(ModelWOChd wOChd,
			 ModelWOMst modelWOMst,
			 ModelOrderItemQty modelOrderItemQty,
			 ModelCurrency modelCurrency,
			 @RequestParam("wOMstId") Long wOMstId,
			 @RequestParam("orderItemQtyId") Long orderItemQtyId,
			 @RequestParam("itemQty") Double itemQty,
 			 @RequestParam("itemRate") Double itemRate,
 			 @RequestParam("currency") Long currency,
	         @RequestParam("remarks") String remarks,
	         @RequestParam("commissionRate")Double commissionRate,
 			 @RequestParam("commissionTotal")Double commissionTotal,
 			 @RequestParam("commissionPerUOM") Long commissionPerUOM,
 			 @RequestParam("enteredBy") Long enteredBy)
 			//@RequestParam("itemQty")double commissionRate)
 			 {
 	   	 
     	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
     	
     	
     	//ModelWOChd modelWOChd=new ModelWOChd();
     	//wOChd.setWoChdId(wOChdId);
		System.out.println("WO Mst Id : "+ wOMstId);
     	modelWOMst.setwOMstId(wOMstId);
     	System.out.println("ModelWOMst  : "+ modelWOMst);
     	wOChd.setModelWOMst(modelWOMst);
     	
     	System.out.println("orderItemQtyId : "+ orderItemQtyId);
     	modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
     	System.out.println("ModelOrderItemQty  : "+ modelOrderItemQty);
     	wOChd.setModelOrderItemQty(modelOrderItemQty);
     	
     	System.out.println("Item Qty  : "+ itemQty);
     	wOChd.setItemQty(itemQty);
     	
     	System.out.println("Item Rate  : "+ itemRate);
     	wOChd.setItemRate(itemRate);
     	
     	System.out.println("currency  : "+ currency);
     	modelCurrency.setCurrencyId(currency);
     	System.out.println("Model Currency  : "+ modelCurrency);
     	wOChd.setModelCurrency(modelCurrency);
     	
     	System.out.println("Remarks  : "+ remarks);
     	wOChd.setRemarks(remarks);
     	
     	System.out.println("Commission Rate  : "+ commissionRate);
     	wOChd.setCommissionRate(commissionRate);
     	
     	System.out.println("Commission Total  : "+ commissionTotal);
     	wOChd.setCommissionTotal(commissionTotal);
     	
     	System.out.println("Commission Per UOM  : "+ commissionPerUOM);
     	wOChd.setCommissionPerUOM(commissionPerUOM);
     	
     	System.out.println("Active Status :  1 ");
     	wOChd.setActiveStatus(1);
     	System.out.println("Entered By  :  1");
     	
     	ModelUser modelUser =new ModelUser();
     	modelUser.setUserId(enteredBy);
     	wOChd.setEnteredBy(modelUser);
     	wOChd.setEntryTimestamp(entryTime);
     	System.out.println("Entry Time Stamp  : "+entryTime);
     	
     	daoWorkOrderChd.saveWOChd(wOChd);
     	System.out.println("After Saved WO Chd List : "+ wOChd.toString());
     	
     	
     	System.out.println("wOMstId : " + wOMstId);
		 
		 List<ModelWOInquiryData> wOChdListDoneData= new ArrayList<ModelWOInquiryData>();
		 wOChdListDoneData=daoWorkOrder.getWOChdListDoneData(wOChd.getModelWOMst().getwOMstId());
		    
		 System.out.println("List :" +wOChdListDoneData);
		 
		
	    	return wOChdListDoneData;
     	
 	
 	// return "redirect:/work_order";
 	
 }
	 
	 @ResponseBody
	 @RequestMapping(path="/getWOItemQtySpec/list", method=RequestMethod.GET)
	   // public List<ModelWOItemQtySpec> getWOItemQtySpecList(Model model,@RequestParam("orderItemQtyId") Long orderItemQtyId){
	 public List<ModelWOChdListCustom> getWOItemQtySpecList(Model model,@RequestParam("orderItemQtyId") Long orderItemQtyId){
		 
		 
		 /*
		 System.out.println("order Item Qty Id : "+ orderItemQtyId);
		 List<ModelWOItemQtySpec> modelWOItemQtySpecList= daoWOItemQtySpec.getWOItemQtySpecList(orderItemQtyId); 
		 System.out.println("WO Item Qty Spec List : "  + modelWOItemQtySpecList.size());
		 List<ModelSpec> specList=daoSpec.findAll();
         model.addAttribute("specList",specList);
		//List<ModelUOMCustom> modelUOMCustomList= new ArrayList<ModelUOMCustom>(); 
    	//modelUOMCustomList=daoUOMImp.getUOMListCustom();
    	//System.out.println("uom list size " + modelUOMCustomList.size());
    	return modelWOItemQtySpecList;
    	*/
		 
		 
		 System.out.println("Controller order Item Qty Id  : "+ orderItemQtyId);
		 List<ModelWOChdListCustom> resultList= daoWOItemQtySpec.getWOItemQtySpecListCustom(orderItemQtyId); 
		 List<ModelWOChdListCustom> wOItemQtySpecList = new ArrayList<ModelWOChdListCustom>();
		 System.out.println("1.WO Item Qty Spec List : "  + resultList.size());
		 
		 
		    List<ModelSpec> specList = daoSpec.findAll();
		    System.out.println("specList " + specList.size());
		 
		 
		    List<ModelUOMCustom> modelUOMCustomList= new ArrayList<ModelUOMCustom>(); 
	    	modelUOMCustomList=daoUOMImp.getUOMListCustom();
	    	System.out.println("uom list size " + modelUOMCustomList.size());
	    	
	    	
	    	for (ModelWOChdListCustom wOItemQtySpec : resultList ) {
	    		wOItemQtySpec.setModelUOMCustomList(modelUOMCustomList);
	    		wOItemQtySpec.setModelSpecList(specList);
	    		
	    		
	    		wOItemQtySpecList.add(wOItemQtySpec);
	    		
	    	}
	    	
	    	
	    	
	    	System.out.println("Controller wOItemQtySpecList " + wOItemQtySpecList);
	    	System.out.println("2.Controller wOItemQtySpecList size : " + wOItemQtySpecList.size());
		 
		 
		 return wOItemQtySpecList;
		 
    	
    	
	 }
	 
	 
	 @ResponseBody
	    @RequestMapping(value = "/saveWOItemQtySpecNew/Ajax")
	    public void saveWOItemQtySpecNew(
	    		@RequestParam(value = "wOSpecNew") Long wOSpecNew,
	    		@RequestParam(value = "wOValueNew") String wOValueNew,
	    		@RequestParam(value = "wOUOMNew") Long wOUOMNew,
	    		@RequestParam(value = "wORemarksSpecNew") String wORemarksSpecNew) {
		 
		    Long logonUserId=systemUserId;
		 
	        System.out.println("wO Spec New : " + wOSpecNew);
	        System.out.println("wO Value New : " + wOValueNew);
	        System.out.println("wO UOM New : " + wOUOMNew);
	        System.out.println("wO Remarks Spec New : " + wORemarksSpecNew);
	        
	        java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	        
	        ModelWOItemQtySpec modelWOItemQtySpec= new ModelWOItemQtySpec();
			ModelSpec modelSpec = new ModelSpec(); 
			
			modelSpec.setSpecId(wOSpecNew);
			modelWOItemQtySpec.setModelSpecWO(modelSpec);
			modelWOItemQtySpec.setwOSpecValue(wOValueNew);
			
			ModelUOM modelUOM = new ModelUOM();
			modelUOM.setUomId(wOUOMNew);
			modelWOItemQtySpec.setModelUOMWO(modelUOM);
			modelWOItemQtySpec.setRemarks(wORemarksSpecNew);
			modelWOItemQtySpec.setActiveStatus(1);
			
			
			ModelUser modelUser = new ModelUser();
			modelUser.setUserId(logonUserId);
			
			modelWOItemQtySpec.setEnteredBy(modelUser);
			modelWOItemQtySpec.setEntryTimestamp(entryTime);
			
			
	        
	        daoWOItemQtySpec.saveWOItemQtySpecNew(modelWOItemQtySpec);
	    }
	 
	 
	 @ResponseBody
	 @RequestMapping(path="/workordercontroller/getwomstlistdonedata", method=RequestMethod.GET)
	 public List<ModelWOInquiryData> getWOMstListDoneData(Model model,
	 @RequestParam("orderItemQtyId") Long orderItemQtyId){
		 
		 System.out.println("orderItemQtyId : " + orderItemQtyId);
		 
		 List<ModelWOInquiryData> wOMstListDoneData= new ArrayList<ModelWOInquiryData>();
		 wOMstListDoneData=daoWorkOrder.getWOMstListDoneData(orderItemQtyId);
		    
		 System.out.println("List :" +wOMstListDoneData);
		 
		
	    	return wOMstListDoneData;
		 
		 //   return null;
	       }
	 
	 
	 
	 
	 
	 @ResponseBody
	 @RequestMapping(path="/workordercontroller/getwochdlistdonedata", method=RequestMethod.GET)
	 public List<ModelWOInquiryData> getWOChdListDoneData(Model model,
	 @RequestParam("wOMstId") Long wOMstId){
		 
		 System.out.println("wOMstId : " + wOMstId);
		 
		 List<ModelWOInquiryData> wOChdListDoneData= new ArrayList<ModelWOInquiryData>();
		 wOChdListDoneData=daoWorkOrder.getWOChdListDoneData(wOMstId);
		    
		 System.out.println("List :" +wOChdListDoneData);
		 
		
	    	return wOChdListDoneData;
		 
		   // return null;
	       }
	 
	 
	
     
	 
	 
	 @RequestMapping(path="/workordercontroller/workorderchdeditsave",method=RequestMethod.POST)
		@ResponseBody
		public List<ModelWOInquiryData> workOrderChdEditSave(Model model,ModelWOChd modelWOChd,ModelCurrency modelCurrency,ModelUOM modelUOM,
				 @RequestParam("wOMstId") Long wOMstId,
				@RequestParam("wOChdId") Long wOChdId,
				  @RequestParam("commissionRate") Double commissionRate,
		          @RequestParam("commissionTotal") Double commissionTotal,
		          @RequestParam("wOChdQty") Double itemQty,
		          @RequestParam("itemRate") Double itemRate,
		          @RequestParam("currencyId") Long currencyId,
		          @RequestParam("commissionPerUOM") Long commissionPerUOM,
		          @RequestParam("remarks") String remarks,
		          @RequestParam("activeStatus") int activeStatus,
		          @RequestParam("updatedBy") Long updatedBy) {
			
		 System.out.println("wOMstId :" +wOMstId);
		 
		 
		     System.out.println("wOChdId :" +wOChdId);
			 System.out.println("commissionRate :" +commissionRate);
			 System.out.println("commissionTotal :" +commissionTotal);
			 System.out.println("itemQty :" +itemQty);
			 System.out.println("itemRate :" +itemRate);
			 System.out.println("currencyId :" +currencyId);
			 System.out.println("commissionPerUOM :" +commissionPerUOM);
			 System.out.println("remarks :" +remarks);
			 System.out.println("activeStatus :" +activeStatus);
			 
			 
			 
			    java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			    
			    
			    Optional<ModelWOChd> modelWOChdOpt = daoWorkOrderChd.findWOChdById(wOChdId);
			    
			    modelWOChd=modelWOChdOpt.get();
			    
			    modelWOChd.setCommissionRate(commissionRate);
			    modelWOChd.setCommissionTotal(commissionTotal);
			    modelWOChd.setItemQty(itemQty);
			    modelWOChd.setItemRate(itemRate);
			    modelWOChd.setCommissionPerUOM(commissionPerUOM);
			    
			    modelCurrency.setCurrencyId(currencyId);
			    modelWOChd.setModelCurrency(modelCurrency);
			    
			    modelWOChd.setRemarks(remarks);
			    modelWOChd.setActiveStatus(activeStatus);
			    
				ModelUser modelUser =new ModelUser();
				modelUser.setUserId(updatedBy);
			    modelWOChd.setUpdatedBy(modelUser);
			    modelWOChd.setUpdateTimestap(entryTime);
			    
			    daoWorkOrderChd.saveWOChd(modelWOChd);
			    
			   
			    
			    System.out.println("After Save modelWOChd :" +modelWOChd);
			    
			    List<ModelWOInquiryData> wOChdListDoneData= new ArrayList<ModelWOInquiryData>();
				 wOChdListDoneData=daoWorkOrder.getWOChdListDoneData(wOMstId);
				    
				 System.out.println("List :" +wOChdListDoneData);
				 
				
			    	return wOChdListDoneData;
			
		//	return null;
		}
	 
	 
	 
	 @RequestMapping(path="/workordercontroller/specsave",method=RequestMethod.POST)
		@ResponseBody
		public List<ModelDesignSpec> specSave(Model model,ModelSpec modelSpec,
				  @RequestParam("specName") String specName,
				  @RequestParam("remarks") String remarks,
		          @RequestParam("activeStatus") int activeStatus) {
	 		
	 		
			
			System.out.println("Controller Section: Spec Data " );
			System.out.println("specName " +specName);
			System.out.println("remarks " +remarks);
			System.out.println("activeStatus " +activeStatus);
			
			
			java.util.Date date = new java.util.Date();
		    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			modelSpec.setSpecName(specName);
			modelSpec.setRemarks(remarks);
			modelSpec.setActiveStatus(activeStatus);
			
			modelSpec.setEnteredBy(1L);
			modelSpec.setEntryTimestamp(entryTime);
			
			
			daoSpeca.specSave(modelSpec);
			
			System.out.println("after Save modelSpec : " +modelSpec);
			
			
			
			List<ModelDesignSpec> specItemList = specItem.getDesignSpecList();
			
			System.out.println("after Save specItemList : " +specItemList);
			
			return specItemList;
			
	 	}

	
	
	
	//creator : sas , date : 01/12/2019
//	@ResponseBody
//    @RequestMapping(path="/workOrderEntry/workOrderMst/search/ajax", method=RequestMethod.GET)
//    public List<ModelPIInquiryList> getWoMstListAjax(Model model,@RequestParam("id") Long id) {
//    	System.out.println("id :" +id);
//    	List<ModelPIInquiryList> WoMstList=daoWorkOrder.getWoMstList(id);
//    	      				
//		return WoMstList;
//    }
	 
	 
	 
		
	 	@RequestMapping(path="/workordercontroller/findspectypeafteraddspec")
		@ResponseBody
		public List<ModelSpec> findSpecTypeAfterAddSpec(){
	 		
	 		System.out.println("Controller section findSpecTypeAfterAddSpec : ");
	 		
			return daoSpec.findAll();
		}
	 	
	 
	
	@RequestMapping(path="/workOrderEntry/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwner(@RequestParam("ownerType")Long typeId){
		//return orderOwner.getOwnerByType(typeId);
		
		return orderOwner.getOwnerByTypeInOrder(typeId);
	}
	
	
	@RequestMapping(path="/workOrderEntry/findactiveorderowner")
	@ResponseBody
	public List<ModelOrderOwner> findActiveOrderOwner(@RequestParam("ownerType")Long typeId){
		System.out.println("Controller Section findActiveOrderOwner : /workOrderEntry/findactiveorderowner" +typeId);
		return orderOwner.getActiveOrderOwnerListByOwnerTypeId(typeId);
	}
	
	
	@RequestMapping(path="/work_order/user/cluster")
	@ResponseBody
	public List <ModelUserCluster> findCluster(@RequestParam("user")Long userId){			
		return userCluster.getClusterByUser(userId);
	}
	


}
