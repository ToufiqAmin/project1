package com.biziitech.mlfm.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.daoimp.DaoUserImp;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelDesignCustom;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoDesign;
import com.biziitech.mlfm.dao.DaoDesignConsumItem;
import com.biziitech.mlfm.dao.DaoDesignSpec;
import com.biziitech.mlfm.dao.DaoFabricType;
import com.biziitech.mlfm.dao.DaoItem;
import com.biziitech.mlfm.dao.DaoProductionPlan;
import com.biziitech.mlfm.dao.DaoSpec;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoDesignImp;
import com.biziitech.mlfm.daoimpl.DaoDesignSpecImp;
import com.biziitech.mlfm.daoimpl.DaoDessignConsumItemImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.daoimpl.DaoOrderImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoSpecImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignConsumItem;
import com.biziitech.mlfm.model.ModelDesignSpec;
import com.biziitech.mlfm.model.ModelFabricType;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Controller
public class DesignController {
	
	@Autowired
	private DaoSpecImp daoSpec;
	@Autowired
	private DaoDesignSpecImp specItem;
	@Autowired
	private DaoDessignConsumItemImp consumeItem;
	@Autowired
	private DaoUserImp user;
	@Autowired
	private DaoOrderImp order;
	@Autowired DaoDesignImp designImp;
	
	@Autowired
	private DaoDesignSpec daoDesignSpec;
	
	@Autowired
	private DaoDesignConsumItem daoDessignConsumItem;
	
	@Autowired
	private DaoDesign daoDesign;
	
	@Autowired
	private DaoSpec daoSpeca;
	
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoItem daoItem;
	
	@Autowired
	private DaoMachineTypeImp daoMachineTypeImp;
	
	@Autowired
	private DaoFabricType daoFabricType;
	
	@Autowired
	private DaoProductionPlan daoProductionPlan;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	
	
	@RequestMapping(path="/design/{userId}")
	public String getDesignTables(@PathVariable Long userId,Model model) {
		
		
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
				
				List<ModelUser> userList=daoProductionPlan.getUserList();
				//List<ModelUser> userList= user.getAllUSerName();
				userList.get(0).getUserName();
				System.out.println("user ID and Name :" +userList.get(0).getUserName());
				model.addAttribute("userList",userList);
			}
			
			else {
				
				List<ModelUser> userList=user.getUser(userId);
				model.addAttribute("userList",userList);
			}
		//this code add 10/04/2019
		
		
		
		
		
		
		List<ModelUser> userList= user.getAllUSerName();
		//model.addAttribute("userList",userList);
		model.addAttribute("searchList",order.getOwnerList());
		ModelDesign design=new ModelDesign();
		design.setActive(true);
		model.addAttribute("designList",design);
		model.addAttribute("designListInquery",designImp.getDesignList());
		// this code add SAS -25-02-2019
		
		model.addAttribute("designSpecList",specItem.getDesignSpecList());
		
		model.addAttribute("designConsumItemList",consumeItem.getDesignConsumeListItem());
		// this code add SAS -25-02-2019
		List<ModelSpec> specList=daoSpec.findAll();
        model.addAttribute("specList",specList);
        
        
        
        // 27-02-2019 new code implement -SAS
        
        List<ModelOrderOwnerType> ownerTypeList= daoOrderOwnerTypeImp.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
        List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwner();
		model.addAttribute("ownerList",ownerList);
		
		
		List<ModelItem> modelItemList= daoItem.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
		
		
		List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
		model.addAttribute("machineTypeList",machineTypeList);
		
		
		List<ModelFabricType> fabricTypeList = daoFabricType.getFabricTypeList();
		model.addAttribute("fabricTypeList",fabricTypeList);
		
		
		List<ModelUOM> uomName=daoUOMImp.getUomName();
		model.addAttribute("uomList", uomName);
		
		
		
		

