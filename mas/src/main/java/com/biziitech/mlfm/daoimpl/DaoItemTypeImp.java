package com.biziitech.mlfm.daoimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.dao.DaoItemType;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.repository.ItemTypeRepository;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class DaoItemTypeImp implements DaoItemType {
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	private ItemTypeRepository itemRepository;
	

	@Override
	public List<ModelItemType> findItems() {
		
		List<ModelItemType> resultList = itemRepository.findAll();
		List<ModelItemType> itemList= new ArrayList<>();
		for(ModelItemType type: resultList) {
			if(type.getActiveStatus()==1)
				{
				type.setActive(true);
				type.setsActive("Yes");
				}
			else
			{
				type.setActive(true);
				type.setsActive("No");
			}
			itemList.add(type);
		}
		
		
		
		return itemList;
	}

	@Override
	public void saveItemType(ModelItemType item) {
		if(item.isActive())
			item.setActiveStatus(1);
		else
			item.setActiveStatus(0);
		
		itemRepository.save(item);
		
	}

	@Override
	public Optional<ModelItemType> findItemTypeById(Long id) {
		Optional<ModelItemType> model= itemRepository.findById(id);
		if(model.isPresent())
		{
			if(model.get().getActiveStatus()==1)
				model.get().setActive(true);
			else
				model.get().setActive(false);
		}
		return model;
	}

	@Override
	public List<ModelItemType> searchItem(Integer level, String name, String remarks, int active) {
		List<ModelItemType> resultList = itemRepository.findItemDetails();
		List<ModelItemType> itemList= new ArrayList<>();
		for(ModelItemType type: resultList) {
			if(type.getActiveStatus()==1)
				{
				type.setActive(true);
				type.setsActive("Yes");
				}
			else
			{
				type.setActive(true);
				type.setsActive("No");
			}
			itemList.add(type);
		}
		
		
		
		return itemList;
	}
	
	
	@Override
	public List<ModelItemType> findItemCategory() {
		List<ModelItemType> resultList=itemRepository.findItemCategory();
		List<ModelItemType> categoryList=new ArrayList<>();
		
		for (ModelItemType mitype : resultList) {
			
		      
			categoryList.add(mitype);
			
		}
		return categoryList;
		
	}
	
	@Override
	public List<ModelItemType> findItemSubcategory(Long categoryId) {
		
		List<ModelItemType> resultList=itemRepository.findItemSubcategory(categoryId);
		
		List<ModelItemType> subcategoryList=new ArrayList<>();
		
		for (ModelItemType mitype : resultList) {
			
			subcategoryList.add(mitype);
		}
		
		return subcategoryList;
	}
	
	
	public List<ModelItemType> findItemSubsubcategory(Long subcategoryId) {
		
		List<ModelItemType> resultList=itemRepository.findItemSubsubcategory(subcategoryId);
		List<ModelItemType> subsubcategoryList=new ArrayList<>();
		
		for (ModelItemType mitype: resultList) {
			
			
			subsubcategoryList.add(mitype);
		}
		
		return subsubcategoryList;
	}
	
	
	
	   public void generatePdfReport(String contextPath) throws SQLException {
	        
	        Connection conn= dataSource.getConnection();
	        

	 try {
	            
	            
	             InputStream input = new FileInputStream(new File(contextPath));
	             
	             System.out.println("Context Path is  : ="+contextPath);
	              JasperDesign jasperDesign = JRXmlLoader.load(input);

	            System.out.println("Compiling Report Designs");
	            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

	            System.out.println("Creating JasperPrint Object");
	          /*
	            Map<String, String> parameters = new HashMap<String, String>();
	            parameters.put("ReportTitle", "PDF JasperReport");
	            
	            */

	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,conn);

	           // File f = new File("report/StudentReport.pdf");
	          
	            File f = new File("d:\\clientList.pdf");
	             f.createNewFile();
	          
	            //Exporting the report
	            OutputStream output = new FileOutputStream(f);

	            JasperExportManager.exportReportToPdfStream(jasperPrint, output);

	            System.out.println("Report Generation Complete");
	            conn.close();
	            }
	             catch (Exception e) {
	            e.printStackTrace();
	            }
	    }

}
