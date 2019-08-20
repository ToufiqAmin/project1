package com.biziitech.mlfm.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelProductionCustom;
import com.biziitech.mlfm.dao.DaoMachineShift;
import com.biziitech.mlfm.dao.DaoPOConsumItem;
import com.biziitech.mlfm.dao.DaoProduction;
import com.biziitech.mlfm.dao.DaoProductionPlan;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoMachineImp;
import com.biziitech.mlfm.daoimpl.DaoMachineShiftImp;
import com.biziitech.mlfm.daoimpl.DaoMachineTypeImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelMachine;
import com.biziitech.mlfm.model.ModelMachineShift;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelOrder;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPO;
import com.biziitech.mlfm.model.ModelPOConsumItem;
import com.biziitech.mlfm.model.ModelPOMst;
import com.biziitech.mlfm.model.ModelProduction;
import com.biziitech.mlfm.model.ModelProductionPlanChd;
import com.biziitech.mlfm.model.ModelProductionPlanMst;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.model.ModelWOItemQtySpec;
import com.biziitech.mlfm.model.ModelWashing;
import com.biziitech.mlfm.repository.POConsumItemRepository;
import com.biziitech.mlfm.repository.ProductionRepository;

@Controller
public class ProductionController {
	
	@Autowired
	private DaoOrderOwnerTypeImp ownerType;
	
	@Autowired
	private DaoOrderOwnerImp orderOwner;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoProduction daoProduction;
	
	@Autowired
	private DaoMachineTypeImp daoMachineTypeImp;
	
	@Autowired
	private DaoMachineImp daoMachineImp;
	
	
	@Autowired
	private DaoMachineShiftImp DaoMachineShift;
	
	@Autowired
	private ProductionRepository productionRepository;
	
	@Autowired
	private DaoPOConsumItem daoPOConsumItem;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
	
	@RequestMapping(path="/production/{userId}")
	public String init(@PathVariable Long userId,Model model) {
		try {
			
			
			
			
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
					
			
			
			
			
			
			
		    List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();			
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwner();
			model.addAttribute("ownerList",ownerList);
			
			List<ModelMachineType> machineTypeList= daoMachineTypeImp.getMachineName();
			//System.out.println("machine id "+machineTypeList);
			model.addAttribute("machineTypeList",machineTypeList);
	        
		    //model.addAttribute("productionPlan", modelProductionPlan);
		return "production";
		}
		catch (Exception e) {
			 e.printStackTrace();
				 //System.out.println("Error at URL : " );
			 return "production"; 
		}
		
	}
	
	
	/*
	   * order Type : inquiry(Drop down selected option ) and checked : New/Pending Production
	   * 
	   * creator : sas
	   * 
	   * */
		@ResponseBody
		@RequestMapping(path="/productioncontroller/getorderdatanotdoneproduction")
		public List<ModelInquiryList> getOrderDataNotDoneProduction(Model model,ModelProductionPlanMst modelProductionPlanMst,
			
				  @RequestParam("buyerId") String buyerId,
		          @RequestParam("ultimateBuyerId") String ultimateBuyerId,
		          @RequestParam("inqueryId") Long inqueryId,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqFromDate") Date inqFromDate,
		          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqToDate") Date inqToDate){
			
			System.out.println("\n Select Inquiry and checked New/Pending Production \n");
			
			System.out.println("Ajax data sent " + "\n initial buyer :" +buyerId+
					"\n ultimate buyer Id :" + ultimateBuyerId+ 
					"\n Inquiry Id :" + inqueryId+ 
					"\n Inq Strat Date :" + inqFromDate+
					"\n Inq End Date :" + inqToDate);
			
			List<ModelInquiryList> modelOrderList = daoProduction.getOrderListPlanned(buyerId,ultimateBuyerId,inqueryId,inqFromDate,inqToDate);
			
			System.out.println("Order List size : "+ modelOrderList.size() );
			System.out.println("Order List : "+ modelOrderList );
			
		 return modelOrderList;
		
		}
		
		
		
