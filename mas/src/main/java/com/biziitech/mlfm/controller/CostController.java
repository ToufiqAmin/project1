package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.biziitech.mlfm.custom.model.ModelPIChdOrder;
import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelPIMstCustom;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.custom.model.ModelWOInquiryData;
import com.biziitech.mlfm.dao.DaoCostCalculation;
import com.biziitech.mlfm.dao.DaoDesign;
import com.biziitech.mlfm.dao.DaoItem;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoDesignCostImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerImp;
import com.biziitech.mlfm.daoimpl.DaoOrderOwnerTypeImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelCostHead;
import com.biziitech.mlfm.model.ModelDesign;
import com.biziitech.mlfm.model.ModelDesignCost;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelOrderOwner;
import com.biziitech.mlfm.model.ModelOrderOwnerType;
import com.biziitech.mlfm.model.ModelPIChd;
import com.biziitech.mlfm.model.ModelPIMst;
import com.biziitech.mlfm.model.ModelPurchasePrice;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.model.ModelWOChd;
import com.biziitech.mlfm.repository.CostHeadRepository;
import com.biziitech.mlfm.repository.CurrencyRepository;
import com.biziitech.mlfm.repository.PurchasePriceRepository;

@Controller
public class CostController {
	
	
	@Autowired
	private DaoOrderOwnerTypeImp daoOrderOwnerTypeImp;
	
	@Autowired
	private DaoOrderOwnerImp daoOrderOwnerImp;
	
	@Autowired
	private DaoItem daoItem;
	
	@Autowired
	private DaoCostCalculation daoCostCalculation;

    @Autowired
	private DaoDesign daoDesign; 
    
	@Autowired
	private DaoUserImp daoUserImp;
	
	@Autowired
	private PurchasePriceRepository purchasePriceRepository;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private CostHeadRepository costHeadRepositoy;
	
	@Autowired
	private DaoDesignCostImp daoDesignCostImp;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


