package com.biziitech.mlfm.bg.daoimp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.bg.dao.DaoPhone;
import com.biziitech.mlfm.bg.model.ModelCountry;
import com.biziitech.mlfm.bg.model.ModelPhone;
import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.repository.PhoneRepository;
@Service
public class DaoPhoneImp implements DaoPhone {
	
@Autowired
private DataSource dataSource;

@Autowired
private JdbcTemplate jdbcTemplate;

@Autowired
private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
@Autowired
 private PhoneRepository phoneRepository;
	@Override
	public long getPhoneId() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ModelPhone> getUserPhoneData(Long userId) {
		 List<ModelPhone> numberList= phoneRepository.findPhones(userId);
		 List<ModelPhone> phoneList = new ArrayList<>();
		 for(ModelPhone phone:numberList) {
			 if(phone.getActiveStatus()==1)
			 { phone.setsActive("Yes");
			  phone.setActive(true);
			 }
			 
			 else
			 {	 phone.setsActive("NO");
			     phone.setActive(false);
			 }
			 
			 if(phone.getEmergencyStatus()==1)
				 phone.setsEmergency("Yes");
			 else
				 phone.setsEmergency("No");
			 if(phone.getTypeId()==1)
				 phone.setPhoneType("Cell");
			 else if(phone.getTypeId()==2)
					 phone.setPhoneType("Lan");
			 phoneList.add(phone);
		 }
		return phoneList;
	}

	@Override
	public void savePhone(ModelPhone phone) {
		
		 if(phone.isActive())
			 phone.setActiveStatus(1);
		 else
			 phone.setActiveStatus(0);
		 
		 if(phone.isEmergency())
			 phone.setEmergencyStatus(1);
		 else
			 phone.setEmergencyStatus(0);
		 
		 //System.out.println(phone.getCountryName());
		 ModelCountry country= new ModelCountry();
		 country.setCountryId(phone.getModelCountry().getCountryId());
		 phone.setModelCountry(country);//setCountryId(Long.valueOf(phone.getCountryName()));
		
		phoneRepository.save(phone);
		
	}

	@Override
	public void updatePhone(ModelPhone phone) {
		phoneRepository.save(phone);
		
	}

	@Override
	public long getPhoneId(Long userId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<ModelPhone> getPhoneById(Long phoneId) {
		// TODO Auto-generated method stub
		Optional<ModelPhone> phone= phoneRepository.findById(phoneId);
		 if(phone.get().getActiveStatus()==1)
		 {
			 phone.get().setActive(true);
		  
		 }
		 
		 else
		 {	 
			 phone.get().setActive(false);
		 }
		 
		 if(phone.get().getEmergencyStatus()==1)
			 phone.get().setEmergency(true);
		 else
			 phone.get().setEmergency(false);
		return phone;
	}

	@Override
	public List<ModelPhone> getUserPhoneDataByUserId(Long userId) {
		// TODO Auto-generated method stub
		 List<ModelPhone> numberList= phoneRepository.findPhonesByUserId(userId);
		 List<ModelPhone> phoneList = new ArrayList<>();
		 for(ModelPhone phone:numberList) {
			 if(phone.getActiveStatus()==1)
			 { phone.setsActive("Yes");
			  phone.setActive(true);
			 }
			 
			 else
			 {	 phone.setsActive("NO");
			     phone.setActive(false);
			 }
			 
			 if(phone.getEmergencyStatus()==1) {
				 phone.setsEmergency("Yes");
			 	 phone.setEmergency(true);
			 }
			 else {
				 phone.setsEmergency("No");
				 phone.setEmergency(false);
			 }
			 if(phone.getTypeId()==1)
				 phone.setPhoneType("Cell");
			 else if(phone.getTypeId()==2)
					 phone.setPhoneType("Lan");
			 phoneList.add(phone);
		 }
		return phoneList;
	}
	
         //created by sohel rana on 07-04-2019
	
	  //get contact person name by orderownerid
	  
	@Override
	public List<ModelPhone> getContactPersonName(Long orderOwnerId) {

		String qry="SELECT a.CONTACT_PERSON_NAME FROM bg_phone a INNER JOIN mlfm_order_owner b on a.order_owner_id=b.order_owner_id WHERE a.active_status=1 AND b.order_owner_id=coalesce(:orderOwnerId,b.order_owner_id)";
				
	
		RowMapper<ModelPhone> serviceMapper=new RowMapper<ModelPhone>() {
			public ModelPhone mapRow(ResultSet rs, int rownum) throws SQLException{
				
				
				
				ModelPhone bn=new ModelPhone();
						
		             bn.setContactPersonName(rs.getString("contact_person_name"));        
		        return bn;
			}
		};
		
		  MapSqlParameterSource parameters = new MapSqlParameterSource();
		  
		  parameters.addValue("orderOwnerId", orderOwnerId);
	     
	       
	      return namedParameterJdbcTemplate.query(qry,parameters,serviceMapper);
	      
	}
	
	
}