		/*
		   * order Type : inquiry(Drop down selected option ) and Unchecked : New/Pending Production
		   * 
		   * creator : sas
		   * 
		   * */
			@ResponseBody
			@RequestMapping(path="/production/getOrderDataNewPandingProductionUnchecked")
			public List<ModelInquiryList> getOrderDataNewPandingProductionUnchecked(Model model,ModelProductionPlanMst modelProductionPlanMst,
					
					  @RequestParam("buyerId") String buyerId,
			          @RequestParam("ultimateBuyerId") String ultimateBuyerId,
			          @RequestParam("inqueryId") Long inqueryId,
			          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqFromDate") Date inqFromDate,
			          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("inqToDate") Date inqToDate){
				
				System.out.println("\n Select Inquiry and Unchecked Planned \n");
				
				System.out.println("Ajax data sent " + "\n initial buyer :" +buyerId+ "\n ultimate buyer Id :" + ultimateBuyerId+  "\n Inquiry Id :" + inqueryId+ "\n Inq Strat Date :" + inqFromDate+"\n inq End Date :" + inqToDate);
				List<ModelInquiryList> modelOrderList = daoProduction.getOrderListNotPlanned(buyerId,ultimateBuyerId,inqueryId,inqFromDate,inqToDate);
				
				
			return modelOrderList;
			
			}
			
			
			/*
			   * order Type : Work Order(Drop down selected option ) and checked : New/Pending Production
			   * 
			   * creator : sas
			   * 
			   * */
				@ResponseBody
				@RequestMapping(path="/productioncontroller/getsearchdatapendingproduction")
				public List<ModelProductionCustom> getSearchDataPendingProduction(Model model,ModelProductionPlanMst modelProductionPlanMst,
						
						  @RequestParam("orderOwnerId") Long orderOwnerId,
				          @RequestParam("wOId") Long wOId,
				          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOStartDate") Date pOStartDate,
				          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOEndDate") Date pOEndDate){
					
					System.out.println("\n Select Work Order and checked New/Pending Production \n");
					
					System.out.println("Ajax data sent " +
					        "\n initial buyer :" +orderOwnerId+
							"\n Inquiry Id :" + wOId+ 
							"\n Plan Strat Date :" + pOStartDate+
							"\n plan End Date :" + pOEndDate);
					
					List<ModelProductionCustom> searchList = daoProduction.getSearchListPending(orderOwnerId,wOId,pOStartDate,pOEndDate);
					
					System.out.println(" WO LIST " +searchList.size());
					
				return searchList;
				
				}
				
				/*
				   * order Type : Work Order(Drop down selected option ) and Unchecked : New/Pending Production(production done)
				   * 
				   * creator : sas
				   * 
				   * */
				
				
					@ResponseBody
					@RequestMapping(path="/productioncontroller/getsearchdatadoneproduction")
					public List<ModelProductionCustom> getSearchDataDoneProduction(Model model,ModelProductionPlanMst modelProductionPlanMst,
							
							  @RequestParam("orderOwnerId") Long orderOwnerId,
					         // @RequestParam("ultimateBuyerId") String ultimateBuyerId,
					          @RequestParam("wOId") Long wOId,
					          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOStartDate") Date pOStartDate,
					          @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("pOEndDate") Date pOEndDate){
						
						System.out.println("\n Select Work Order and Unchecked Planned \n");
						
						System.out.println("Ajax data sent " + 
						        "\n initial buyer :" +orderOwnerId+
								"\n Inquiry Id :" + wOId+ 
								"\n Plan Strat Date :" + pOStartDate+
								"\n plan End Date :" + pOEndDate);
						
						List<ModelProductionCustom> modelOrderList = daoProduction.getSearchListDoneProduction(orderOwnerId,wOId,pOStartDate,pOEndDate);
						
						System.out.println(" WO LIST " +modelOrderList.size());
						
					return modelOrderList;
					
					}
					
					
					@RequestMapping(path="/productioncontroller/saveproduction", method = RequestMethod.POST)
					@ResponseBody
						public List<ModelProductionCustom> saveProduction(Model model,ModelProduction modelProduction,
					//public String saveProduction(Model model,ModelProduction modelProduction,
								ModelMachineShift modelMachineShift,
								ModelOrderItemQty modelOrderItemQty, 
								ModelProductionPlanChd modelProductionPlanChd,
								ModelPOMst modelPOMst,
								ModelDesign modelDesign,
								ModelWOChd modelWOChd,
								ModelOrder modelOrder,
								@RequestParam("pOMstId") Long pOMstId,
								@RequestParam("orderItemQtyId") Long orderItemQtyId,
								@RequestParam("productionPlanChdId") Long productionPlanChdId,
								@RequestParam("machineShiftId") Long machineShiftId,
								@RequestParam("designId") Long designId,
								@RequestParam("orderId") Long orderId,
								@RequestParam("wOChdId") Long wOChdId,
								@RequestParam("productionRefNo") String productionRefNo,
								@RequestParam("productionQty") Double productionQty,
								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
								@RequestParam("remarks") String remarks,
								@RequestParam("activeStatus") int activeStatus,
								
								@RequestParam("noOfStitches") Integer noOfStitches
								)
						
