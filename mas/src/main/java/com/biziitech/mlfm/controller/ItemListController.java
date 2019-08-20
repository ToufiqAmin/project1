package com.biziitech.mlfm.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemImp;
import com.biziitech.mlfm.daoimpl.DaoItemTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.ItemRepository;

@Controller
public class ItemListController {
	
	@Autowired
	private DaoItemImp daoItem;
	
	@Autowired
	private DaoItemTypeImp itemType;
	
	@Autowired
	private ItemRepository itemRepository;
			
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;


private Long systemUserId;

	@RequestMapping(path="/item/list/{userId}", method = RequestMethod.GET)
	//@RequestMapping(path="/item/list")
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
		
		
		
		//ModelItem newItem = new  ModelItem();
		//newItem.setActive(true);
		
		model.addAttribute("itemList", new ArrayList<ModelItem>());
	
		return "item_list";
	}
	
	@RequestMapping(path="/item/search")
	public String itemSearch(Model model, @RequestParam("itemName") String itemName, @RequestParam("remarks") String remarks, @RequestParam(value="active", required=false)boolean active ) {
	
		
		
		
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
		
		
		
		
		/*
		int status=0;
		
		if (!activeStatus.isEmpty() && activeStatus!=null) {
			
			status=Integer.parseInt(activeStatus);
		}
		
		else {
			
			status=0;
		}
		
		*/
		
		System.out.println("item Name : " + itemName);
		
		// search item selects items based on item name, remarks and active status.
		List<ModelItem> itemList=daoItem.searchItem(itemName,remarks,active);
		
		model.addAttribute("itemList", itemList);
		return "item_list";
		
		
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
	@RequestMapping(path="item/edit")
	public String itemEdit(Model model, @PathVariable("bd")Long id) {
		
		
		return "redirect:/item/add/"+id;
	
	    
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