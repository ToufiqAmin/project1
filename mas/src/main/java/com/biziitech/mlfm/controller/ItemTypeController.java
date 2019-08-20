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

import com.biziitech.mlfm.bg.model.ModelUser;
import com.biziitech.mlfm.dao.DaoUserObject;
import com.biziitech.mlfm.daoimpl.DaoItemTypeImp;
import com.biziitech.mlfm.daoimpl.DaoLogonImp;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.model.ModelUserObject;
import com.biziitech.mlfm.repository.ItemTypeRepository;

@Controller
public class ItemTypeController {
	
	@Autowired
	private DaoItemTypeImp item;
	
	@Autowired
	private ItemTypeRepository itemRepository;
	
	@Autowired
	private DaoUserObject daoUserObject;
	
	@Autowired
	private DaoLogonImp daoLogonImp;
			
	private Long systemUserId;

	@RequestMapping(path="/item/type/{userId}")
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
		
		
		
		
		 ModelItemType newItem = new  ModelItemType();
		newItem.setActive(true);
		
		model.addAttribute("item",newItem);
		model.addAttribute("parentList",item.findItems());
		return "item_type";
	}
	
	
	
	@RequestMapping(path="/item/save", method =RequestMethod.POST)
	
	public String saveItemType(ModelItemType item) {
		
		
		 Long userId=systemUserId;
		
		if(item.getItemTypeId()== null) {
			java.util.Date date = new java.util.Date();
			java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
			
			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
			 
			item.setEnteredBy(user);//later from System
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
			java.sql.Timestamp updateTime = new java.sql.Timestamp(date.getTime());
			
			 ModelUser user=new ModelUser();
			 user.setUserId(userId);
			 
			item.setUpdatedBy(user);
			item.setUpdateTimestap(updateTime);
			
			
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
		return "redirect:/itemcontroller/itemEdit/"+item.getItemTypeId();
	}
	
	
	@RequestMapping(path="/itemcontroller/itemEdit/{id}")
	//public String itemEntry(Model model,@PathVariable("id")Long id) {
		public String itemEdit(Model model,@PathVariable("id")Long id) {
		
		
		
		
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
		
		
		
		
		
		model.addAttribute("item",item.findItemTypeById(id));
		model.addAttribute("parentList",item.findItems());
		  if(id!=0) {
	        	String msg="Successfully Saved";
	        	 model.addAttribute("message",msg );
	        }
	        else
	        {
	        	String msg="Some Error Occured";
	        	 model.addAttribute("message",msg);
	        }
		return "item_type";
	}
	
	
	
	
	
}
