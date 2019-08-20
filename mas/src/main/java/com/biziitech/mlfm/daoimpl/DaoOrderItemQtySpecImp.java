package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.biziitech.mlfm.custom.model.ModelInquiryList;
import com.biziitech.mlfm.custom.model.ModelWOChdListCustom;
import com.biziitech.mlfm.dao.DaoOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.model.ModelOrderItemQtySpec;
import com.biziitech.mlfm.model.ModelSpec;
import com.biziitech.mlfm.repository.OrderItemQtyRepository;
import com.biziitech.mlfm.repository.OrderItemQtySpecRepository;
@Service
public class DaoOrderItemQtySpecImp implements DaoOrderItemQtySpec{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	

	@Autowired
    private DataSource dataSource;
	
	
	private List<ModelOrderItemQtySpec> orderItemQtySpec;
	
	private Long itemQtyId;
	public Long getItemQtyId() {
		return itemQtyId;
	}
	
	public List<ModelOrderItemQtySpec> getOrderItemQtySpec() {
		return orderItemQtySpec;
	}

	public void setOrderItemQtySpec(List<ModelOrderItemQtySpec> orderItemQtySpec) {
		this.orderItemQtySpec = orderItemQtySpec;
	}

	public void setItemQtyId(Long itemQtyId) {
		this.itemQtyId = itemQtyId;
	}
	@Autowired
	OrderItemQtySpecRepository orderItemQtySpecRepository;
	@Override
	public void saveSpecification(String item,String specValue,String remarks) {
		Long specId=Long.parseLong(item);
		 ModelOrderItemQtySpec spec=new ModelOrderItemQtySpec();
		 ModelSpec specification=new ModelSpec();
		 ModelOrderItemQty modelOrderItemQty=new ModelOrderItemQty();
		 modelOrderItemQty.setOrderItemQtyId(getItemQtyId());
		 specification.setSpecId(specId);
		 java.util.Date date = new java.util.Date();
		 java.sql.Timestamp entryTime = new java.sql.Timestamp(date.getTime());
		 spec.setModelSpec(specification);
		 spec.setActive(true);
		 spec.setActiveStatus(1);
		 spec.setEntryTimestamp(entryTime);
		 spec.setModelOrderItemQty(modelOrderItemQty);
		 spec.setRemarks(remarks);
		 spec.setSpecValue(specValue);
		 orderItemQtySpecRepository.save(spec);
	}
	@Override
	public List<ModelOrderItemQtySpec>findAll(){
		List<ModelOrderItemQtySpec> itemSpecList=orderItemQtySpecRepository.findAll();
		return itemSpecList;
	}
	
	public List<ModelOrderItemQtySpec>findByQtyId(Long id){
		List<ModelOrderItemQtySpec> itemSpecListById=orderItemQtySpecRepository.findItemSpecDetails(id);
		setOrderItemQtySpec(itemSpecListById);
		return itemSpecListById;
	}
@Override
	public Optional<ModelOrderItemQtySpec> getItemQtySpecById(Long spec_Id) {
		
		Optional<ModelOrderItemQtySpec> itemSpecList=orderItemQtySpecRepository.findById(spec_Id);
		setItemQtyId(itemSpecList.get().getModelOrderItemQty().getOrderItemQtyId());
		return itemSpecList;
	}

public void editSpec(ModelOrderItemQtySpec itemQtySpec) {	
	orderItemQtySpecRepository.save(itemQtySpec);
}

@Override
public List<ModelOrderItemQtySpec> getOrderItemQtySpecList(Long inquiryItemQtyId) {
	
	List<ModelOrderItemQtySpec> modelOrderItemQtySpecList = orderItemQtySpecRepository.findItemSpecDetails(inquiryItemQtyId);
	
	
      return	modelOrderItemQtySpecList;
	// TODO Auto-generated method stub
	
}



@Override
public List<ModelOrderItemQtySpec> getOrderItemQtySpecActiveList(Long inquiryItemQtyId) {
	
	List<ModelOrderItemQtySpec> modelOrderItemQtySpecList = orderItemQtySpecRepository.findItemSpecDetailsActive(inquiryItemQtyId);
	
	
      return	modelOrderItemQtySpecList;
	// TODO Auto-generated method stub
	
}

@Override
public void saveNewSpecification(ModelOrderItemQtySpec modelOrderItemQtySpec) {
	
	orderItemQtySpecRepository.save(modelOrderItemQtySpec);
	
}

@Override
public List<ModelOrderItemQtySpec> findAllSpecByOrderItemQtyId(Long id) {
	// TODO Auto-generated method stub
	return orderItemQtySpecRepository.findItemSpecDetails(id);
}


/*created by sohel rana on 31/03/2019
 */

@Override
public List<ModelOrderItemQtySpec> findAllSpecification(Long specificationId) {
	// TODO Auto-generated method stub
	return orderItemQtySpecRepository.findSpecification(specificationId);
}


public void updateOrderSpecificationData(ModelOrderItemQtySpec specification){
    try {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update("update mlfm_order_item_qty_spec set spec_value=?,active_status=?,remarks=?, updated_by=?, update_timestamp=? where order_item_qty_spec_id=?",specification.getSpecValue(),specification.getActiveStatus(),specification.getRemarks(),specification.getModelUpdatedBy().getUserId(),specification.getUpdateTimestap(),specification.getOrderItemQtySpecId());
    	
    
    System.out.println("update success");
   }
    
    
    catch(Exception e) {
    	e.printStackTrace();
}

}


//created by sohel rana on 23/4/2019


@Override
public List<ModelWOChdListCustom> findSpecificationById(Long specificationId) {
	
	 String qry="SELECT a.order_item_qty_spec_id,b.spec_name,a.spec_value,a.remarks,a.active_status FROM mlfm_order_item_qty_spec a INNER JOIN mlfm_spec b ON a.spec_id=b.spec_id WHERE a.order_item_qty_spec_id=coalesce(:id,a.order_item_qty_spec_id)";
		
		
		RowMapper<ModelWOChdListCustom> serviceMapper=new RowMapper<ModelWOChdListCustom>() {
			@Override
			public ModelWOChdListCustom mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelWOChdListCustom bn=new ModelWOChdListCustom();
		        
				bn.setOrderSpecId(rs.getLong("order_item_qty_spec_id"));
				bn.setOrderSpecName(rs.getString("spec_name"));
				bn.setOrderSpecValue(rs.getString("spec_value"));
				bn.setRemarks(rs.getString("remarks"));
				bn.setActiveStatus(rs.getInt("active_status"));
				
				
			
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", specificationId);
	     
	      


		return  jdbcTemplate.query(qry,parameters,serviceMapper);
}

}