    private Long systemUserId;
	
    
	@RequestMapping(path="/cost/init/{userId}", method = RequestMethod.GET)
	public String createCost(@PathVariable Long userId,Model model) {
		
		
		
		
		
		
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
		
		
		
		
		
		
		 
		List<ModelOrderOwnerType> ownerTypeList= daoOrderOwnerTypeImp.getTypeName();			
        model.addAttribute("ownerTypeList",ownerTypeList);
        
		List<ModelOrderOwner> ownerList= daoOrderOwnerImp.getAllOwnerName();
		model.addAttribute("ownerList",ownerList);
		
		List<ModelItem> modelItemList= daoItem.getItemListActive();
		model.addAttribute("modelItemList", modelItemList);
				
		List<ModelDesign> modelDesignList= daoDesign.getDesignListActive();
		model.addAttribute("modelDesignList", modelDesignList);
		
		List<ModelUser> userList= daoUserImp.getAllUSerName();
		model.addAttribute("userList",userList);
		
		List<ModelCostHead> cosList= costHeadRepositoy.findCostHead();
		model.addAttribute("costHeadList",cosList);
		
		List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
		currencyList=currencyRepository.findCurrency();
		
		return "cost_calculation";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/search/data", method = RequestMethod.GET)
	     public List<ModelWOInquiryData>  getAllDataWithoutCalculate(Model model,@RequestParam("ownerType")Long ownerType,@RequestParam("owner")String owner,
	    		 @RequestParam("ultimateOwner")String ultimateOwner,@RequestParam("inqid")Long inquiryId,@RequestParam("designid")Long designId,@RequestParam("userdefinedno")Long userDefinedNo,
	    		 @RequestParam("designName")String designName,Long designBy,Long itemName,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="startDate")Date startDate,
			       @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="endDate")Date endDate){
	    	   
		        List<ModelWOInquiryData> searchList=new ArrayList<ModelWOInquiryData>();
		        
		        searchList=daoCostCalculation.getSearchData(ownerType,owner,ultimateOwner,inquiryId,designId,userDefinedNo,designName,designBy,itemName,startDate,endDate);
		        
		        System.out.println(searchList.size());
				      		
                return searchList;			               	                     
			    	
			    }
	
	
	@ResponseBody
	@RequestMapping(value="/search/data/calculate", method = RequestMethod.GET)
	     public List<ModelWOInquiryData>  getAllDataWithCalculate(Model model,@RequestParam("ownerType")Long ownerType,@RequestParam("owner")String owner,
	    		 @RequestParam("ultimateOwner")String ultimateOwner,@RequestParam("inqid")Long inquiryId,@RequestParam("designid")Long designId,@RequestParam("userdefinedno")Long userDefinedNo,
	    		 @RequestParam("designName")String designName,Long designBy,Long itemName,@DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="startDate")Date startDate,
			       @DateTimeFormat(pattern="yyyy-MM-dd")@RequestParam(value="endDate")Date endDate){
	    	   
		        List<ModelWOInquiryData> searchList=new ArrayList<ModelWOInquiryData>();
		        
		        searchList=daoCostCalculation.getSearchDataWithCalculate(ownerType,owner,ultimateOwner,inquiryId,designId,userDefinedNo,designName,designBy,itemName,startDate,endDate);
		        
		        System.out.println(searchList.size());
				      		
                return searchList;			               	                     
			    	
			    }
	
	    @ResponseBody
	    @RequestMapping(value = "/consumt/item/search", method=RequestMethod.GET)
	    public List<ModelWOInquiryData> getConsumItemData(@RequestParam("id") Long id) {
		   
     	System.out.println("Design Id :" + id);
     	
     	List<ModelWOInquiryData> designList= new ArrayList<ModelWOInquiryData>(); 
     	
     	List<ModelWOInquiryData> resultList=daoCostCalculation.getDataByDesignId(id);
     	  	
     	List<ModelUOMCustom> uOMList= new ArrayList<ModelUOMCustom>(); 
     	uOMList=daoUOMImp.getUOMListCustom();
     	
     	List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
		currencyList=currencyRepository.findCurrency();
     	
     	
     	for(ModelWOInquiryData m:resultList ) {
    		m.setModelUOMCustomList(uOMList);
    		m.setModelCurrencyList(currencyList);
    		designList.add(m);
    		
        	}
     	
		   
	        return designList;
		}
	    
	    @ResponseBody
	    @RequestMapping(value = "/purchase/price/search", method=RequestMethod.GET)
	    public List<ModelPurchasePrice> getPurchasePrice(@RequestParam("id") Long uOMId) {
		   
	    	List<ModelPurchasePrice> priceList= new ArrayList<ModelPurchasePrice>(); 
	     	
	     	priceList=purchasePriceRepository.findPriceByUOM(uOMId);
		   
	        return priceList;
		}
	    
	    @ResponseBody
	    @RequestMapping(value = "/cost/head/search", method=RequestMethod.GET)
	    public List<ModelCostHead> getCostHeadData(@RequestParam("id") Long id) {
		   
	    	List<ModelCostHead> costHeadDataList= new ArrayList<ModelCostHead>(); 
	    	costHeadDataList=costHeadRepositoy.findCostHeadById(id);
	    	
		   List<ModelCurrency> modelCurrencyList= new ArrayList<ModelCurrency>();
		   modelCurrencyList= currencyRepository.findCurrency();
		   
		   for(ModelCostHead costHeadData:costHeadDataList ) {
		   costHeadData.setModelCurrencyList(modelCurrencyList);
		   }
		   
		   
	        return costHeadDataList;
		}
	    
	    
	    @RequestMapping(path = "/consum/item/save", method = RequestMethod.POST) 
    	public  String saveConsumItem(ModelDesignCost dCost,ModelDesign design,ModelItem item,ModelUOM modelUOM,ModelCurrency modelCurrency,@RequestParam("designId") Long designId,@RequestParam("uOMId") Long uOMId,@RequestParam("itemId") Long itemId,@RequestParam("currency") Long currency,@RequestParam("qty") double qty,@RequestParam("unitPrice") double unitPrice,@RequestParam("totalCost") double totalCost) {
    	   	 
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
        	
        	System.out.println("Design Id : "+ designId);
        	System.out.println("uOMId : "+ uOMId);
        	
        	design.setDesignId(designId);
        	modelUOM.setUomId(uOMId); 
        	item.setItemId(itemId);
        	modelCurrency.setCurrencyId(currency);
        	
        	dCost.setModelDesign(design);
        	dCost.setModelUOM(modelUOM);
        	dCost.setModelItem(item);
        	dCost.setModelCurrency(modelCurrency);
        	dCost.setQty(qty);
        	dCost.setUnitPrice(unitPrice);
        	dCost.setTotalCost(totalCost);
        	dCost.setActiveStatus(1);
        	dCost.setEntryTimestamp(entryTime);
        	dCost.setEnteredBy(1);
        	
        	daoDesignCostImp.saveDesignCost(dCost);
    	
    	 return "redirect:/cost/init";
    	
    }
	    
	    /*
	    @ResponseBody
	    @RequestMapping(value = "/currency/data/list", method=RequestMethod.GET)
	    public List<ModelCurrency> getCurrencyDataListForCostHead() {
		   
	    	List<ModelCurrency> currencyDataList=new ArrayList<ModelCurrency>(); 
			currencyDataList=currencyRepository.findCurrency();
			
			System.out.println("size :" + currencyDataList.size());
		   
	        return currencyDataList;
		}
	    
	    */
	    
	    @RequestMapping(path = "/other/item/save", method = RequestMethod.POST) 
    	public  String saveOhterItem(ModelDesignCost dCost,ModelDesign design,ModelUOM modelUOM,ModelCostHead costHead,@RequestParam("designId") Long designId,@RequestParam("qty") double qty,@RequestParam("uOMId") Long uOMId
    			
    			,@RequestParam("unitPrice") double unitPrice,@RequestParam("totalCost") double totalCost,@RequestParam("headId") Long headId,ModelCurrency modelCurrency,@RequestParam("currencyId") Long currencyId) {
    	   	 
        	java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
        	
        	System.out.println("Design Id : "+ designId);
        	System.out.println("uOMId : "+ uOMId);
        	
        	design.setDesignId(designId);
        	modelUOM.setUomId(uOMId);
        	costHead.setCostHeadId(headId);
        	modelCurrency.setCurrencyId(currencyId);
        	
        	
        	
        	dCost.setModelDesign(design);
        	dCost.setModelUOM(modelUOM);
        	dCost.setCostHeadId(costHead);
        	dCost.setQty(qty);
        	dCost.setUnitPrice(unitPrice);
        	dCost.setTotalCost(totalCost);
        	dCost.setActiveStatus(1);
        	dCost.setEntryTimestamp(entryTime);
        	dCost.setEnteredBy(1);
        	dCost.setModelCurrency(modelCurrency);
        	
        	
        	daoDesignCostImp.saveDesignCost(dCost);
    	
    	 return "redirect:/cost/init";
    	
    }
	    
	    
	    @ResponseBody
	    @RequestMapping(value = "/consum/calculate/search", method=RequestMethod.GET)
	    public List<ModelWOInquiryData> getConsumCalculateData(@RequestParam("id") Long id) {
		   
     	System.out.println("Design Id :" + id);
     	
     	List<ModelWOInquiryData> designList= new ArrayList<ModelWOInquiryData>(); 
     	
     	List<ModelWOInquiryData> resultList=daoCostCalculation.getConsumCalculateDataByDesignId(id);
     	  	
     	List<ModelUOMCustom> uOMList= new ArrayList<ModelUOMCustom>(); 
     	uOMList=daoUOMImp.getUOMListCustom();
     	
     	List<ModelCurrency> currencyList=new ArrayList<ModelCurrency>(); 
		currencyList=currencyRepository.findCurrency();
     	
     	
     	for(ModelWOInquiryData m:resultList ) {
    		m.setModelUOMCustomList(uOMList);
    		m.setModelCurrencyList(currencyList);
    		designList.add(m);
    		
        	}
     	
		   
	        return designList;
		}
	    
	    
}