        return "design_table";
        
	}
	
	
	// new Implementation  - SAS Start
	
	
	/*
	   * Unchecked : Design Data Provide
	   * 
	   * creator : sas
	   * 
	   * */
		@ResponseBody
		@RequestMapping(path="/designcontroller/getdesigndatanotdone")
		public List<ModelDesignCustom> getDesignDataNotDone(Model model,
				
				  @RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
				  @RequestParam("orderOwnerId") Long orderOwnerId,
		          @RequestParam("ultimateBuyerId") Long ultimateBuyerId,
		          @RequestParam("orderId") Long orderId,
		          @RequestParam("itemId") Long itemId,
		          @RequestParam("userId") Long userId,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderStartDate") Date orderStartDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("orderEndDate") Date orderEndDate,
		          @RequestParam("userInqueryNo") String userInqueryNo,
		          @RequestParam("remarks") String remarks,
		          @RequestParam("active") int active
		          ){
			
			
			System.out.println("Ajax data sent " + "\n orderOwnerId  :" + orderOwnerId+
					"\n orderOwner Type Id  :" + orderOwnerTypeId+
					"\n ultimate buyer id :" + ultimateBuyerId+
					"\n order Id :" + orderId+
					"\n itemId :" +itemId +
					"\n userId :" + userId +
					"\n orderStartDate :" + orderStartDate+
					"\n orderEndDate :" + orderEndDate+
					"\n userInqueryNo:" + userInqueryNo+
					"\n remarks :" +remarks+
					"\n active :" + active);
			
			
			List<ModelDesignCustom> designListNotDone =  daoDesign.getDesignListNotDone(orderOwnerTypeId,orderOwnerId,ultimateBuyerId,orderId,itemId,userId,orderStartDate,orderEndDate,userInqueryNo,remarks,active);
		
			 System.out.println("designListNotDone size : " + designListNotDone.size());
			 
			return designListNotDone;
		
		          }
		
		/*
		   * checked : Design Data Provide
		   * 
		   * creator : sas
		   * 
		   * */
			@ResponseBody
			@RequestMapping(path="/designcontroller/getdesigndatadone")
			public List<ModelDesignCustom> getDesignDataDone(Model model,
					  @RequestParam("orderOwnerTypeId") Long orderOwnerTypeId,
					  @RequestParam("orderOwnerId") Long orderOwnerId,
			          @RequestParam("ultimateBuyerId") Long ultimateBuyerId,
			          @RequestParam("orderId") Long orderId,
			          @RequestParam("itemId") Long itemId,
			          @RequestParam("userId") Long userId,
			          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designStartDate") Date designStartDate,
			          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designEndDate") Date designEndDate,
			          @RequestParam("userInqueryNo") String userInqueryNo,
			          @RequestParam("remarks") String remarks,
			          @RequestParam("active") int active
			          ){
				
				System.out.println("Design Done List ");
				System.out.println("Ajax data sent " + "\n orderOwnerId  :" + orderOwnerId+
						"\n orderOwnerTypeId :" + orderOwnerTypeId+
						"\n ultimate buyer id :" + ultimateBuyerId+
						"\n order Id :" + orderId+
						"\n itemId :" +itemId +
						"\n userId :" + userId +
						"\n designStartDate :" + designStartDate+
						"\n designEndDate :" + designEndDate+
						"\n userInqueryNo:" + userInqueryNo+
						"\n remarks :" +remarks+
						"\n active :" + active);
				
				List<ModelDesignCustom> designListDone =  daoDesign.getDesignListDone(orderOwnerTypeId,orderOwnerId,ultimateBuyerId,orderId,itemId,userId,designStartDate,designEndDate,userInqueryNo,remarks,active);
				
				 System.out.println("designListDone size : " + designListDone.size());
				
			return designListDone;
			}
			
			
			@RequestMapping(path="/designcontroller/getdesignlistdoneorder",method=RequestMethod.GET)
			@ResponseBody
			public List<ModelDesignCustom> getDesignListDoneOrder(Model model,
					@RequestParam("designId") Long designId){
				
				
				 System.out.println("getDesignListDoneOrder : controller :designId: " + designId);
				 
				 
				 List<ModelDesignCustom> designList= new ArrayList<ModelDesignCustom>();
					designList=daoDesign.getDesignListByDesignId(designId);
				 System.out.println(" controller : Model design  find by Id : " + designList);
				 System.out.println(" controller : Model design  list size : " + designList.size());
				
				return designList;
				 
				
				
			//	return null;
			}
			
			
			@RequestMapping(path="/designcontroller/getdesignspeclistanddesignconsumeitemlist",method=RequestMethod.GET)
			@ResponseBody
			public List<ModelDesignCustom> getDesignSpecListDoneDesign(Model model,
					@RequestParam("designId") Long designId){
				
				
				 System.out.println("getDesignSpecListAndDesignConsumeItemList : controller :designId: " + designId);
				 
				 
				 List<ModelDesignCustom> allDesignSpecList= new ArrayList<ModelDesignCustom>();
			    	allDesignSpecList = daoDesignSpec.getAllDesignSpecListByDesignId(designId);
			        
			        
			        System.out.println("Controller Section: allDesignSpecList : " +allDesignSpecList);
			        
			        System.out.println("Controller Section: allDesignSpecList Size : " +allDesignSpecList.size());
			  	    
			          return allDesignSpecList;
				
			
				 
				
				
				//return null;
			}
			
			
			
			@RequestMapping(path="/designcontroller/getdesignconsumeitemlistdonedesign",method=RequestMethod.GET)
			@ResponseBody
			public List<ModelDesignCustom> getDesignConsumeItemListDoneDesign(Model model,
					@RequestParam("designId") Long designId){
				
				
				 System.out.println("getDesignConsumeItemListDoneDesign : controller :designId: " + designId);
				 
				 
				 List<ModelDesignCustom> allDesignConsumeList= new ArrayList<ModelDesignCustom>();
		    	 allDesignConsumeList=daoDessignConsumItem.getAllDesignConsumItemListByDesignId(designId);
		         
		         
		         System.out.println("Controller Section: allDesignConsumeList : " +allDesignConsumeList);
		         
		         System.out.println("Controller Section: allDesignConsumeList Size : " +allDesignConsumeList.size());
		   	    
		           return allDesignConsumeList;
				
			
				 
				
				
				//return null;
			}
			
			
			
			
			@RequestMapping(path="/designcontroller/designsave",method=RequestMethod.POST)
			@ResponseBody
			public List<ModelDesignCustom> designSave(Model model,ModelOrder modelOrder,ModelMachineType modelMachineType,
					ModelDesign modelDesign,ModelFabricType modelFabricType,
					  @RequestParam("getOrderIdfordesign") Long orderId,
					  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("modalDesignDate") Date designDate,
					  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("modalFactoryDeliDate") Date factoryDeliDate,
			          @RequestParam("modalDesignName") String designName,
			          @RequestParam("modalUserDesignNumber") String userDesignNo,
			          @RequestParam("modalDesignRemarks") String remarks,
			          @RequestParam("modalDesignerName") Long designerId,
			          @RequestParam("modalMachineTypeId") Long machineTypeId,
			          
			          @RequestParam("modalNoOfStitches") Integer noOfStitches,
			          @RequestParam("modalFebricTypeId") Long fabricTypeId,
			          @RequestParam("modalDTM") String dTM,
			          @RequestParam("modalLaceTypeId") int laceTypeId,
			          
			          @RequestParam("activeStatus") int activeStatus) {
				
				 Long userId=systemUserId;
				
				
				System.out.println("Controller Section: Design Save " );
				
				System.out.println("orderId " +orderId);
				System.out.println("designDate " +designDate);
				System.out.println("factoryDeliDate " +factoryDeliDate);
				System.out.println("designName " +designName);
				System.out.println("userDesignNo " +userDesignNo);
				System.out.println("remarks " +remarks);
				System.out.println("designerId " +designerId);
				System.out.println("machineTypeId " +machineTypeId);
				System.out.println("activeStatus " +activeStatus);
				
				System.out.println("noOfStitches " +noOfStitches);
				System.out.println("febricTypeId " +fabricTypeId);
				System.out.println("dTM " +dTM);
				System.out.println("laceTypeId " +laceTypeId);
				
				
				java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
				
				modelOrder.setOrderId(orderId);
				modelDesign.setModelOrder(modelOrder);
				
				modelDesign.setDesignName(designName);
				modelDesign.setDesignDate(designDate);
				modelDesign.setFactPropDeliveryDate(factoryDeliDate);
				modelDesign.setUserDesignNo(userDesignNo);
				modelDesign.setRemarks(remarks);
				modelDesign.setDesignerId(designerId);
				
				modelMachineType.setMachineTypeId(machineTypeId);
				modelDesign.setModelMachineType(modelMachineType);
				
				modelDesign.setActiveStatus(activeStatus);
				
				modelFabricType.setFabricTypeId(fabricTypeId);
				modelDesign.setModelFabricType(modelFabricType);
				modelDesign.setdTM(dTM);
				modelDesign.setNoOfStitches(noOfStitches);
				modelDesign.setLaceTypeId(laceTypeId);
				
				 ModelUser user=new ModelUser();
				 user.setUserId(userId);
				
				modelDesign.setEnteredBy(user);
				modelDesign.setEntryTimestamp(entryTime);
				
				daoDesign.designSaveModal(modelDesign);
				
				System.out.println("Controller Section: designId "+modelDesign.getDesignId());
				
				List<ModelDesignCustom> designList= new ArrayList<ModelDesignCustom>();
				designList=daoDesign.getDesignListByDesignId(modelDesign.getDesignId());
				
				System.out.println("Controller Section: After Save Design " +designList );
				
				return designList;
			}
			
			
			@RequestMapping(path="/designcontroller/getdesignlist",method=RequestMethod.GET)
			@ResponseBody
			public List<ModelDesignCustom> getDesignList(Model model,ModelProductionPlanMst modelProductionPlanMst,
					@RequestParam("designId") Long designId){
				
				
				 System.out.println(" controller :designId: " + designId);
				 
				 List<ModelDesignCustom> designList= new ArrayList<ModelDesignCustom>();
					designList=daoDesign.getDesignListByDesignId(designId);
				 System.out.println(" controller : Model design  find by Id : " + designList);
				 System.out.println(" controller : Model design  list size : " + designList.size());
				
				return designList;
			}
			
			
			@RequestMapping(path="/designcontroller/designmodaleditsave",method=RequestMethod.POST)
			@ResponseBody
			public List<ModelDesignCustom> designModalEditSave(Model model,ModelOrder modelOrder,ModelMachineType modelMachineType,
					ModelDesign modelDesign,ModelFabricType modelFabricType,
					  @RequestParam("designId") Long designId,
					  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("designDate") Date designDate,
					  @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("factPropDeliveryDate") Date factoryDeliDate,
			          @RequestParam("designName") String designName,
			          @RequestParam("userDesignNo") String userDesignNo,
			          @RequestParam("remarks") String remarks,
			          @RequestParam("designerId") Long designerId,
			          @RequestParam("machineTypeId") Long machineTypeId,
			          
			          @RequestParam("noOfStitches") Integer noOfStitches,
			          @RequestParam("fabricTypeId") Long fabricTypeId,
			          @RequestParam("dTM") String dTM,
			          @RequestParam("laceTypeId") int laceTypeId,
			      
			         @RequestParam("activeStatus") int activeStatus) {
				
				
				   Long userId=systemUserId;
				
				System.out.println("Controller Section: Design Save " );
				System.out.println("designId " +designId);
				System.out.println("designDate " +designDate);
				System.out.println("factoryDeliDate " +factoryDeliDate);
				System.out.println("designName " +designName);
				System.out.println("userDesignNo " +userDesignNo);
				System.out.println("remarks " +remarks);
				System.out.println("designerId " +designerId);
				System.out.println("machineTypeId " +machineTypeId);
				System.out.println("activeStatus " +activeStatus);
				
				System.out.println("noOfStitches " +noOfStitches);
				System.out.println("febricTypeId " +fabricTypeId);
				System.out.println("dTM " +dTM);
				System.out.println("laceTypeId " +laceTypeId);
				
				java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			    
			    Optional <ModelDesign> designDataByOpt = daoDesign.findDesignById(designId);
			    System.out.println("designDataByOpt (optional type Object) : "+designDataByOpt);
			    modelDesign= designDataByOpt.get();
			    
			    System.out.println("designData (ModelDesign type Object) : "+modelDesign);
			    
				
				modelDesign.setDesignName(designName);
				modelDesign.setDesignDate(designDate);
				modelDesign.setFactPropDeliveryDate(factoryDeliDate);
				modelDesign.setUserDesignNo(userDesignNo);
				modelDesign.setRemarks(remarks);
				modelDesign.setDesignerId(designerId);
				modelMachineType.setMachineTypeId(machineTypeId);
				modelDesign.setModelMachineType(modelMachineType);
				modelDesign.setActiveStatus(activeStatus);
				
				
				modelFabricType.setFabricTypeId(fabricTypeId);
				modelDesign.setModelFabricType(modelFabricType);
				modelDesign.setdTM(dTM);
				modelDesign.setNoOfStitches(noOfStitches);
				modelDesign.setLaceTypeId(laceTypeId);
				
				
				 ModelUser user=new ModelUser();
				 user.setUserId(userId);
				
				modelDesign.setUpdatedBy(user);
				modelDesign.setUpdateTimestap(entryTime);
				
				
				daoDesign.designSaveModal(modelDesign);
				
				
                 System.out.println("Controller Section: designId "+modelDesign.getDesignId());
				
				List<ModelDesignCustom> designList= new ArrayList<ModelDesignCustom>();
				designList=daoDesign.getDesignListByDesignId(modelDesign.getDesignId());
				
				System.out.println("Controller Section: Edit Save Design " +designList );
				
				return designList;
				
				
			//	return null;
			}
			
			
			@RequestMapping(path="/designcontroller/specificationsave",method=RequestMethod.POST)
			@ResponseBody
			public List<ModelDesignCustom> specificationSave(Model model,ModelOrder modelOrder,ModelMachineType modelMachineType,ModelDesign modelDesign,
					  @RequestParam("specValue") String specValue,
					  @RequestParam("remarks") String remarks,
			         // @RequestParam("modalMachineTypeId") int machineTypeId,
			          @RequestParam("specId") Long specId) {
				
				System.out.println("Controller Section: Design Save " );
				
				System.out.println("specValue " +specValue);
				System.out.println("remarks " +remarks);
				System.out.println("specId " +specId);
				
				return null;
			}
			
			
			
			@ResponseBody
		    @RequestMapping(value = "/design/specificationItem")
			//public String getSpecificationItemAjax(@RequestParam(value = "specificationValue") String id,@RequestParam(value = "remarks")String remarks,@RequestParam(value = "item")String item) {
		    public List<ModelDesignCustom> getSpecificationItem(ModelDesignSpec modelDesignSpec,ModelDesign modelDesign,ModelSpec modelSpec,
		    		@RequestParam(value = "specificationValue") String specValue,
		    		@RequestParam(value = "remarks")String remarks,
		    	//	@RequestParam(value = "item")String item,
		    		@RequestParam(value = "designId")Long designId,
		    		@RequestParam(value = "specId")Long specId) {
		        System.out.println("come to ajax /n specValue : "+ specValue+"remarks : "+remarks+"designId : "+designId);
		        
		      //  specItem.saveSpecification(id, remarks, specItem.getDesignId());
		       // specItem.saveSpecification(specValue, remarks,designId);
		        
		        Long logonUserId=systemUserId;
		        
		        java.util.Date date = new java.util.Date();
			    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		        
		        modelDesignSpec.setSpecValue(specValue);
		        modelDesignSpec.setRemarks(remarks);
		        
		        modelDesign.setDesignId(designId);
		        modelDesignSpec.setModelDesign(modelDesign);
		        
		        modelSpec.setSpecId(specId);
		        modelDesignSpec.setModelSpec(modelSpec);
		        
		        modelDesignSpec.setActiveStatus(1);
		        
		        ModelUser modelUser = new ModelUser();
		        modelUser.setUserId(logonUserId);
		        
		        modelDesignSpec.setEnteredBy(modelUser);
		        modelDesignSpec.setEntryTimestamp(entryTime);
		        
		        
		        daoDesignSpec.saveSpec(modelDesignSpec);
		        
		        
		        List<ModelDesignCustom> designSpecList= new ArrayList<ModelDesignCustom>();
		        designSpecList=daoDesignSpec.getDesignSpecListBySpecId(modelDesignSpec.getDesignSpecId());
		        
		        System.out.println("Controller Section: designSpec : " +designSpecList);
		        
		        System.out.println("Controller Section: designSpec Size : " +designSpecList.size());
		        
		        return designSpecList;

		    }
	
	
	
 	@RequestMapping(path="/designcontroller/findOwnerType")
	@ResponseBody
	public List<ModelOrderOwner> findOwnerType(@RequestParam("ownerType")Long typeId){
 		
 		System.out.println(" design Controller  : find owner Type ");
		return daoOrderOwnerImp.getOwnerByTypeInOrder(typeId);
	}
 	
 	
 	@RequestMapping(path="/designcontroller/findspectype")
	@ResponseBody
	public List<ModelSpec> findSpecType(){
 		
 		System.out.println("Controller section findSpecType : ");
 		
		return daoSpec.findAll();
	}
 	
 	
 	@RequestMapping(path="/designcontroller/findspectypeafteraddspec")
	@ResponseBody
	public List<ModelSpec> findSpecTypeAfterAddSpec(){
 		
 		System.out.println("Controller section findSpecTypeAfterAddSpec : ");
 		
		return daoSpec.findAll();
	}
 	
 	
 	@ResponseBody
	@RequestMapping(path="/designcontroller/getspeclist", method=RequestMethod.GET)
    public List<ModelDesignCustom> getSpecList(@RequestParam(value = "designSpecId")Long designSpecId) {
		
    	System.out.println("designSpecId : " + designSpecId );
    	
    	List<ModelDesignCustom> designSpecList= new ArrayList<ModelDesignCustom>();
        designSpecList=daoDesignSpec.getDesignSpecListBySpecId(designSpecId);
        
        System.out.println("Controller Section: designSpec : " +designSpecList);
        
        
        
        System.out.println("Controller Section: designSpec Size : " +designSpecList.size());
    	
    	return designSpecList;
    }
 	
 	
 	@RequestMapping(path="/designcontroller/specificationmodaleditsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelDesignCustom> specificationModalEditSave(Model model,ModelDesignSpec modelDesignSpec,ModelSpec modelSpec,
			  @RequestParam("designId") Long designId,
			  @RequestParam("specDesignId") Long specDesignId,
			  @RequestParam("specId") Long specId,
			  @RequestParam("specValue") String specValue,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("activeStatus") int activeStatus) {
 		
 		
 		Long logonUserId=systemUserId;
 		
 		
 		System.out.println("designId " +designId);
		
		System.out.println("Controller Section: Specification Edit Save " );
		System.out.println("specDesignId " +specDesignId);
		System.out.println("specId " +specId);
		System.out.println("specValue " +specValue);
		System.out.println("remarks " +remarks);
		System.out.println("activeStatus " +activeStatus);
		
		
		Optional <ModelDesignSpec> designSpecDataByOpt = daoDesignSpec.findDesignSpecById(specDesignId);
	    System.out.println("designSpecDataByOpt (optional type Object) : "+designSpecDataByOpt);
	    modelDesignSpec= designSpecDataByOpt.get();
	    System.out.println("designSpecData (ModelDesign type Object) : "+modelDesignSpec);
		
		java.util.Date date = new java.util.Date();
	    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    
	    
	    modelSpec.setSpecId(specId);
        modelDesignSpec.setModelSpec(modelSpec);
        
        modelDesignSpec.setSpecValue(specValue);
        modelDesignSpec.setRemarks(remarks);
        
        modelDesignSpec.setActiveStatus(activeStatus);
     
        ModelUser modelUser = new ModelUser();
        modelUser.setUserId(logonUserId);
        
        modelDesignSpec.setUpdatedBy(modelUser);
        modelDesignSpec.setUpdateTimestap(entryTime);
        
        
        
        daoDesignSpec.saveSpec(modelDesignSpec);
        
        /*
        List<ModelDesignCustom> designSpecList= new ArrayList<ModelDesignCustom>();
        designSpecList=daoDesignSpec.getDesignSpecListBySpecId(modelDesignSpec.getDesignSpecId());
        
        System.out.println("Controller Section: designSpec : " +designSpecList);
        
        System.out.println("Controller Section: designSpec Size : " +designSpecList.size());
        
        return designSpecList;
	 */
        
        
        List<ModelDesignCustom> allDesignSpecList= new ArrayList<ModelDesignCustom>();
    	allDesignSpecList = daoDesignSpec.getAllDesignSpecListByDesignId(designId);
        
        
        System.out.println("Controller Section: allDesignSpecList : " +allDesignSpecList);
        
        System.out.println("Controller Section: allDesignSpecList Size : " +allDesignSpecList.size());
  	    
          return allDesignSpecList;
        
        
		//return null;
	}
 	
 	
 	
 	@ResponseBody
    @RequestMapping(value = "/designcontroller/savedesignconsumeitem")
	 public List<ModelDesignCustom> saveDesignConsumeItem(ModelDesignConsumItem modelDesignConsumItem,
			 ModelDesign modelDesign,
			 ModelItem modelItem,
			 ModelUOM  modelUOM,
    		@RequestParam(value = "itemId") Long itemId,
    		@RequestParam(value = "remarks")String remarks,
    	    @RequestParam(value = "designId")Long designId,
    	    @RequestParam(value = "uomId")Long uomId,
    		@RequestParam(value = "itemQty")Long itemQty) {
 		
        System.out.println("come to ajax  itemId : " +itemId);
        System.out.println(" itemQty : " +itemQty);
        System.out.println(" designId : " +designId);
        System.out.println(" uomId : " +uomId);
        System.out.println(" remarks : " +remarks);
        
        
        
        java.util.Date date = new java.util.Date();
	    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    
	    modelDesignConsumItem.setItemQty(itemQty);
	    
	    modelDesign.setDesignId(designId);
	    modelDesignConsumItem.setModelDesign(modelDesign);
	    
	    modelUOM.setUomId(uomId);
	    modelDesignConsumItem.setModelUOM(modelUOM);
	    
	    modelItem.setItemId(itemId);
	    modelDesignConsumItem.setModelItem(modelItem);
	    modelDesignConsumItem.setRemarks(remarks);
	    modelDesignConsumItem.setActiveStatus(1);
	    
	    Long logonUserId=systemUserId;
        ModelUser modelUser = new ModelUser();
        modelUser.setUserId(logonUserId);
	    
	    modelDesignConsumItem.setEnteredBy(modelUser);
	    modelDesignConsumItem.setEntryTimestamp(entryTime);
	    
	    
	    daoDessignConsumItem.saveDesignConsumeItem(modelDesignConsumItem);
	    
      List<ModelDesignCustom> designConsumeList= new ArrayList<ModelDesignCustom>();
      designConsumeList=daoDessignConsumItem.getDesignConsumItemListByDesignConItemId(modelDesignConsumItem.getDesignConsumItemId());
      
      
      System.out.println("Controller Section: designConsumeItemList : " +designConsumeList);
      
      System.out.println("Controller Section: designConsumeItemList Size : " +designConsumeList.size());
	    
        return designConsumeList;

        
       // return null;

    }
 	
 	
 	@ResponseBody
	@RequestMapping(path="/designcontroller/getdesignconsumeitemlist", method=RequestMethod.GET)
    public List<ModelDesignCustom> getDesignConsumeItemList(@RequestParam(value = "designConsumeItemId")Long designConsumeItemId) {
		
    	System.out.println("designConsumeItemId : " + designConsumeItemId );
    	
    	 List<ModelDesignCustom> designConsumeList= new ArrayList<ModelDesignCustom>();
         designConsumeList=daoDessignConsumItem.getDesignConsumItemListByDesignConItemId(designConsumeItemId);
         
         
         System.out.println("Controller Section: designConsumeItemList : " +designConsumeList);
         
         System.out.println("Controller Section: designConsumeItemList Size : " +designConsumeList.size());
   	    
           return designConsumeList;
    }
 	
 	
 	
 	
 	
 	
 	@RequestMapping(path="/designcontroller/consumemodaleditsave",method=RequestMethod.POST)
	@ResponseBody
	public List<ModelDesignCustom> consumeModalEditSave(Model model,ModelDesignConsumItem modelDesignConsumItem,
			ModelItem modelItem,
			ModelUOM modelUOM,
			  @RequestParam("designId") Long designId,
			  @RequestParam("designConsumeitemId") Long designConsumeItemId,
			  @RequestParam("itemId") Long itemId,
			  @RequestParam("itemQty") Long itemQty,
	          @RequestParam("remarks") String remarks,
	          @RequestParam("uomId") Long uomId,
	          @RequestParam("activeStatus") int activeStatus) {
 		
 		System.out.println("designId " +designId);
		
		System.out.println("Controller Section: Design Consume Edit Save " );
		System.out.println("designConsumeitemId " +designConsumeItemId);
		System.out.println("itemId " +itemId);
		System.out.println("itemQty " +itemQty);
		System.out.println("remarks " +remarks);
		System.out.println("uomId " +uomId);
		System.out.println("activeStatus " +activeStatus);
		
		
		Optional <ModelDesignConsumItem> designConsumeItemDataByOpt = daoDessignConsumItem.findDesignConsumeItemByConsumeId(designConsumeItemId);
	    System.out.println("designConsumeItemDataByOpt (optional type Object) : "+designConsumeItemDataByOpt);
	    modelDesignConsumItem= designConsumeItemDataByOpt.get();
	    System.out.println("modelDesignConsumItem (modelDesignConsumItem type Object) : "+modelDesignConsumItem);
		
		java.util.Date date = new java.util.Date();
	    java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
	    
	    modelItem.setItemId(itemId);
	    modelDesignConsumItem.setModelItem(modelItem);
	    modelDesignConsumItem.setRemarks(remarks);
	    modelDesignConsumItem.setActiveStatus(activeStatus);
	    
	   // modelUOM.setUomId(uomId);
	   // modelDesignConsumItem.setModelUOM(modelUOM);
	    
	    
	    modelUOM.setUomId(uomId);
	    modelDesignConsumItem.setModelUOM(modelUOM);
	    
	    modelDesignConsumItem.setItemQty(itemQty);
	    
	    Long logonUserId=systemUserId;
        ModelUser modelUser = new ModelUser();
        modelUser.setUserId(logonUserId);

	    
	    modelDesignConsumItem.setUpdatedBy(modelUser);
	    modelDesignConsumItem.setUpdateTimestap(entryTime);
	    
	    daoDessignConsumItem.saveDesignConsumeItem(modelDesignConsumItem);
	    
//	    List<ModelDesignCustom> designConsumeList= new ArrayList<ModelDesignCustom>();
//	      designConsumeList=daoDessignConsumItem.getDesignConsumItemListByDesignConItemId(modelDesignConsumItem.getDesignConsumItemId());
//	      
//	      
//	      System.out.println("Controller Section: designConsumeItemList : " +designConsumeList);
//	      
//	      System.out.println("Controller Section: designConsumeItemList Size : " +designConsumeList.size());
//		    
//	        return designConsumeList;
	    
	    
	    List<ModelDesignCustom> allDesignConsumeList= new ArrayList<ModelDesignCustom>();
   	 allDesignConsumeList=daoDessignConsumItem.getAllDesignConsumItemListByDesignId(designId);
        
        
        System.out.println("Controller Section: allDesignConsumeList : " +allDesignConsumeList);
        
        System.out.println("Controller Section: allDesignConsumeList Size : " +allDesignConsumeList.size());
  	    
          return allDesignConsumeList;
	 
		//return null;
	}
 	
 	
 	@RequestMapping(path="/designcontroller/specsave",method=RequestMethod.POST)
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
 	
 	
	@ResponseBody
	@RequestMapping(path="/designcontroller/getalldesignconsumeitemlist", method=RequestMethod.GET)
    public List<ModelDesignCustom> getAllDesignConsumeItemList(@RequestParam(value = "designId")Long designId) {
		
    	System.out.println("designId : " + designId );
    	
    	 List<ModelDesignCustom> allDesignConsumeList= new ArrayList<ModelDesignCustom>();
    	 allDesignConsumeList=daoDessignConsumItem.getAllDesignConsumItemListByDesignId(designId);
         
         
         System.out.println("Controller Section: allDesignConsumeList : " +allDesignConsumeList);
         
         System.out.println("Controller Section: allDesignConsumeList Size : " +allDesignConsumeList.size());
   	    
           return allDesignConsumeList;
           
          // return null;
    }
	
	
	@ResponseBody
	@RequestMapping(path="/designcontroller/getalldesignspeclist", method=RequestMethod.GET)
    public List<ModelDesignCustom> getAllDesignSpecList(@RequestParam(value = "designId")Long designId) {
		
    	System.out.println("designId : " + designId );
    	
    	List<ModelDesignCustom> allDesignSpecList= new ArrayList<ModelDesignCustom>();
    	allDesignSpecList = daoDesignSpec.getAllDesignSpecListByDesignId(designId);
        
        
        System.out.println("Controller Section: allDesignSpecList : " +allDesignSpecList);
        
        System.out.println("Controller Section: allDesignSpecList Size : " +allDesignSpecList.size());
  	    
          return allDesignSpecList;
           
       //  return null;
    }
	
	
	
	
	@ResponseBody
	@RequestMapping(path="/designcontroller/checkSpecForDesign", method=RequestMethod.GET)
    public List<ModelDesignCustom> checkSpecForDesign(@RequestParam(value = "designId")Long designId,@RequestParam(value = "specId")Long specId) {
		
    	System.out.println("designId : " + designId );
    	System.out.println("specId : " + specId );
    	
    	
    	 List<ModelDesignSpec> result= new ArrayList<ModelDesignSpec>();
    	 result=daoDesignSpec.checkDesignSpecForDesignId(designId, specId);
    	 
    	 
         System.out.println("Controller Section: Spec List : " +result);
         System.out.println("Controller Section: Spec List Size : " +result.size());
   	    
          // return allDesignConsumeList;
        
           return null;
    }
	
	
	
	
 // new Implementation  - SAS End
	
	
 // previous Implementation  - SAS
