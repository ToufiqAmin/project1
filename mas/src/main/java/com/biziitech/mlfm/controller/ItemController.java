package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoItemTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.daoimpl.DaoUOMImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.model.ModelUOM;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	private DaoItemImp daoItem;
	
	@Autowired
	private DaoItemTypeImp daoItemType;
	
	@Autowired
	private DaoUOMImp daoUOM;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private DaoUOMImp daoUOMImp;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;
			
	private Long systemUserId;

	@RequestMapping(path="/item/add/{userId}", method = RequestMethod.GET)
	public String itemEntry(@PathVariable Long userId,Model model) {
		
		
		
		
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
		
		
		
		
		
		ModelItem newItem = new  ModelItem();
		newItem.setActive(true);
		
		model.addAttribute("item",newItem);
		
		//this List add sas
		List<ModelUOM> uomName=daoUOMImp.getUomName();
		model.addAttribute("uomList", uomName);
		
		
		//model.addAttribute("parentList",item.findItems());
		
		// category data populate
		
				//List<ModelItemType> itemCategoryList=itemType.findItemCategory();
				model.addAttribute("categoryList",daoItemType.findItemCategory());
				
			//	Map<String,Long> m= new HashMap<String, Long>();
			//	m.put( "PCS",Long.valueOf(1));
			//	m.put( "Box",Long.valueOf(2));
				
				//Map<Long,Long> m= new HashMap<Long, Long>();
				//m.put( Long.valueOf(1),Long.valueOf(1));
				//m.put(Long.valueOf(1),Long.valueOf(2));
				
				model.addAttribute("uomList",daoUOM.getUOMList());
		
				return "item";
	}
	
	/*
	 @RequestMapping(path = "/owners/add", method = RequestMethod.GET)
	    public String createBuyer(Model model) {

		 ModelOrderOwner newOwner= new ModelOrderOwner();
		 ModelCountry defaultCountry= new ModelCountry();
		 defaultCountry.setCountryId(1l);
		 
		 ModelBusinessType defaultType = new ModelBusinessType();
		 defaultType.setTypeId(1l);
		 
		 ModelOrderOwnerType defaultOwnerType= new ModelOrderOwnerType();
		 defaultOwnerType.setOrderOwnerTypeId(1l);
		 
		 newOwner.setOwnerCountry(defaultCountry);
		 newOwner.setModelBusinessType(defaultType);
		 newOwner.setOrderOwnerType(defaultOwnerType);
	        model.addAttribute("owner", newOwner);
	        String msg=" ";
	        model.addAttribute("message", " ");
	        List <ModelCountry> countryList= country.getCountryName();
	        
	        model.addAttribute("countryList",countryList);

	        List <ModelBusinessType> typeList=  businessType.getTypeName();
	        model.addAttribute("typeList",typeList);
	        
	        List<ModelOrderOwnerType> ownerTypeList= ownerType.getTypeName();
	        model.addAttribute("ownerTypeList",ownerTypeList);
	        
	        
	        ModelPhone phone= new ModelPhone();
	        
	        
	        
	        ModelCountry defaultC= new ModelCountry();
			defaultC.setCountryId(1l);
			phone.setModelCountry(defaultC);
			
	        model.addAttribute("phone",phone);
	        
	        List<ModelPhone> numberList= new ArrayList<>();
	        
	        model.addAttribute("phoneList",numberList);
	        
	        return "buyer_data_upload";
	}
	
	*/
	
	/*
	@RequestMapping(path="/item/add/{id}")
	public String itemEntry(Model model,@PathVariable("id")Long id) {
		//model.addAttribute("item",item.findItemTypeById(id));
		//model.addAttribute("parentList",item.findItems());
		
		
		
		  if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
		return "item";
	}
	*/
	
	@RequestMapping(path="/item/subcategory")
	@ResponseBody
	public List<ModelItemType> findSubcategory(@RequestParam("category") Long categoryId){
		
		return daoItemType.findItemSubcategory(categoryId);
		
	}
	
	
	
	
	@RequestMapping(path="/item/subsubcategory")
	@ResponseBody
	public List<ModelItemType> findSububcategory(@RequestParam("subcategory") Long subcategoryId){
		
		return daoItemType.findItemSubsubcategory(subcategoryId);
		
	}
	
	

	
	 @RequestMapping(path = "/item/datasave", method = RequestMethod.POST)
	public String saveItem(ModelItem modelItem) {
		 
		 
		    Long userId=systemUserId;
		 
	System.out.println("category id " + modelItem.getCategoryId()
	+ " subcategory id "  + modelItem.getSubcategoryId() 
	+ " subsubcategory id " + modelItem.getSubsubcategoryId() );
	
		 
	if(modelItem.getItemId()==null )
		{
		// category id is not blank but subcategory and subsubcategory are blank	
		if (modelItem.getCategoryId()>0 && modelItem.getSubcategoryId()==0 && modelItem.getSubsubcategoryId()==0 ) {
			//modelItem.getItemTypeId().setItemTypeId(modelItem.getCategoryId());
			ModelItemType modelItemType= new ModelItemType();
			modelItemType.setItemTypeId(modelItem.getCategoryId());
			modelItem.setItemTypeId(modelItemType);
		}
		// Category and Subcategory Id are not blank but Subsubcategory is blank
		else if (modelItem.getCategoryId()>0 && modelItem.getSubcategoryId()>0 && modelItem.getSubsubcategoryId()==0) {
			//modelItem.getItemTypeId().setItemTypeId(modelItem.getSubcategoryId());
			ModelItemType modelItemType= new ModelItemType();
			modelItemType.setItemTypeId(modelItem.getSubcategoryId());
			modelItem.setItemTypeId(modelItemType);
		
		}
		// Category, Subcategory and Subsubcategory are not blank.
		else if (modelItem.getCategoryId()>0 && modelItem.getSubcategoryId()>0 && modelItem.getSubsubcategoryId()>0) {
			ModelItemType modelItemType= new ModelItemType();
			modelItemType.setItemTypeId(modelItem.getSubsubcategoryId());
			modelItem.setItemTypeId(modelItemType);
		
		}

		//System.out.println("uom id " + modelItem.getModelUOM().getUomId());
		
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		modelItem.setEntryTimestamp(entryTime);
		
		 ModelUser user=new ModelUser();
		 user.setUserId(userId);
		
		
		modelItem.setEnteredBy(user);
		
		daoItem.saveItem(modelItem);
			
		}
		else
		{
			Optional<ModelItem> existItem= itemRepository.findById(modelItem.getItemId());
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
			
			modelItem.setEnteredBy(existItem.get().getEnteredBy());
			modelItem.setEntryTimestamp(existItem.get().getEntryTimestamp());
			
			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
			
			modelItem.setUpdatedBy(user);
			modelItem.setUpdateTimestap(updateTime);
			daoItem.saveItem(modelItem);
		}
	    

	        return "redirect:/itemcontroller/editItem/"+modelItem.getItemId();
	}
	 
	 
		
	 @RequestMapping(path = "/itemcontroller/editItem/{id}", method = RequestMethod.GET)
	    public String editItem(@PathVariable Long id, Model model) {
		 
		 
		 
		 
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
		 
		 
		 
		 
		 
		 model.addAttribute("categoryList",daoItemType.findItemCategory());
		 
		 Optional<ModelItem> modelItem;
		 modelItem= itemRepository.findById(id);
		
		 if( modelItem.get().getActiveStatus()==1) 
			modelItem.get().setActive(true);
			else
				modelItem.get().setActive(false);
	        model.addAttribute("item", modelItem);
	        
	        if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Errors Occured";
	        	 model.addAttribute("message",msg);
	        }
	        
	        
	        
	        
	        //List <ModelItemType> categoryList=itemType.findItemCategory();
	        
	        
	        model.addAttribute("categoryList",daoItemType.findItemCategory());
	        
	        
            //List <ModelItemType> subcategoryList= itemType.findItemSubcategory(modelItem.get().getCategoryId());
	        
	        //model.addAttribute("subcategoryList",categoryList);
	        
            //List <ModelItemType> subsubcategoryList= itemType.findItemSubsubcategory(modelItem.get().getSubcategoryId());
	        
	        //model.addAttribute("subsubcategoryList",subsubcategoryList);
	        
	        // List<ModelUOM> uomList= daoUOM.getUOMList();
	        // 
	        // model.addAttribute("uomList",uomList);
	        
	        model.addAttribute("uomList",daoUOM.getUOMList());
	        
	        
	        return "item";
	}
	 
	/* 
	 @RequestMapping(path="/item/viewform", method=RequestMethod.POST)
	 
	 public String searchItem (ModelItem modelItem) {
		 
		 List<ModelItem> itemList=daoItem.findItems();
		 
		 
		 
	 }
	 
	 */
	
	
	/*
	@RequestMapping(path="/item/save", method =RequestMethod.POST)
	
	public String saveItemType(ModelItemType item) {
		if(item.getItemTypeId()== null) {
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			item.setEnteredBy(1);//later from System
			item.setEntryTimestamp(entryTime);
			if(item.getParentItemTypeId()==0||item.getParentItemTypeId()==item.getItemTypeId())
				item.setLevelNo(0);
			else
			{
				Optional<ModelItemType> parent=this.item.findItemTypeById(item.getParentItemTypeId());
				if(parent.get().getParentItemTypeId()==0)
					item.setLevelNo(1);
				else
					item.setLevelNo(2);
			}
				
			this.item.saveItemType(item);
		}
		else
		{
			Optional<ModelItemType> model=this.item.findItemTypeById(item.getItemTypeId());
			item.setEnteredBy(model.get().getEnteredBy());
			item.setEntryTimestamp(model.get().getEntryTimestamp());
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			item.setUpdatedBy(2);//latter from system
			item.setUpdateTimestap(date);
			if(item.getParentItemTypeId()==0 ||item.getParentItemTypeId()==item.getItemTypeId() )
				item.setLevelNo(0);
			else
			{
				Optional<ModelItemType> parent=this.item.findItemTypeById(item.getParentItemTypeId());
				if(parent.get().getParentItemTypeId()==0)
					item.setLevelNo(1);
				else
					item.setLevelNo(2);
			}
			
			
			this.item.saveItemType(item);
		}
		return "redirect:/item/type/"+item.getItemTypeId();
	}
	*/
}