						{
						System.out.println(" WO LIST " +activeStatus);
					//	System.out.println(" WO LIST " +orderItemQtyId+" WO LIST " +productionPlanChdId+" WO LIST " +machineShiftId+" WO LIST " +productionQty+" WO LIST " +productionDate+" WO LIST " +remarks+" WO LIST " +activeStatus);
						
						java.util.Date date = new java.util.Date();
						java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
						
						modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
						modelProduction.setModelOrderItemQty(modelOrderItemQty);
						
						
						modelMachineShift.setMachineShiftId(machineShiftId);
						modelProduction.setModelMachineShift(modelMachineShift);
						
						modelProductionPlanChd.setProductionPlanChdId(productionPlanChdId);
						modelProduction.setModelProductionPlanChd(modelProductionPlanChd);
						
						modelPOMst.setpOMstId(pOMstId);
						modelProduction.setModelPOMst(modelPOMst);
						
						modelWOChd.setWoChdId(wOChdId);
						modelProduction.setModelWOChd(modelWOChd);
						
						modelDesign.setDesignId(designId);
						modelProduction.setModelDesign(modelDesign);
						
						modelOrder.setOrderId(orderId);
						modelProduction.setModelOrder(modelOrder);
						
						
						modelProduction.setProductionDate(productionDate);
						
						modelProduction.setProductionRefNo(productionRefNo);
						
						modelProduction.setProductionQty(productionQty);
						
						modelProduction.setRemarks(remarks);
						
						modelProduction.setActiveStatus(activeStatus);
						
						modelProduction.setNoOfStitches(noOfStitches);
						
						Long logonUserId=systemUserId;
						ModelUser modelUser1 = new ModelUser();
					    modelUser1.setUserId(logonUserId);
						
						modelProduction.setEnteredBy(modelUser1);
						modelProduction.setEntryTimeStamp(entryTime);
						
						
						daoProduction.saveProduction(modelProduction);
					
						List<ModelProductionCustom> modelProductionCustomList= new ArrayList<ModelProductionCustom>();
						modelProductionCustomList = daoProduction.getProductionById(modelProduction.getProductionId());
						
						System.out.println(" controller : Model Production  find by Id : " + modelProductionCustomList);
						
						
						
						return modelProductionCustomList;
						
						//return "";
						}

                 
					
					
					@RequestMapping(path="/productioncontroller/saveeditproduction", method = RequestMethod.POST)
					@ResponseBody
						public List<ModelProductionCustom> saveEditProduction(Model model,ModelProduction modelProduction,
					            @RequestParam("productionId") Long productionId,
								@RequestParam("productionRefNo") String productionRefNo,
								@RequestParam("productionQty") Double productionQty,
								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
								@RequestParam("remarks") String remarks,
								@RequestParam("activeStatus") int activeStatus,
								@RequestParam("noOfStitches") Integer noOfStitches
								)
						