//	@RequestMapping(path="/design/search")
//		public String getAllOwner(Model model,@RequestParam("initial_Buyer") String initial_Buyer,@RequestParam("ultimate_Buyer") String ultimate_Buyer,@RequestParam("inquery_ID") Long inquery_ID,@RequestParam("mail_Id")String mail_Id,@RequestParam("user_inquery_no")Long user_inquery_no,@RequestParam("remarks")String remarks,@RequestParam("user")String user,@RequestParam("inquery_start_date")String inq_start,@RequestParam("inquery_end_date")String inq_end,@RequestParam("mail_start_date")String mail_start,@RequestParam("mail_end_date")String mail_end ) throws ParseException {
//	    	if(inq_start!=null && inq_end!=null && mail_start!=null && mail_end!=null) {
//	    		System.out.println(inq_start+inq_end);
//	    		Date inq_st = new SimpleDateFormat("yyyy-MM-dd").parse(inq_start);
//		    	Date inq_ed=new SimpleDateFormat("yyyy-MM-dd").parse(inq_end);
//		    	Date mail_st=new SimpleDateFormat("yyyy-MM-dd").parse(mail_start);
//		    	Date mail_ed=new SimpleDateFormat("yyyy-MM-dd").parse(mail_end);
//	    		order.getAllOwner(initial_Buyer, ultimate_Buyer,inquery_ID , mail_Id, user_inquery_no, remarks, user,inq_st,inq_ed,mail_st,mail_ed);
//	    	}
//	    	
//    	return "redirect:/design";
//    	
//    			
//    }
	
	
	@RequestMapping(path="/design/save", method = RequestMethod.POST)
	public String designSave(ModelDesign design,@RequestParam("design_Date")String designDate,@RequestParam("FactoryPro.Del.Date")String factoryDate) throws ParseException {
		if(design.getDesignId()==null) {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		design.setEntryTimestamp(entryTime);
		System.out.println(design.getActiveStatus());
		if(designDate!=null) {
			System.out.println(designDate);
			design.setDesignDate(format.parse(designDate));
		}
		if(factoryDate!=null) {
			System.out.println(factoryDate);
			design.setFactPropDeliveryDate(format.parse(factoryDate));
		}
		design.setActive(true);
		design.setActiveStatus(1);
		designImp.saveDesign(design);
		}
		else {
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			design.setEntryTimestamp(entryTime);
			System.out.println(design.getActiveStatus());
			if(designDate!=null) {
				System.out.println(designDate);
				design.setDesignDate(format.parse(designDate));
			}
			if(factoryDate!=null) {
				System.out.println(factoryDate);
				design.setFactPropDeliveryDate(format.parse(factoryDate));
			}
			design.setActive(true);
			design.setActiveStatus(1);
			designImp.saveDesign(design);
		}
		return "redirect:/design";
	}
	@ResponseBody
	@RequestMapping(path="/design/getOrderId/{id}", method=RequestMethod.GET)
	public Optional<ModelOrder> loadEntity(@PathVariable("id") Long id) {
		//System.out.println(id);
	        return order.getOrderById(id);
		}
	@ResponseBody
	@RequestMapping(path="/design/getOrderById/{id}", method=RequestMethod.GET)
	public Optional<ModelDesign> getDesign(@PathVariable("id") Long id) {
		
	        return designImp.getDesignById(id);
		}
	@RequestMapping(path="/design/designSearch/{id}", method=RequestMethod.GET)
    public String getItemByInquery(@PathVariable("id") Long inquery_Id ) {
		
		 System.out.println("/design/designSearch/{id}");
		designImp.getDesignFromInquery(inquery_Id);
		
		return "redirect:/design";
    }
	
	@RequestMapping(path="/url")
	public @ResponseBody String deleteQuestions(@RequestParam("arr") String tdValues) {
	
		System.out.println("123");
		
		return "design_table";
	 
	}
	
	@ResponseBody
    @RequestMapping(value = "/design/consumeItem")

   // public String getConsumeItemAjax(@RequestParam(value = "itemQty") String id,@RequestParam(value = "remarks")String remarks,@RequestParam(value = "item")String item) {
		public String getConsumeItemAjax(@RequestParam(value = "itemQty") String id,@RequestParam(value = "remarks")String remarks,@RequestParam(value = "item")String item,@RequestParam(value = "designId") Long designId) {
        System.out.println("come to ajax"+ id+remarks+item);
        Long itemQty=Long.parseLong(id);
        
      //  consumeItem.saveConsume(itemQty, remarks);
        
        consumeItem.saveConsume(designId,itemQty, remarks);
        
        return "hello";

    }
	
	
	/*
	 * 
	 * 
	@ResponseBody
    @RequestMapping(value = "/design/specificationItem")
	//public String getSpecificationItemAjax(@RequestParam(value = "specificationValue") String id,@RequestParam(value = "remarks")String remarks,@RequestParam(value = "item")String item) {
    public String getSpecificationItemAjax(@RequestParam(value = "specificationValue") String specValue,@RequestParam(value = "remarks")String remarks,@RequestParam(value = "item")String item,@RequestParam(value = "designId")Long designId) {
        System.out.println("come to ajax /n specValue : "+ specValue+"remarks :"+remarks+""+item);
        
      //  specItem.saveSpecification(id, remarks, specItem.getDesignId());
        specItem.saveSpecification(specValue, remarks,designId);
        
        
        return "hello";

    }
    
    */
    
	/*
	@ResponseBody
	@RequestMapping(path="/designController/designId/{id}")
    public Long getDesignId(@PathVariable("id")Long id) {
		specItem.setDesignId(id);
    	System.out.println("id " + id);
    	
    	return id;
    }
    
    */
	
	
	@ResponseBody
	@RequestMapping(path="/designController/designId/{id}", method=RequestMethod.GET)
    public Long getDesignId(@PathVariable("id") Long id) {
		this.specItem.setDesignId(id);
    	System.out.println("id test " + id );
    	
    	return id;
    }
    
 // this code add SAS -25-02-2019
	@RequestMapping(path="/designcontroller/getdesignspecdatabydesignid/{id}", method=RequestMethod.GET)
    public String getDesignSpecDataByDesignId(@PathVariable("id") Long designId ) {
		
		System.out.println("getDesignSpecDataByDesignId " + designId );
		daoDesignSpec.getDesignSpecDataByDesignId(designId);
		System.out.println(" Design Spec List "+daoDesignSpec.getDesignSpecDataByDesignId(designId) );
		
		System.out.println("getDesignSpecDataByDesignId " + designId );
		daoDessignConsumItem.getDesignConsumItemList(designId);
		System.out.println(" Design Consum Item List "+daoDessignConsumItem.getDesignConsumItemList(designId) );
		
		
		return "redirect:/design";
    }
	// this code add SAS -25-02-2019
	
}
