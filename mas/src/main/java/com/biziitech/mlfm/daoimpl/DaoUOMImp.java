package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelPIInquiryList;
import com.biziitech.mlfm.custom.model.ModelUOMCustom;
import com.biziitech.mlfm.dao.DaoUOM;
import com.biziitech.mlfm.model.ModelItem;
import com.biziitech.mlfm.model.ModelMachineType;
import com.biziitech.mlfm.model.ModelUOM;

import com.biziitech.mlfm.repository.UOMRepository;




@Service
public class DaoUOMImp implements DaoUOM {
	
	
	
	
	@Autowired
	private UOMRepository uomRepository;
	
	@Autowired
    private DataSource dataSource;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        //this.jdbcTemplate = new JdbcTemplate(dataSource);
        //this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate=jdbcTemplate;
          }
	
	
	
	public void saveUOM(ModelUOM modelUOM) {
		if (modelUOM.isActive()) {
			modelUOM.setActiveStatus(1);
		}
		
		else
		{
			modelUOM.setActiveStatus(0);
		}
		
		
		uomRepository.save(modelUOM);
		
	}
	
	@Override
	public List<ModelUOM> getUOMList() {
		
		List<ModelUOM> resultList = uomRepository.findUOM();
		List<ModelUOM> uomList= new ArrayList<>();
		
		for(ModelUOM uom: resultList) {
			if(uom.getActiveStatus()==1)
				if(uom.getActiveStatus()==1)
				 { uom.setsActive("Yes");
				  uom.setActive(true);
				 }
				 
				 else
				 {	 uom.setsActive("NO");
				     uom.setActive(false);
				     
				 }
				 
			uomList.add(uom);
		}
		
		
		
		return uomList;
	}
	
	
	@Override
	public List<ModelUOM> getUOMListByCraiteria(String uomName, String shortCode, String remarks, int  status) {
		
		List<ModelUOM> resultList = uomRepository.findUOMDetails(uomName, shortCode, remarks, status);
		List<ModelUOM> uomList= new ArrayList<>();
		
		for(ModelUOM uom: resultList) {
			if(uom.getActiveStatus()==1)
				if(uom.getActiveStatus()==1)
				 { uom.setsActive("Yes");
				  uom.setActive(true);
				 }
				 
				 else
				 {	 uom.setsActive("NO");
				     uom.setActive(false);
				     
				 }
				 
			uomList.add(uom);
		}
		
		
		
		return uomList;
	}
	
	@Override
	public List<ModelUOM> getUomName() {
		
		//return uomRepository.findAll();
		return uomRepository.getUomName();
	}

	@Override
	public List<ModelUOMCustom> getUOMListCustom() {
		
		String qry="select uom_id, uom_name from mlfm_uom where active_status=1 order by uom_name";
		RowMapper<ModelUOMCustom> serviceMapper=new RowMapper<ModelUOMCustom>() {
			public ModelUOMCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				
				ModelUOMCustom bn=new ModelUOMCustom();
		
			    bn.setUomId(rs.getLong("uom_id"));
			    bn.setUomName(rs.getString("uom_name"));
		 
			    return bn;
			 }
			
		};
		
	
	      
	      return jdbcTemplate.query(qry, serviceMapper);
	}
	




}