						{
						
						java.util.Date date = new java.util.Date();
						java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
						
						Optional<ModelProduction> modelProductionOpt = daoProduction.findProductiontById(productionId);
						modelProduction=modelProductionOpt.get();
						
						modelProduction.setProductionDate(productionDate);
						
						modelProduction.setProductionRefNo(productionRefNo);
						
						modelProduction.setProductionQty(productionQty);
						
						modelProduction.setRemarks(remarks);
						
						modelProduction.setActiveStatus(activeStatus);
						
						modelProduction.setNoOfStitches(noOfStitches);
						
						Long logonUserId=systemUserId;
						 ModelUser modelUser1 = new ModelUser();
					        modelUser1.setUserId(logonUserId);
						
						modelProduction.setUpdatedBy(modelUser1);
						modelProduction.setUpdateTimeStamp(entryTime);
						
						
						daoProduction.saveProduction(modelProduction);
					
						List<ModelProductionCustom> modelProductionCustomList= new ArrayList<ModelProductionCustom>();
						modelProductionCustomList = daoProduction.getProductionById(modelProduction.getProductionId());
						
						System.out.println(" controller : Model Production  find by Id : " + modelProductionCustomList);
						
						
						
						return modelProductionCustomList;
						
						//return "";
						}
					
					

					@RequestMapping(path="/productioncontroller/getproductionlist", method = RequestMethod.GET)
					@ResponseBody
					public List<ModelProductionCustom> getProductionList(Model model,
							@RequestParam("productionId") Long productionId){
						
						System.out.println("production Id : " +productionId);
						
						List<ModelProductionCustom> productionList = daoProduction.getProductionByIdForproductionEdit(productionId);
					
						
						return productionList;
					}
					
					@RequestMapping(path="/productioncontroller/getproductiondonelist", method = RequestMethod.GET)
					@ResponseBody
					public List<ModelProductionCustom> getProductionDoneList(Model model,
							@RequestParam("pOMstId") Long pOMstId){
						
						System.out.println("pOMstId Id : " +pOMstId);
						
						List<ModelProductionCustom> productionList = daoProduction.getProductionByPOMstId(pOMstId);
					
						
						return productionList;
					}
					
					
					@RequestMapping(path="/production/updateproduction", method = RequestMethod.POST)
					@ResponseBody
					public String updateProduction(Model model,ModelProduction modelProduction,
							@RequestParam("productionId") Long productionId,
							@RequestParam("productionQty") Double productionQty,
							@RequestParam("productionRemarks") String productionRemarks){
						
						
						
						System.out.println("production Id : "+productionId+" productionQty  : "+productionQty+" productionRemarks  : "+productionRemarks);
						
						
//						Optional<ModelProduction> modelProductionOpt = daoProduction.findProductiontById(productionId);
//						System.out.println("Before Update ModelProduction Optional type : " +modelProductionOpt);
//						
//                        modelProduction=modelProductionOpt.get();
//						System.out.println("Before Update ModelProduction : " +modelProduction);
//						
//						modelProduction.setProductionQty(productionQty);
//						System.out.println("Before Update ModelProduction when set production Qty  : " +modelProduction);
//						daoProduction.saveProduction(modelProduction);
						
						
						
//						productionRepository.save(modelProduction);
//						System.out.println("After Update ModelProduction : " +modelProduction);
						return "production";
					}
					
					
					@RequestMapping(path="/productioncontroller/getpoconsumitemdetailsbypomstId", method = RequestMethod.GET)
					@ResponseBody
					public List<ModelPOConsumItem> getPOConsumItemDetailsByPOMstId(Model model,
							@RequestParam("pOMstId") Long pOMstId){
						
						System.out.println("pOMstId : " +pOMstId);
						
						List<ModelPOConsumItem> pOConsumItemList =	daoPOConsumItem.getPOConsumItemDetailsByPOMstId(pOMstId);
						
						System.out.println("pO Consum Item List : " +pOConsumItemList);
						
						return pOConsumItemList;
					}
					
					
					/*
					   * plan Details 
					   * 
					   * creator : sas
					   * 
					   * */
						@ResponseBody
						@RequestMapping(path="/production/getPlanDetails")
						public List<ModelProductionCustom> getPlanDetails(Model model,
								@RequestParam("orderItemQtyId") Long orderItemQtyId){
							
							System.out.println("Ajax data sent " + "\n orderItemQtyId :" +orderItemQtyId);
							
							List<ModelProductionCustom> planDetailsList = daoProduction.getPlanDetails(orderItemQtyId);
							
							System.out.println("Plan Details List Size : " +planDetailsList.size());
							System.out.println("Plan Details List : " +planDetailsList);
							
						return planDetailsList;
						}
					
