package com.biziitech.mlfm.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.biziitech.mlfm.dao.DaoOrderItemQty;
import com.biziitech.mlfm.model.ModelDeliveryChallan;
import com.biziitech.mlfm.model.ModelItemType;
import com.biziitech.mlfm.model.ModelOrderItem;
import com.biziitech.mlfm.model.ModelOrderItemQty;
import com.biziitech.mlfm.repository.OrderItemQtyRepository;
@Service
public class DaoOrderItemQtyImp implements DaoOrderItemQty {
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbc;
	

	@Autowired
    private DataSource dataSource;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private List<ModelOrderItemQty> itemQtyList;
	
	private Long itemQtyId;
	public Long getItemQtyId() {
		return itemQtyId;
	}

	public void setItemQtyId(Long itemQtyId) {
		this.itemQtyId = itemQtyId;
	}

	public List<ModelOrderItemQty> getItemQtyList() {
		return itemQtyList;
	}

	public void setItemQtyList(List<ModelOrderItemQty> itemQtyList) {
		this.itemQtyList = itemQtyList;
	}

	private Long orderItemId;
	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Autowired
	private OrderItemQtyRepository orderItemQty;

	@Override
	public void saveOrderItemQty(ModelOrderItemQty itemQty) {
		/*
		itemQty.setActiveStatus(1);
		ModelOrderItem orderItem=new ModelOrderItem();
		orderItem.setItemOrderId(getOrderItemId());
		itemQty.setModelOrderItem(orderItem);
		
		*/
		orderItemQty.save(itemQty);
	}
	
	@Override
	public List<ModelOrderItemQty> findItemsQty() {
		
		List<ModelOrderItemQty> resultList = orderItemQty.findAll();
		List<ModelOrderItemQty> itemList= new ArrayList<>();
		for(ModelOrderItemQty type: resultList) {
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
	public List<ModelOrderItemQty> getOrderItemQtyById(Long id) {
		List<ModelOrderItemQty>  itemQtyList= new ArrayList<>();
		List<ModelOrderItemQty> resultList= orderItemQty.findItemQtyDetails(id);
		for(ModelOrderItemQty itemQty:resultList) {
			if(itemQty.getActiveStatus()==1)
			 { itemQty.setsActive("Yes");
			  itemQty.setActive(true);
			 }
			 
			 else
			 {	 itemQty.setsActive("NO");
			     itemQty.setActive(false);
			 }
			 
			
			 itemQtyList.add(itemQty);
			 setItemQtyList(itemQtyList);
		 }
		return itemQtyList;
	}
	
	@Override
	public Optional<ModelOrderItemQty> getOrderItemQty(Long itemQtyId) {
		// TODO Auto-generated method stub
		Optional<ModelOrderItemQty> itemQty=orderItemQty.findById(itemQtyId);
		setItemQtyId(itemQty.get().getOrderItemQtyId());
		setOrderItemId(itemQty.get().getModelOrderItem().getItemOrderId());
		itemQty.get().setOrderDateTaken(format.format(itemQty.get().getOrderDate()));
		return itemQty;
	}

	@Override
	public List<ModelInquiryList> getOrderItemQtyDetailsById(Long id) {
    String qry="SELECT a.design_id,a.order_item_qty_id,a.active_status,a.remarks,a.item_Qty FROM mlfm_order_item_qty a INNER JOIN mlfm_order_item b ON a.order_item_id=b.item_order_id WHERE a.order_item_qty_id=coalesce(:id,a.order_item_qty_id)";
		
		
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			@Override
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelInquiryList bn=new ModelInquiryList();
		        
				bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
				bn.setItemQty(rs.getDouble("item_qty"));
				bn.setActiveStatus(rs.getInt("active_status"));
				bn.setRemarks(rs.getString("remarks"));
				bn.setDesignId(rs.getLong("design_id"));
				
				
			
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	     
	      
  

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelOrderItemQty> getQuantityDataByOrderItemId(Long id) {
		// TODO Auto-generated method stub
		return orderItemQty.findItemQtyDetails(id);
	}

	@Override
	public List<ModelOrderItemQty> findOrderItemQtyForEdit(Long id) {
		// TODO Auto-generated method stub
		return orderItemQty.findItemQtyDetailsForEdit(id);
	}
	
	
	
	/*created by sohel rana on 31/03/2019 
	 * 
	 * Edit qty save
	 */
	
	public void updateOrderQtyData(ModelOrderItemQty qty){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update mlfm_order_item_qty set item_qty=?,order_date=?,uom_id=?,active_status=?,remarks=?, sample_type=?, updated_by=?, update_timestamp=? where order_item_qty_id=?",qty.getItemQty(),qty.getOrderDate(),qty.getModelUOM().getUomId(),qty.getActiveStatus(),qty.getRemarks(),qty.getSampleType(),qty.getModelUpdatedBy().getUserId(),qty.getUpdateTimestap(),qty.getOrderItemQtyId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}
	
	
	public void addedDesignId(ModelOrderItemQty qty){
        try {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("update mlfm_order_item_qty set design_id=?, updated_by=?, update_timestamp=? where order_item_qty_id=? ",qty.getModelDesign().getDesignId(),qty.getModelUpdatedBy().getUserId(),qty.getUpdateTimestap(),qty.getOrderItemQtyId());
        	
        
        System.out.println("update success");
       }
        
        
        catch(Exception e) {
        	e.printStackTrace();
}

	}
	
	
	
	//get qty data by item order id
	
	@Override
	public List<ModelInquiryList> getOrderItemQtyDetailsByOrderItemId(Long id) {
    String qry="SELECT a.design_id,a.order_item_qty_id,a.active_status,a.remarks,a.item_Qty FROM mlfm_order_item_qty a INNER JOIN mlfm_order_item b ON a.order_item_id=b.item_order_id WHERE b.item_order_id=coalesce(:id,b.item_order_id)";
		
		
		RowMapper<ModelInquiryList> serviceMapper=new RowMapper<ModelInquiryList>() {
			@Override
			public ModelInquiryList mapRow(ResultSet rs, int rownum) throws SQLException{
				ModelInquiryList bn=new ModelInquiryList();
		        
				bn.setOrderItemQuantityId(rs.getLong("order_item_qty_id"));
				bn.setItemQty(rs.getDouble("item_qty"));
				bn.setActiveStatus(rs.getInt("active_status"));
				bn.setRemarks(rs.getString("remarks"));
				bn.setDesignId(rs.getLong("design_id"));
				
				
			
			return bn;
			
			}	
		};
		

		MapSqlParameterSource parameters = new MapSqlParameterSource();
	      
	      parameters.addValue("id", id);
	     
	      
  

		return  jdbcTemplate.query(qry,parameters,serviceMapper);
	}

	@Override
	public List<ModelOrderItemQty> getAllInquiryMappedData(Long typeId, Long owner, Long ultimateOwner, Long inquiryId,
			Long user, Date startDate, Date endDate, int status) {
		// TODO Auto-generated method stub
		return orderItemQty.findInquiryMappedDetails(typeId, owner, ultimateOwner, inquiryId, user, startDate, endDate, status);
	}
	

}