						/* 
						@RequestMapping(path="/production/saveModalProduction", method = RequestMethod.POST)
						public String saveModalProduction(Model model,ModelProduction modelProduction,ModelMachineShift modelMachineShift,
								ModelOrderItemQty modelOrderItemQty, 
								@RequestParam("orderItemQtyId") Long orderItemQtyId,
								@RequestParam("productionQty") Double productionQty,
								@RequestParam("machineShiftId") Long machineShiftId,
								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
								@RequestParam("remarks") String remarks){
							
							
							//System.out.println("Ajax data received " + "\n orderItemQtyId :" +orderItemQtyId+ "\n productionQty :" +productionQty
								//	+ "\n machineShiftId :" +machineShiftId+ "\n productionDate :" +productionDate+ "\n designId :" +designId+ "\n productionPlanChdId :" +productionPlanChdId);
							
							
							java.util.Date date = new java.util.Date();
							java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
							
							modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
							modelProduction.setModelOrderItemQty(modelOrderItemQty);
							
							
							modelMachineShift.setMachineShiftId(machineShiftId);
							modelProduction.setModelMachineShift(modelMachineShift);
							
							modelProduction.setProductionDate(productionDate);
							
							modelProduction.setProductionQty(productionQty);
							
							modelProduction.setRemarks(remarks);
							
							modelProduction.setActiveStatus(1);
							modelProduction.setEnteredBy(1L);
							modelProduction.setEntryTimeStamp(entryTime);
							
							
							daoProduction.saveProduction(modelProduction);
							
							
							return "redirect:/production";
						}
						*/
						
						
					@RequestMapping(path="/production/saveModalProduction", method = RequestMethod.POST)
					@ResponseBody
						public List<ModelProductionCustom> saveModalProduction(Model model,ModelProduction modelProduction,
								ModelMachineShift modelMachineShift,
								ModelWOChd modelWOChd, 
								ModelOrderItemQty modelOrderItemQty, 
								@RequestParam("orderItemQtyId") Long orderItemQtyId,
								@RequestParam("wOChdId") Long wOChdId,
								@RequestParam("productionQty") Double productionQty,
								@RequestParam("machineShiftId") Long machineShiftId,
								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
								@RequestParam("remarks") String remarks)
						
//						public List<ModelProduction> saveModalProduction(Model model,ModelProduction modelProduction,ModelMachineShift modelMachineShift,
//								ModelOrderItemQty modelOrderItemQty, 
//								@RequestParam("orderItemQtyId") Long orderItemQtyId,
//								@RequestParam("productionQty") Double productionQty,
//								@RequestParam("machineShiftId") Long machineShiftId,
//								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
//								@RequestParam("remarks") String remarks)
						
//						public String saveModalProduction(Model model,ModelProduction modelProduction,ModelMachineShift modelMachineShift,
//								ModelOrderItemQty modelOrderItemQty, 
//								@RequestParam("orderItemQtyId") Long orderItemQtyId,
//								@RequestParam("productionQty") Double productionQty,
//								@RequestParam("machineShiftId") Long machineShiftId,
//								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
//								@RequestParam("remarks") String remarks)
						{
							
							
							//System.out.println("Ajax data received " + "\n orderItemQtyId :" +orderItemQtyId+ "\n productionQty :" +productionQty
								//	+ "\n machineShiftId :" +machineShiftId+ "\n productionDate :" +productionDate+ "\n designId :" +designId+ "\n productionPlanChdId :" +productionPlanChdId);
							
						Long x = wOChdId;
						if(x!=null) 
						{
							modelWOChd.setWoChdId(wOChdId);
							modelProduction.setModelWOChd(modelWOChd);
						}
						else 
						{
							modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
							modelProduction.setModelOrderItemQty(modelOrderItemQty);
						}
						
							
							java.util.Date date = new java.util.Date();
							java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
							
							//modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
							//modelProduction.setModelOrderItemQty(modelOrderItemQty);
							
							
							modelMachineShift.setMachineShiftId(machineShiftId);
							modelProduction.setModelMachineShift(modelMachineShift);
							
							modelProduction.setProductionDate(productionDate);
							
							modelProduction.setProductionQty(productionQty);
							
							modelProduction.setRemarks(remarks);
							
							modelProduction.setActiveStatus(1);
							
							Long logonUserId=systemUserId;
							 ModelUser modelUser1 = new ModelUser();
						        modelUser1.setUserId(logonUserId);
						        
							modelProduction.setEnteredBy(modelUser1);
							modelProduction.setEntryTimeStamp(entryTime);
							
							System.out.println("Before saved ModelProduction : " + modelProduction);
							daoProduction.saveProduction(modelProduction);
							
							System.out.println("Production Id : " + modelProduction.getProductionId());
							System.out.println("Production Date : " + modelProduction.getProductionDate());
							System.out.println("Production qty : " + modelProduction.getProductionQty());
							/*
							Optional<ModelProduction> modelProductionOpt = productionRepository.findById(modelProduction.getProductionId());
							modelProduction=modelProductionOpt.get();
							
							System.out.println(" Model Production After Save : " + modelProduction);
							
							List<ModelProduction> modelProductionList= new ArrayList<ModelProduction>();
							modelProductionList.add(modelProduction);
							
							System.out.println(" After Save modelProductionList : " + modelProductionList);
							//System.out.println(" machine name : " + modelProduction.getModelMachineShift().getModelMachine().getMachineName());
							System.out.println(" Production Qty : " + modelProduction.getProductionQty());
							
							return modelProductionList;
							*/
							
							
							
							List<ModelProductionCustom> modelProductionCustomList= new ArrayList<ModelProductionCustom>();
							modelProductionCustomList = daoProduction.getProductionById(modelProduction.getProductionId());
							
							System.out.println(" controller : Model Production  find by Id : " + modelProductionCustomList);
							
							
							
							return modelProductionCustomList;
							
							
							//System.out.println("After saved ModelProduction : " + modelProduction);
							//return "production";
						}
						
						
						
						@RequestMapping(path="/production/saveProduction", method = RequestMethod.POST)
						@ResponseBody
//						public List<ModelProduction> saveProduction(Model model,ModelProduction modelProduction,
//								ModelOrderItemQty modelOrderItemQty,ModelMachineShift modelMachineShift,ModelDesign modelDesign,
//								ModelProductionPlanChd modelProductionPlanChd,
//								@RequestParam("orderItemQtyId") Long orderItemQtyId,
//								@RequestParam("productionQty") Double productionQty,
//								@RequestParam("machineShiftId") Long machineShiftId,
//								@RequestParam("designId") Long designId,
//								@RequestParam("productionPlanChdId") Long productionPlanChdId,
//								@RequestParam("remarks") String remarks,
//								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate)
						public List<ModelProductionCustom> saveProduction(Model model,ModelProduction modelProduction,
								//public String saveProduction(Model model,ModelProduction modelProduction,
								ModelOrderItemQty modelOrderItemQty,ModelMachineShift modelMachineShift,ModelDesign modelDesign,
								ModelProductionPlanChd modelProductionPlanChd,ModelWOChd modelWOChd,
								@RequestParam("orderItemQtyId") Long orderItemQtyId,
								@RequestParam("productionQty") Double productionQty,
								@RequestParam("machineShiftId") Long machineShiftId,
								@RequestParam("designId") Long designId,
								@RequestParam("productionPlanChdId") Long productionPlanChdId,
								@RequestParam("remarks") String remarks,
								@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam("productionDate") Date productionDate,
								@RequestParam("wOChdId") Long wOChdId){
							
							
							System.out.println("wo chd Id " +wOChdId);
							
							System.out.println("Ajax data received " + "\n orderItemQtyId :" +orderItemQtyId+ "\n productionQty :" +productionQty
									+ "\n machineShiftId :" +machineShiftId+ "\n productionDate :" +productionDate+ "\n designId :" +designId+ "\n productionPlanChdId :" +productionPlanChdId);
							
							Long x =wOChdId;
							if(x!=null) 
							{
								System.out.println("in if controller"+x);
								
								modelWOChd.setWoChdId(wOChdId);
								modelProduction.setModelWOChd(modelWOChd);
								
							}
							else 
							{
								
								modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
								modelProduction.setModelOrderItemQty(modelOrderItemQty);
								//System.out.println("in else controller" + x);
							}
							
							java.util.Date date = new java.util.Date();
							java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
							
							modelProduction.setProductionQty(productionQty);
							modelProduction.setProductionDate(productionDate);
							
							//modelOrderItemQty.setOrderItemQtyId(orderItemQtyId);
							//modelProduction.setModelOrderItemQty(modelOrderItemQty);
							
							modelMachineShift.setMachineShiftId(machineShiftId);
							modelProduction.setModelMachineShift(modelMachineShift);
							
//							modelWOChd.setWoChdId(wOChdId);
//							modelProduction.setModelWOChd(modelWOChd);
							
							//modelDesign.setDesignerId(designId);
							//modelProduction.setModelDesign(modelDesign);
							
							modelProductionPlanChd.setProductionPlanChdId(productionPlanChdId);
							modelProduction.setModelProductionPlanChd(modelProductionPlanChd);
							
							modelProduction.setRemarks(remarks);
							
							modelProduction.setActiveStatus(1);
							System.out.println("Active Status : 1"); 
							
							Long logonUserId=systemUserId;
							 ModelUser modelUser1 = new ModelUser();
						        modelUser1.setUserId(logonUserId);
							
							modelProduction.setEnteredBy(modelUser1);
							System.out.println("Entered By : 1"); 
							
							modelProduction.setEntryTimeStamp(entryTime);
							System.out.println("Entry Time Stamp : " +entryTime); 
							
					
							System.out.println("Before saved ModelProduction" + modelProduction);
							daoProduction.saveProduction(modelProduction);
							//System.out.println(" production id " + modelProduction.getModelMachineShift().getModelMachine().getMachineName());
							System.out.println(" production id " + modelProduction.getProductionId());
							
						
							//productionRepository.findById(modelProduction.getProductionId());
							//Optional<ModelProduction> modelProductionOpt = productionRepository.findById(modelProduction.getProductionId());
							//modelProduction =modelProductionOpt.get();
							//System.out.println(" Model Production After Save : " + modelProduction);
							//System.out.println(" machine name " + modelProduction.getModelMachineShift().getModelMachine().getMachineName());
							
							
							
							final Long id;
							id= modelProduction.getProductionId();
							System.out.println("Final Id : " + id);
							
							List<ModelProductionCustom> modelProductionCustomList= new ArrayList<ModelProductionCustom>();
							modelProductionCustomList = daoProduction.getProductionById(id);
							System.out.println(" Model Production  find by Id : " + modelProductionCustomList);
							
							
							//modelProductionList.add(modelProduction);
							//return modelProductionList;
							
							
							
							return modelProductionCustomList;
							
							
							//return "";
						}
						
						
						
						
						
						
					
	
	
	@RequestMapping(path="/production/owner")
	@ResponseBody
	public List<ModelOrderOwner> findOwner(@RequestParam("ownerType")Long typeId){
		List<ModelOrderOwner> modelOrderOwnerList = orderOwner.getOwnerByTypeInOrder(typeId);
		System.out.println("Size:" +modelOrderOwnerList.size());
		return orderOwner.getOwnerByTypeInOrder(typeId);
	}
	
	
	@ResponseBody
    @RequestMapping(path="/production/machine/type")
	public List<ModelMachine> findMachineNames(@RequestParam("machine")Long id){
   
        return daoMachineImp.getMachineByType(id);
	} 
	
	
	@RequestMapping(path="/production/machine/shift/type")
	@ResponseBody
	public List<ModelMachineShift> findMachineShiftName(@RequestParam("machineId")Long id){
    	System.out.println(id);
		return DaoMachineShift.getMachineShiftByName(id);
	}
	
	

}
